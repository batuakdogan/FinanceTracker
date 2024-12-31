package com.financeTracker.PersonelFinanceTracker.service.impl;

import com.financeTracker.PersonelFinanceTracker.dto.BudgetDTO;
import com.financeTracker.PersonelFinanceTracker.entity.Budget;
import com.financeTracker.PersonelFinanceTracker.entity.TransactionType;
import com.financeTracker.PersonelFinanceTracker.entity.User;
import com.financeTracker.PersonelFinanceTracker.repository.BudgetRepository;
import com.financeTracker.PersonelFinanceTracker.repository.TransactionRepository;
import com.financeTracker.PersonelFinanceTracker.repository.UserRepository;
import com.financeTracker.PersonelFinanceTracker.service.BudgetService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    @Override
    @Transactional
    public BudgetDTO createBudget(BudgetDTO budgetDTO) {
        User user = userRepository.findById(budgetDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (budgetRepository.existsByUserAndCategoryAndBudgetMonth(user, budgetDTO.getCategory(), budgetDTO.getBudgetMonth())) {
            throw new IllegalArgumentException("Budget already exists for this category and month");
        }

        Budget budget = new Budget();
        budget.setUser(user);
        budget.setAmount(budgetDTO.getAmount());
        budget.setCategory(budgetDTO.getCategory());
        budget.setBudgetMonth(budgetDTO.getBudgetMonth());

        Budget savedBudget = budgetRepository.save(budget);
        return convertToDTO(savedBudget);
    }

    @Override
    @Transactional
    public BudgetDTO updateBudget(Long budgetId, BudgetDTO budgetDTO) {
        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new EntityNotFoundException("Budget not found"));

        budget.setAmount(budgetDTO.getAmount());
        budget.setCategory(budgetDTO.getCategory());
        budget.setBudgetMonth(budgetDTO.getBudgetMonth());

        Budget updatedBudget = budgetRepository.save(budget);
        return convertToDTO(updatedBudget);
    }

    @Override
    @Transactional
    public void deleteBudget(Long budgetId) {
        if (!budgetRepository.existsById(budgetId)) {
            throw new EntityNotFoundException("Budget not found");
        }
        budgetRepository.deleteById(budgetId);
    }

    @Override
    public BudgetDTO getBudgetById(Long budgetId) {
        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new EntityNotFoundException("Budget not found"));
        return convertToDTO(budget);
    }

    @Override
    public List<BudgetDTO> getBudgetsByUserAndMonth(Long userId, YearMonth month) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return budgetRepository.findByUserAndBudgetMonth(user, month)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BudgetDTO> getAllBudgetsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return budgetRepository.findByUserOrderByBudgetMonthDesc(user)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BudgetDTO getBudgetByUserAndCategoryAndMonth(Long userId, String category, YearMonth month) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Budget budget = budgetRepository.findByUserAndCategoryAndBudgetMonth(user, category, month)
                .orElseThrow(() -> new EntityNotFoundException("Budget not found"));
        return convertToDTO(budget);
    }

    @Override
    public void checkBudgetLimits(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        
        YearMonth currentMonth = YearMonth.now();
        List<Budget> budgets = budgetRepository.findByUserAndBudgetMonth(user, currentMonth);
        
        LocalDateTime startOfMonth = currentMonth.atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = currentMonth.atEndOfMonth().atTime(23, 59, 59);
        
        for (Budget budget : budgets) {
            BigDecimal spent = transactionRepository.calculateTotalByUserAndTypeInPeriod(
                user, TransactionType.EXPENSE, startOfMonth, endOfMonth);
            
            if (spent != null && spent.compareTo(budget.getAmount()) > 0) {
                // Here you would implement notification logic
                // For example, sending an email or creating a notification record
                System.out.println("Budget exceeded for category: " + budget.getCategory());
            }
        }
    }

    private BudgetDTO convertToDTO(Budget budget) {
        BudgetDTO dto = new BudgetDTO();
        dto.setId(budget.getId());
        dto.setAmount(budget.getAmount());
        dto.setCategory(budget.getCategory());
        dto.setBudgetMonth(budget.getBudgetMonth());
        dto.setUserId(budget.getUser().getId());
        
        // Calculate spent and remaining amounts
        LocalDateTime startOfMonth = budget.getBudgetMonth().atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = budget.getBudgetMonth().atEndOfMonth().atTime(23, 59, 59);
        
        BigDecimal spent = transactionRepository.calculateTotalByUserAndTypeInPeriod(
            budget.getUser(), TransactionType.EXPENSE, startOfMonth, endOfMonth);
        
        dto.setSpentAmount(spent != null ? spent : BigDecimal.ZERO);
        dto.setRemainingAmount(budget.getAmount().subtract(dto.getSpentAmount()));
        
        return dto;
    }
} 