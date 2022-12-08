package com.example.demo.controller;

import com.example.demo.model.Customer;
import com.example.demo.model.Transaction;
import com.example.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionController {
    @Autowired
    private TransactionService service;

    @PostMapping("transaction")
    public ResponseEntity<?> purchase(@RequestBody Transaction transaction) {
        service.purchase(transaction);
        return ResponseEntity.ok(transaction);
    }

//    @GetMapping("customer/{customerId}")
//    public ResponseEntity<Customer> getCustomer(@PathVariable("customerId") String customerId) {
//        return ResponseEntity.ok(service.getCustomer(customerId));
//    }

    @GetMapping("transaction/{customerId}")
    public List<Transaction> getTransactions(@PathVariable("customerId") String customerId) {
        return service.getTransactions(customerId);
    }

    @GetMapping("transaction")
    public List<Transaction> getTransactions() {
        return service.getTransactions();
    }

}
