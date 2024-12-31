package com.financeTracker.PersonelFinanceTracker.controller;

import com.financeTracker.PersonelFinanceTracker.dto.BudgetDTO;
import com.financeTracker.PersonelFinanceTracker.service.BudgetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/api/budgets")
@RequiredArgsConstructor
@Tag(name = "Budget Management", description = "APIs for managing budgets")
public class BudgetController {

    private final BudgetService budgetService;

    @PostMapping
    @Operation(summary = "Create budget", description = "Creates a new budget")
    public ResponseEntity<BudgetDTO> createBudget(@Valid @RequestBody BudgetDTO budgetDTO) {
        return new ResponseEntity<>(budgetService.createBudget(budgetDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{budgetId}")
    @Operation(summary = "Update budget", description = "Updates an existing budget")
    public ResponseEntity<BudgetDTO> updateBudget(
            @PathVariable Long budgetId,
            @Valid @RequestBody BudgetDTO budgetDTO) {
        return ResponseEntity.ok(budgetService.updateBudget(budgetId, budgetDTO));
    }

    @DeleteMapping("/{budgetId}")
    @Operation(summary = "Delete budget", description = "Deletes a budget")
    public ResponseEntity<Void> deleteBudget(@PathVariable Long budgetId) {
        budgetService.deleteBudget(budgetId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{budgetId}")
    @Operation(summary = "Get budget by ID", description = "Retrieves budget information by ID")
    public ResponseEntity<BudgetDTO> getBudgetById(@PathVariable Long budgetId) {
        return ResponseEntity.ok(budgetService.getBudgetById(budgetId));
    }

    @GetMapping("/user/{userId}/month/{month}")
    @Operation(summary = "Get budgets by month", description = "Retrieves all budgets for a user in a specific month")
    public ResponseEntity<List<BudgetDTO>> getBudgetsByMonth(
            @PathVariable Long userId,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM") YearMonth month) {
        return ResponseEntity.ok(budgetService.getBudgetsByUserAndMonth(userId, month));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get all budgets", description = "Retrieves all budgets for a user")
    public ResponseEntity<List<BudgetDTO>> getAllBudgets(@PathVariable Long userId) {
        return ResponseEntity.ok(budgetService.getAllBudgetsByUser(userId));
    }

    @GetMapping("/user/{userId}/category/{category}/month/{month}")
    @Operation(summary = "Get budget by category and month", description = "Retrieves budget for a specific category and month")
    public ResponseEntity<BudgetDTO> getBudgetByCategory(
            @PathVariable Long userId,
            @PathVariable String category,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM") YearMonth month) {
        return ResponseEntity.ok(budgetService.getBudgetByUserAndCategoryAndMonth(userId, category, month));
    }

    @PostMapping("/user/{userId}/check-limits")
    @Operation(summary = "Check budget limits", description = "Checks if any budget limits have been exceeded")
    public ResponseEntity<Void> checkBudgetLimits(@PathVariable Long userId) {
        budgetService.checkBudgetLimits(userId);
        return ResponseEntity.ok().build();
    }
} 