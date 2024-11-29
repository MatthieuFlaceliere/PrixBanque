package com.prixbanque.transaction_service.controller;

import com.prixbanque.transaction_service.dto.CreateTransactionDTO;
import com.prixbanque.transaction_service.dto.TransactionDTO;
import com.prixbanque.transaction_service.dto.UpdateTransactionDTO;
import com.prixbanque.transaction_service.service.TransactionService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
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
@RequestMapping("/transactions")
@AllArgsConstructor
public class TransactionController {
    private TransactionService transactionService;

    @GetMapping
    public Page<TransactionDTO> list(@PageableDefault(size = 10) Pageable pagination) {
        return transactionService.getAll(pagination);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> detail(@PathVariable @NotNull Long id) {
        TransactionDTO transactionDTO = transactionService.getById(id);

        return ResponseEntity.ok(transactionDTO);
    }

    @PostMapping
    @CircuitBreaker(name = "createTransaction", fallbackMethod = "")
    public ResponseEntity<TransactionDTO> create(
            @Valid @RequestBody CreateTransactionDTO transactionDTO,
            UriComponentsBuilder uriBuilder) {
        TransactionDTO transaction = transactionService.processTransaction(transactionDTO);
        URI uri = uriBuilder.path("/transactions/{id}").buildAndExpand(transaction.getId()).toUri();

        return ResponseEntity.created(uri).body(transaction);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionDTO> update(
            @PathVariable @NotNull Long id,
            @RequestBody @Valid UpdateTransactionDTO updateTransactionDTO) {
        TransactionDTO transaction = transactionService.update(id, updateTransactionDTO);

        return ResponseEntity.ok(transaction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull Long id) {
        transactionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

