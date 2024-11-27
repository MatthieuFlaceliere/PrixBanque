package com.prixbanque.account_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdateBalanceDTO {
    @NotNull(message = "Balance cannot be null.")
    private BigDecimal balance;
}
