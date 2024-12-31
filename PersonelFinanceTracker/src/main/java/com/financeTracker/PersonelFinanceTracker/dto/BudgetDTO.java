package com.financeTracker.PersonelFinanceTracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.YearMonth;

@Data
public class BudgetDTO {
    private Long id;
    
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;
    
    @NotBlank(message = "Category is required")
    private String category;
    
    @NotNull(message = "Budget month is required")
    private YearMonth budgetMonth;
    
    private Long userId;
    
    private BigDecimal spentAmount;
    private BigDecimal remainingAmount;
} 