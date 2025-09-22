package com.educative.ecommerce.controllers;

import com.educative.ecommerce.dto.product.PurchaseDto;
import com.educative.ecommerce.model.Purchase;
import com.educative.ecommerce.model.User;
import com.educative.ecommerce.service.PurchaseService;
import com.educative.ecommerce.service.UserService;
import com.educative.ecommerce.support.DateWrongRangeException;
import com.educative.ecommerce.support.UserNotFoundException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;
    private final UserService userService;

    public PurchaseController(PurchaseService purchaseService, UserService userService) {
        this.purchaseService = purchaseService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createPurchase(@RequestBody @Valid PurchaseDto purchaseDto) {
        User user = userService.readUser(purchaseDto.getBuyerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "User not found!"));

        Set<Integer> uniqueProducts = new HashSet<>(purchaseDto.getProductIds());
        if (uniqueProducts.size() != purchaseDto.getProductIds().size()) {
            return ResponseEntity.badRequest().body("Check the product list: there are duplicate product IDs");
        }

        try {
            purchaseService.addPurchase(purchaseDto, user);
            return new ResponseEntity<>("Purchase created", HttpStatus.CREATED);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User not found!");
        }
    }

    // RESTITUISCI SOLO DTO (NON ENTITY!) QUI SOTTO:

    @GetMapping
    public ResponseEntity<List<PurchaseDto>> getPurchases() {
        List<Purchase> purchases = purchaseService.listPurchases();
        List<PurchaseDto> dtos = purchases.stream()
                .map(PurchaseDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> listPurchases(@PathVariable int userId) {
        User user = userService.readUser(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found!"));
        try {
            List<Purchase> purchases = purchaseService.listPurchasesByUser(user);
            List<PurchaseDto> dtos = purchases.stream()
                    .map(PurchaseDto::new)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found!");
        }
    }

    @GetMapping("/user/{userId}/period")
    public ResponseEntity<?> listPurchasesInPeriod(@PathVariable int userId,
                                                   @RequestParam("start") @DateTimeFormat(pattern = "dd-MM-yyyy") Date dataI,
                                                   @RequestParam("end") @DateTimeFormat(pattern = "dd-MM-yyyy") Date dataF) {
        User user = userService.readUser(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found!"));
        try {
            List<Purchase> result = purchaseService.listPurchasesByUserInPeriod(user, dataI, dataF);
            if (result.isEmpty())
                return ResponseEntity.ok("No results");
            List<PurchaseDto> dtos = result.stream().map(PurchaseDto::new).collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found!");
        } catch (DateWrongRangeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Check start and end date");
        }
    }

    @DeleteMapping("/{purchaseId}")
    public ResponseEntity<?> deletePurchase(@PathVariable Integer purchaseId) {
        purchaseService.deletePurchase(purchaseId);
        return ResponseEntity.noContent().build();
    }
}
