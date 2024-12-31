package com.financeTracker.PersonelFinanceTracker.controller;

import com.financeTracker.PersonelFinanceTracker.dto.TransactionDTO;
import com.financeTracker.PersonelFinanceTracker.entity.TransactionType;
import com.financeTracker.PersonelFinanceTracker.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@Tag(name = "Transaction Management", description = "APIs for managing transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    @Operation(summary = "Create transaction", description = "Creates a new transaction")
    public ResponseEntity<TransactionDTO> createTransaction(@Valid @RequestBody TransactionDTO transactionDTO) {
        return new ResponseEntity<>(transactionService.createTransaction(transactionDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{transactionId}")
    @Operation(summary = "Update transaction", description = "Updates an existing transaction")
    public ResponseEntity<TransactionDTO> updateTransaction(
            @PathVariable Long transactionId,
            @Valid @RequestBody TransactionDTO transactionDTO) {
        return ResponseEntity.ok(transactionService.updateTransaction(transactionId, transactionDTO));
    }

    @DeleteMapping("/{transactionId}")
    @Operation(summary = "Delete transaction", description = "Deletes a transaction")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long transactionId) {
        transactionService.deleteTransaction(transactionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{transactionId}")
    @Operation(summary = "Get transaction by ID", description = "Retrieves transaction information by ID")
    public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable Long transactionId) {
        return ResponseEntity.ok(transactionService.getTransactionById(transactionId));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get all transactions by user", description = "Retrieves all transactions for a user")
    public ResponseEntity<List<TransactionDTO>> getAllTransactionsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(transactionService.getAllTransactionsByUser(userId));
    }

    @GetMapping("/user/{userId}/type/{type}")
    @Operation(summary = "Get transactions by type", description = "Retrieves transactions by type and date range")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByType(
            @PathVariable Long userId,
            @PathVariable TransactionType type,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(transactionService.getTransactionsByUserAndType(userId, type, startDate, endDate));
    }

    @GetMapping("/user/{userId}/total/{type}")
    @Operation(summary = "Calculate total by type", description = "Calculates total amount by transaction type")
    public ResponseEntity<BigDecimal> calculateTotalByType(
            @PathVariable Long userId,
            @PathVariable TransactionType type,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(transactionService.calculateTotalByUserAndType(userId, type, startDate, endDate));
    }

    @GetMapping("/user/{userId}/category/{category}")
    @Operation(summary = "Get transactions by category", description = "Retrieves transactions by category")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByCategory(
            @PathVariable Long userId,
            @PathVariable String category) {
        return ResponseEntity.ok(transactionService.getTransactionsByUserAndCategory(userId, category));
    }
} 