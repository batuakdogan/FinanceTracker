package com.financeTracker.PersonelFinanceTracker.repository;

import com.financeTracker.PersonelFinanceTracker.entity.Transaction;
import com.financeTracker.PersonelFinanceTracker.entity.TransactionType;
import com.financeTracker.PersonelFinanceTracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserOrderByTransactionDateDesc(User user);
    
    List<Transaction> findByUserAndTypeAndTransactionDateBetween(
        User user, 
        TransactionType type, 
        LocalDateTime startDate, 
        LocalDateTime endDate
    );
    
    @Query("SELECT t FROM Transaction t WHERE t.user = ?1 AND t.type = ?2 AND t.transactionDate BETWEEN ?3 AND ?4")
    List<Transaction> findTransactionsByUserAndTypeInPeriod(User user, TransactionType type, LocalDateTime startDate, LocalDateTime endDate);
    
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.user = ?1 AND t.type = ?2 AND t.transactionDate BETWEEN ?3 AND ?4")
    BigDecimal calculateTotalByUserAndTypeInPeriod(User user, TransactionType type, LocalDateTime startDate, LocalDateTime endDate);
    
    List<Transaction> findByUserAndCategoryOrderByTransactionDateDesc(User user, String category);
} 