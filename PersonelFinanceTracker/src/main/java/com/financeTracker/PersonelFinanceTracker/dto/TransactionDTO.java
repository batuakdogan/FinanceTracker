package com.financeTracker.PersonelFinanceTracker.dto;

import com.financeTracker.PersonelFinanceTracker.entity.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionDTO {
    private Long id;
    
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;
    
    @NotNull(message = "Transaction type is required")
    private TransactionType type;
    
    @NotBlank(message = "Category is required")
    private String category;
    
    private String description;
    
    @NotNull(message = "Transaction date is required")
    private LocalDateTime transactionDate;
    
    private Long userId;
} 