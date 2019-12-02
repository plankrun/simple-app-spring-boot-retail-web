package com.example.retailapplication.repository;

import com.example.retailapplication.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository class to access Transaction Entity on database
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
}
