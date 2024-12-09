package com.prixbanque.account_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class CreateAccountDTO {

    @NotBlank(message = "Firstname cannot be blank.")
    @Size(min = 2, max = 50, message = "Firstname must be between 2 and 50 characters.")
    private String firstname;

    @NotBlank(message = "Lastname cannot be blank.")
    @Size(min = 2, max = 50, message = "Lastname must be between 2 and 50 characters.")
    private String lastname;

    @NotBlank(message = "Email cannot be blank.")
    @Email(message = "Email should be valid.")
    @Size(min = 5, max = 100, message = "Email must be between 5 and 100 characters.")
    private String email;

    @NotBlank(message = "Phone cannot be blank.")
    @Size(min = 10, max = 15, message = "Phone number must be between 10 and 15 characters.")
    private String phone;

    @NotBlank(message = "NAS cannot be blank.")
    @Size(min = 9, max = 9, message = "NAS must be exactly 9 characters.")
    private String nas;

    @NotNull(message = "Balance cannot be null.")
    private BigDecimal balance;

    @NotBlank(message = "Password cannot be blank.")
    @Size(min = 6, max = 50, message = "Password must be between 6 and 50 characters.")
    private String password;
}
