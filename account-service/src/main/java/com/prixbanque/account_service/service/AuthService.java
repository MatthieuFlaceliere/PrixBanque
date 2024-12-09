package com.prixbanque.account_service.service;

import com.prixbanque.account_service.model.Account;
import com.prixbanque.account_service.model.Token;
import com.prixbanque.account_service.repository.AccountRepository;
import com.prixbanque.account_service.repository.TokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    private final AccountRepository repository;
    private final TokenRepository tokenRepository;

    public String login(String nas, String password) {
        Account account = repository.findByNas(nas);
        if (account != null && account.getPassword().equals(password)) {
            return generateOrUpdateToken(account);
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }

    private String generateOrUpdateToken(Account account) {
        Token token = tokenRepository.findByAccount(account);
        if (token != null) {
            return token.getToken();
        } else {
            return generateToken(account);
        }
    }

    private String generateToken(Account account) {
        // Génère un token aléatoire
        String tokenValue = UUID.randomUUID().toString();
        // Sauvegarde le token dans la base de données
        Token token = new Token();
        token.setToken(tokenValue);
        token.setAccount(account);
        tokenRepository.save(token);
        return tokenValue;
    }

    public void logout(String tokenValue) {
        // Supprime le token de la base de données
        Token token = tokenRepository.findByToken(tokenValue);
        if (token != null) {
            tokenRepository.delete(token);
        } else {
            throw new RuntimeException("Invalid token");
        }
    }

    public void validate(String tokenValue) {
        Token token = tokenRepository.findByToken(tokenValue);
        if (token == null) {
            throw new RuntimeException("Invalid token");
        }
    }
}
