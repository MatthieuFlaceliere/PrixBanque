package com.prixbanque.account_service.controller;

import com.prixbanque.account_service.dto.LoginDTO;
import com.prixbanque.account_service.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        String token = authService.login(loginDTO.getNas(), loginDTO.getPassword());

        return ResponseEntity.ok(token);
    }

    @PostMapping("/logout")
    public void logout(@RequestBody String tokenValue) {
        authService.logout(tokenValue);
    }

    @PostMapping("/validate")
    public void validate(@RequestBody String tokenValue) {
        authService.validate(tokenValue);
    }
}
