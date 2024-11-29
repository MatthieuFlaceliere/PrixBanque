package com.prixbanque.transaction_service.service;

import com.prixbanque.transaction_service.dto.CreateTransactionDTO;
import com.prixbanque.transaction_service.dto.TransactionDTO;
import com.prixbanque.transaction_service.dto.UpdateTransactionDTO;
import com.prixbanque.transaction_service.http.AccountClient;
import com.prixbanque.transaction_service.http.model.Account;
import com.prixbanque.transaction_service.http.model.Balance;
import com.prixbanque.transaction_service.model.Transaction;
import com.prixbanque.transaction_service.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class TransactionService {
    private final TransactionRepository repository;
    private ModelMapper modelMapper;
    private AccountClient accountClient;

    public Page<TransactionDTO> getAll(Pageable pagination) {
        return repository
                .findAll(pagination)
                .map(p -> modelMapper.map(p, TransactionDTO.class));
    }

    public TransactionDTO getById(Long id) {
        Transaction transaction = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found"));
        return modelMapper.map(transaction, TransactionDTO.class);
    }

    public TransactionDTO processTransaction(CreateTransactionDTO createTransactionDTO) {
        Transaction transaction = modelMapper.map(createTransactionDTO, Transaction.class);

        Account senderAccount = accountClient.getAccountById(transaction.getSenderAccount());
        Account receiverAccount = accountClient.getAccountById(transaction.getReceiverAccount());

        validateTransaction(senderAccount, receiverAccount, transaction.getAmount());

        updateAccountBalance(senderAccount, receiverAccount, transaction);

        repository.save(transaction);

        return modelMapper.map(transaction, TransactionDTO.class);
    }

    private void validateTransaction(Account senderAccount, Account receiverAccount, BigDecimal amount) {
        if (senderAccount == null || receiverAccount == null) {
            throw new IllegalArgumentException("Invalid sender or receiver account");
        }
        if (senderAccount.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Sender account does not have enough balance");
        }
    }

    private void updateAccountBalance(Account senderAccount, Account receiverAccount, Transaction transaction) {
        Balance senderAccountBalance = new Balance();
        senderAccountBalance.setBalance(senderAccount.getBalance().subtract(transaction.getAmount()));
        accountClient.updateBalance(transaction.getSenderAccount(), senderAccountBalance);

        Balance receiverAccountBalance = new Balance();
        receiverAccountBalance.setBalance(receiverAccount.getBalance().add(transaction.getAmount()));
        accountClient.updateBalance(transaction.getReceiverAccount(), receiverAccountBalance);
    }

    public TransactionDTO update(Long id, UpdateTransactionDTO updateTransactionDTO) {
        Transaction transaction = modelMapper.map(updateTransactionDTO, Transaction.class);
        transaction.setId(id);
        repository.save(transaction);
        return modelMapper.map(transaction, TransactionDTO.class);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}

