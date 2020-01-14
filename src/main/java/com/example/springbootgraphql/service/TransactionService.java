package com.example.springbootgraphql.service;

import com.example.springbootgraphql.dao.entity.Transaction;
import com.example.springbootgraphql.dao.repository.TransactionRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository ;

    public TransactionService(final TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository ;
    }

    @Transactional(readOnly = true)
    public List<Transaction> getTransactionsByCustomerId(final int customerId, final int page, final int size) {
        Pageable pageable = PageRequest.of(page, size);
        return this.transactionRepository.findByCustomerId(customerId, pageable);
    }

    @Transactional(readOnly = true)
    public List<Transaction> getTransactionsByAccountNumbers(final List<String> accountNumbers, final int page, final int size) {
        Pageable pageable = PageRequest.of(page, size);
        return this.transactionRepository.findByAccountNumbers(accountNumbers, pageable);
    }

    @Transactional(readOnly = true)
    public List<Transaction> getTransactionsByDescription(final String description, final int page, final int size) {
        Pageable pageable = PageRequest.of(page, size);
        return this.transactionRepository.findByDescription("%" + description + "%", pageable);
    }
}
