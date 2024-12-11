package com.prixbanque.account_service.repository;

import com.prixbanque.account_service.model.Account;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByNas(String nas);

    @Modifying
    @Transactional
    @Query("update Account a set a.balance = ?1 where a.id = ?2")
    void setAccountBalance(BigDecimal balance, Long id);
}
