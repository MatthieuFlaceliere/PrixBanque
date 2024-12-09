package com.prixbanque.account_service.service;

import com.prixbanque.account_service.dto.AccountDTO;
import com.prixbanque.account_service.dto.CreateAccountDTO;
import com.prixbanque.account_service.dto.UpdateAccountDTO;
import com.prixbanque.account_service.dto.UpdateBalanceDTO;
import com.prixbanque.account_service.model.Account;
import com.prixbanque.account_service.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository repository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AccountService accountService;

    @Test
    void getById() {
        Long id = 1L;
        Account account = new Account(id, "John", "Doe", "john.doe@example.com", "123456789", "123456789", "password", BigDecimal.ZERO);
        when(repository.findById(id)).thenReturn(Optional.of(account));
        when(modelMapper.map(account, AccountDTO.class)).thenReturn(new AccountDTO(id, "John", "Doe", "john.doe@example.com", "123456789", "123456789", BigDecimal.ZERO));

        AccountDTO result = accountService.getById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(repository).findById(id);
    }

    @Test
    void save() {
        CreateAccountDTO createAccountDTO = new CreateAccountDTO("John", "Doe", "john.doe@example.com", "123456789", "123456789", BigDecimal.ZERO, "password");
        Account account = new Account(1L, "John", "Doe", "john.doe@example.com", "123456789", "123456789", "password", BigDecimal.ZERO);
        when(modelMapper.map(createAccountDTO, Account.class)).thenReturn(account);
        when(repository.save(account)).thenReturn(account);
        when(modelMapper.map(account, AccountDTO.class)).thenReturn(new AccountDTO(1L, "John", "Doe", "john.doe@example.com", "123456789", "123456789", BigDecimal.ZERO));

        AccountDTO result = accountService.save(createAccountDTO);

        assertNotNull(result);
        assertEquals("John", result.getFirstname());
        verify(repository).save(account);
    }

    @Test
    void update() {
        Long id = 1L;
        UpdateAccountDTO updateAccountDTO = new UpdateAccountDTO("Jane", "Smith", "jane.smith@example.com", "987654321", "123456789", BigDecimal.ZERO, "password");
        Account account = new Account(id, "John", "Doe", "john.doe@example.com", "123456789", "123456789", "password", BigDecimal.ZERO);
        when(modelMapper.map(updateAccountDTO, Account.class)).thenReturn(account);
        when(repository.save(account)).thenReturn(account);
        when(modelMapper.map(account, AccountDTO.class)).thenReturn(new AccountDTO(id, "Jane", "Smith", "jane.smith@example.com", "987654321", "123456789", BigDecimal.ZERO));

        AccountDTO result = accountService.update(id, updateAccountDTO);

        assertNotNull(result);
        assertEquals("Jane", result.getFirstname());
        verify(repository).save(account);
    }

    @Test
    void delete() {
        Long id = 1L;
        doNothing().when(repository).deleteById(id);

        assertDoesNotThrow(() -> accountService.delete(id));
        verify(repository).deleteById(id);
    }

    @Test
    void updateBalance() {
        Long id = 1L;
        UpdateBalanceDTO balanceDTO = new UpdateBalanceDTO(BigDecimal.TEN);
        Account account = new Account(id, "John", "Doe", "john.doe@example.com", "123456789", "123456789", "password", BigDecimal.ZERO);
        when(repository.findById(id)).thenReturn(Optional.of(account));

        accountService.updateBalance(id, balanceDTO);

        assertEquals(BigDecimal.TEN, account.getBalance());
        verify(repository).save(account);
    }

    @Test
    void getById_throwsEntityNotFound() {
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> accountService.getById(id));
    }

    @Test
    void updateBalance_throwsEntityNotFound() {
        Long id = 1L;
        UpdateBalanceDTO balanceDTO = new UpdateBalanceDTO(BigDecimal.TEN);
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> accountService.updateBalance(id, balanceDTO));
    }
}
