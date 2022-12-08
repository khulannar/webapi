package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {
    private String transactionId;
    private Customer customer;
    private int totalAmount;

    /*
     * 0  ---- 50  ---- 100 --- 120---
     * */
    public void processReward() {
        int point1 = 0, point2 = 0;
        if (totalAmount >= 50 && totalAmount <= 100) {
            point1 = totalAmount - 50;
        } else if (totalAmount > 100) {
            point1 = 50;
            point2 = 2 * (totalAmount - 100);
        }

        if (customer.getReward() == null)
            customer.setReward(new Reward());
        customer.getReward().points = point2 + point1;
    }
}
