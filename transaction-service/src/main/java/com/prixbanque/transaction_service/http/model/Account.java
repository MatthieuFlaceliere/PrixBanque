package com.prixbanque.transaction_service.http.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Account {
    public Long id;
    public String firstName;
    public String lastName;
    public String email;
    public String phone;
    public String nas;
    public BigDecimal balance;
}
