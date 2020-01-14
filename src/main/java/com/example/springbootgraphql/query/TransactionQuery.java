package com.example.springbootgraphql.query;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.example.springbootgraphql.dao.entity.Transaction;
import com.example.springbootgraphql.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionQuery implements GraphQLQueryResolver {
    @Autowired
    private TransactionService transactionService;

    public List<Transaction> getTransactionsByCustomerId(final int customerId, final int page, final int size) {
        return this.transactionService.getTransactionsByCustomerId(customerId, page, size);
    }

    public List<Transaction> getTransactionsByAccountNumbers(final List<String> accountNumbers, final int page, final int size) {
        return this.transactionService.getTransactionsByAccountNumbers(accountNumbers, page, size);
    }

    public List<Transaction> getTransactionsByDescription(final String description, final int page, final int size) {
        return this.transactionService.getTransactionsByDescription(description, page, size);
    }
}
