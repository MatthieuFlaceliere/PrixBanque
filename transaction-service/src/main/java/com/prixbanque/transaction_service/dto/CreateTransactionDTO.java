package com.prixbanque.transaction_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateTransactionDTO {
    @NotNull(message = "Sender account ID cannot be null.")
    private Long senderAccount;
    @NotNull(message = "Receiver account ID cannot be null.")
    private Long receiverAccount;
    @NotNull(message = "Amount cannot be null.")
    private BigDecimal amount;
}
