package com.educative.ecommerce.repository;

import com.educative.ecommerce.model.Purchase;
import com.educative.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {

    List<Purchase> findByBuyer(User user);
    List<Purchase> findByPurchaseTime(Date date);

    // Derived query alternativa a quella JPQL:
    List<Purchase> findByBuyerAndPurchaseTimeBetween(User buyer, Date start, Date end);

    // Oppure mantieni la tua versione JPQL se vuoi:
    @Query("select p from Purchase p where p.purchaseTime > ?1 and p.purchaseTime < ?2 and p.buyer = ?3")
    List<Purchase> findByBuyerInPeriod(Date dataI, Date dataF, User user);
}
