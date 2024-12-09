package com.prixbanque.account_service.repository;

import com.prixbanque.account_service.model.Account;
import com.prixbanque.account_service.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, String> {
    Token findByToken(String tokenValue);

    Token findByAccount(Account account);
}
