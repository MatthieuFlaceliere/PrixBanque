package com.prixbanque.account_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {

    @NotNull(message = "Account ID cannot be null.")
    private Long id;

    @NotNull(message = "Firstname cannot be null.")
    private String firstname;

    @NotNull(message = "Lastname cannot be null.")
    private String lastname;

    @NotNull(message = "Email cannot be null.")
    private String email;

    @NotNull(message = "Phone cannot be null.")
    private String phone;

    @NotNull(message = "NAS cannot be null.")
    private String nas;

    @NotNull(message = "Balance cannot be null.")
    private BigDecimal balance;
}
