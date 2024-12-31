package com.financeTracker.PersonelFinanceTracker.repository;

import com.financeTracker.PersonelFinanceTracker.entity.Budget;
import com.financeTracker.PersonelFinanceTracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    List<Budget> findByUserAndBudgetMonth(User user, YearMonth budgetMonth);
    Optional<Budget> findByUserAndCategoryAndBudgetMonth(User user, String category, YearMonth budgetMonth);
    List<Budget> findByUserOrderByBudgetMonthDesc(User user);
    boolean existsByUserAndCategoryAndBudgetMonth(User user, String category, YearMonth budgetMonth);
} 