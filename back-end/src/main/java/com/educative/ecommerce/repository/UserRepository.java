package com.educative.ecommerce.repository;

import com.educative.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findByName(String name);
    List<User> findBySurname(String surname);
    List<User> findByNameAndSurname(String name, String surname);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    // Esempi opzionali:
    // List<User> findByNameContainingIgnoreCase(String partOfName);
    // Optional<User> findByNameAndSurname(String name, String surname);
}
