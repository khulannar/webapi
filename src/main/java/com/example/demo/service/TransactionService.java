package com.example.demo.service;

import com.example.demo.model.Customer;
import com.example.demo.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    Map<String, Transaction> map = new HashMap<>(){{
        put("txn0001", Transaction.builder().transactionId("txn0001").totalAmount(120).customer(Customer.builder().customerId("C002").build()).build());
        put("txn0002", Transaction.builder().transactionId("txn0002").totalAmount(110).customer(Customer.builder().customerId("C002").build()).build());
        put("txn0003", Transaction.builder().transactionId("txn0003").totalAmount(80).customer(Customer.builder().customerId("C005").build()).build());
    }};

    public Transaction purchase(Transaction txn) {
        txn.processReward();
        map.put(txn.getTransactionId(), txn);
        return txn;
    }

    public Customer getCustomer(String customerId) {
        return map.values().stream().filter(txn -> txn.getCustomer().getCustomerId().equals(customerId)).map(txn -> txn.getCustomer()).findFirst().get();
    }

    public List<Transaction> getTransactions(String customerId) {
        return map.values().stream().filter(txn -> txn.getCustomer().getCustomerId().equals(customerId)).collect(Collectors.toList());
    }

    public List<Transaction> getTransactions() {
        return map.values().stream().collect(Collectors.toList());
    }
}
