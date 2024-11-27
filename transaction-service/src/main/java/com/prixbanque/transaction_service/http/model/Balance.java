package com.prixbanque.transaction_service.http.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Balance {
    public BigDecimal balance;
}
