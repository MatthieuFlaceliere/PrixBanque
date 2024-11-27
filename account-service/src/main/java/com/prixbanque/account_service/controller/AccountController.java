package com.prixbanque.account_service.controller;

import com.prixbanque.account_service.dto.AccountDTO;
import com.prixbanque.account_service.dto.CreateAccountDTO;
import com.prixbanque.account_service.dto.UpdateAccountDTO;
import com.prixbanque.account_service.dto.UpdateBalanceDTO;
import com.prixbanque.account_service.service.AccountService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
public class AccountController {
    private AccountService accountService;

    @GetMapping
    public Page<AccountDTO> list(@PageableDefault(size = 10) Pageable pagination) {
        return accountService.getAll(pagination);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> detail(@PathVariable @NotNull Long id) {
        AccountDTO accountDTO = accountService.getById(id);

        return ResponseEntity.ok(accountDTO);
    }

    @PostMapping
    public ResponseEntity<AccountDTO> create(
            @Valid @RequestBody CreateAccountDTO createAccountDTO,
            UriComponentsBuilder uriBuilder) {
        AccountDTO account = accountService.save(createAccountDTO);
        URI uri = uriBuilder.path("/accounts/{id}").buildAndExpand(account.getId()).toUri();

        return ResponseEntity.created(uri).body(account);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountDTO> update(
            @PathVariable @NotNull Long id,
            @RequestBody @Valid UpdateAccountDTO updateAccountDTO) {
        AccountDTO account = accountService.update(id, updateAccountDTO);

        return ResponseEntity.ok(account);
    }

    @PutMapping("/{id}/balance")
    public ResponseEntity<AccountDTO> updateBalance(
            @PathVariable @NotNull Long id,
            @RequestBody @Valid UpdateBalanceDTO updateBalanceDTO) {
        accountService.updateBalance(id, updateBalanceDTO);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull Long id) {
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
