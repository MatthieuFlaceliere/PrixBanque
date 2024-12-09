package com.prixbanque.account_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdateAccountDTO extends CreateAccountDTO {
    public UpdateAccountDTO(String firstname, String lastname, String email, String phone, String nas, BigDecimal balance, String password) {
        super(firstname, lastname, email, phone, nas, balance, password);
    }
}
