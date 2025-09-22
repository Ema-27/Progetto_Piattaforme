package com.educative.ecommerce.service;

import com.educative.ecommerce.model.User;
import com.educative.ecommerce.repository.UserRepository;
import com.educative.ecommerce.support.MailUserAlreadyExistsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User registerUser(User user) throws MailUserAlreadyExistsException {
        if(userRepository.existsByEmail(user.getEmail()))
            throw new MailUserAlreadyExistsException();
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<User> readUser(Integer id) {
        return userRepository.findById(id);
    }

    @Transactional
    public void deleteUser(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Utente non trovato: " + id);
        }
        userRepository.deleteById(id);
    }
}
