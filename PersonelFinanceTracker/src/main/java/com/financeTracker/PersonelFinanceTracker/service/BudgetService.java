package com.financeTracker.PersonelFinanceTracker.service;

import com.financeTracker.PersonelFinanceTracker.dto.BudgetDTO;

import java.time.YearMonth;
import java.util.List;

public interface BudgetService {
    BudgetDTO createBudget(BudgetDTO budgetDTO);
    BudgetDTO updateBudget(Long budgetId, BudgetDTO budgetDTO);
    void deleteBudget(Long budgetId);
    BudgetDTO getBudgetById(Long budgetId);
    List<BudgetDTO> getBudgetsByUserAndMonth(Long userId, YearMonth month);
    List<BudgetDTO> getAllBudgetsByUser(Long userId);
    BudgetDTO getBudgetByUserAndCategoryAndMonth(Long userId, String category, YearMonth month);
    void checkBudgetLimits(Long userId);
} 