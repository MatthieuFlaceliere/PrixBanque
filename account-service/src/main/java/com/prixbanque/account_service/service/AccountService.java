package com.prixbanque.account_service.service;

import com.prixbanque.account_service.dto.AccountDTO;
import com.prixbanque.account_service.dto.CreateAccountDTO;
import com.prixbanque.account_service.dto.UpdateAccountDTO;
import com.prixbanque.account_service.dto.UpdateBalanceDTO;
import com.prixbanque.account_service.model.Account;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.prixbanque.account_service.repository.AccountRepository;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class AccountService {
    private final AccountRepository repository;
    private ModelMapper modelMapper;

    public Page<AccountDTO> getAll(Pageable pagination) {
        return repository
                .findAll(pagination)
                .map(p -> modelMapper.map(p, AccountDTO.class));
    }

    public AccountDTO getById(Long id) {
        Account account = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));
        return modelMapper.map(account, AccountDTO.class);
    }

    public AccountDTO save(CreateAccountDTO createAccountDTO) {
        Account account = modelMapper.map(createAccountDTO, Account.class);
        account.setBalance(BigDecimal.ZERO);
        repository.save(account);

        return modelMapper.map(account, AccountDTO.class);
    }

    public AccountDTO update(Long id, UpdateAccountDTO updateAccountDTO) {
        Account account = modelMapper.map(updateAccountDTO, Account.class);
        account.setId(id);
        repository.save(account);
        return modelMapper.map(account, AccountDTO.class);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public void updateBalance(Long id, UpdateBalanceDTO balance) {
        BigDecimal newBalance = balance.getBalance();

//        Account account = repository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Account not found"));
//        account.setBalance(newBalance);
//        repository.save(account);

        repository.setAccountBalance(newBalance, id);
    }
}
