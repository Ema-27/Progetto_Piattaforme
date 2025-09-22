package com.educative.ecommerce.controllers;

import com.educative.ecommerce.model.User;
import com.educative.ecommerce.service.UserService;
import com.educative.ecommerce.support.MailUserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody User user){
        try {
            User added = userService.registerUser(user);
            return new ResponseEntity<>(added, HttpStatus.CREATED);
        } catch (MailUserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists");
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers(){
        List<User> body = userService.listUsers();
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
