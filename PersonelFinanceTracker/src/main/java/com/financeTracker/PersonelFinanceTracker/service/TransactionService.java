package com.financeTracker.PersonelFinanceTracker.service;

import com.financeTracker.PersonelFinanceTracker.dto.TransactionDTO;
import com.financeTracker.PersonelFinanceTracker.entity.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {
    TransactionDTO createTransaction(TransactionDTO transactionDTO);
    TransactionDTO updateTransaction(Long transactionId, TransactionDTO transactionDTO);
    void deleteTransaction(Long transactionId);
    TransactionDTO getTransactionById(Long transactionId);
    List<TransactionDTO> getAllTransactionsByUser(Long userId);
    List<TransactionDTO> getTransactionsByUserAndType(Long userId, TransactionType type, LocalDateTime startDate, LocalDateTime endDate);
    BigDecimal calculateTotalByUserAndType(Long userId, TransactionType type, LocalDateTime startDate, LocalDateTime endDate);
    List<TransactionDTO> getTransactionsByUserAndCategory(Long userId, String category);
} 