package com.prixbanque.account_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
    @NotBlank(message = "Nas cannot be blank.")
    private String nas;
    @NotBlank(message = "Password cannot be blank.")
    private String password;
}
