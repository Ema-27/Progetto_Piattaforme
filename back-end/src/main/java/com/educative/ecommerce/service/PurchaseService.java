package com.educative.ecommerce.service;

import com.educative.ecommerce.dto.product.PurchaseDto;
import com.educative.ecommerce.model.*;
import com.educative.ecommerce.repository.ProductInPurchaseRepository;
import com.educative.ecommerce.repository.ProductRepository;
import com.educative.ecommerce.repository.PurchaseRepository;
import com.educative.ecommerce.repository.UserRepository;
import com.educative.ecommerce.support.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final ProductInPurchaseRepository productInPurchaseRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public PurchaseService(PurchaseRepository purchaseRepository,
                           ProductInPurchaseRepository productInPurchaseRepository,
                           ProductRepository productRepository,
                           UserRepository userRepository) {
        this.purchaseRepository = purchaseRepository;
        this.productInPurchaseRepository = productInPurchaseRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    // Mapper DTO → Entity
    public Purchase getPurchaseFromDto(PurchaseDto purchaseDto, User user) {
        Purchase purchase = new Purchase();
        purchase.setBuyer(user);
        purchase.setPurchaseTime(purchaseDto.getPurchaseTime());

        double totalPrice = 0;
        List<ProductInPurchase> productInPurchaseList = new ArrayList<>();

        for(Integer productId : purchaseDto.getProductIds()) {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException(productId));
            totalPrice += product.getPrice();
            ProductInPurchase pip = new ProductInPurchase();
            pip.setPrice(product.getPrice());
            pip.setProduct(product);
            pip.setPurchase(purchase); // Collega la purchase!
            productInPurchaseList.add(pip);
        }
        purchase.setPrice(totalPrice);
        purchase.setProductsInPurchaseList(productInPurchaseList);
        return purchase;
    }

    @Transactional
    public Purchase addPurchase(PurchaseDto purchaseDto, User user) throws UserNotFoundException {
        // Controllo utente
        if (!userRepository.existsById(user.getId()))
            throw new UserNotFoundException();

        Purchase purchase = getPurchaseFromDto(purchaseDto, user);

        Purchase savedPurchase = purchaseRepository.save(purchase);

        for(ProductInPurchase pip: savedPurchase.getProductsInPurchaseList()){
            productInPurchaseRepository.save(pip);
        }
        return savedPurchase;
    }

    @Transactional(readOnly = true)
    public List<Purchase> listPurchases(){
        return purchaseRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Purchase> listPurchasesByUser(User user) throws UserNotFoundException {
        if(!userRepository.existsById(user.getId()))
            throw new UserNotFoundException();
        return purchaseRepository.findByBuyer(user);
    }

    @Transactional(readOnly = true)
    public List<Purchase> listPurchasesByUserInPeriod(User user, Date from, Date to) throws UserNotFoundException, DateWrongRangeException {
        if(!userRepository.existsById(user.getId()))
            throw new UserNotFoundException();
        if(from.compareTo(to) >= 0)
            throw new DateWrongRangeException();
        // Se hai aggiunto il derived method nel repo:
        return purchaseRepository.findByBuyerAndPurchaseTimeBetween(user, from, to);
    }

    @Transactional(readOnly = true)
    public Optional<User> readUser(Integer userId) {
        return userRepository.findById(userId);
    }

    @Transactional
    public void deletePurchase(Integer id){
        if (!purchaseRepository.existsById(id)) {
            throw new RuntimeException("Acquisto non trovato: " + id);
        }
        purchaseRepository.deleteById(id);
    }
}
