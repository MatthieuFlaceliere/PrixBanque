package com.prixbanque.transaction_service.service;

import com.prixbanque.transaction_service.dto.CreateTransactionDTO;
import com.prixbanque.transaction_service.dto.TransactionDTO;
import com.prixbanque.transaction_service.dto.UpdateTransactionDTO;
import com.prixbanque.transaction_service.model.Transaction;
import com.prixbanque.transaction_service.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TransactionService {
    private final TransactionRepository repository;
    private ModelMapper modelMapper;

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

    public TransactionDTO save(CreateTransactionDTO createTransactionDTO) {
        Transaction transaction = modelMapper.map(createTransactionDTO, Transaction.class);
        repository.save(transaction);

        return modelMapper.map(transaction, TransactionDTO.class);
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

