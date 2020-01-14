package com.example.springbootgraphql.dao.repository;

import com.example.springbootgraphql.dao.entity.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    @Query("SELECT t FROM Transaction t WHERE t.customerId = :customerId")
    List<Transaction> findByCustomerId(@Param("customerId") int customerId, Pageable pageable);

    @Query("SELECT t FROM Transaction t WHERE t.accountNumber IN :accountNumbers")
    List<Transaction> findByAccountNumbers(@Param("accountNumbers") List<String> accountNumbers, Pageable pageable);

    @Query("SELECT t FROM Transaction t WHERE t.description LIKE :description")
    List<Transaction> findByDescription(@Param("description") String description, Pageable pageable);
}
