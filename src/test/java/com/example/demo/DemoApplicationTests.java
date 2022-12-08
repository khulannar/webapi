package com.example.demo;

import com.example.demo.model.Customer;
import com.example.demo.model.Transaction;
import com.example.demo.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DemoApplicationTests {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    TransactionService service;

    @Test
    @Order(1)
    public void postTxn() throws Exception {
        Transaction txn = Transaction.builder().transactionId("txn0004").totalAmount(120).customer(Customer.builder().customerId("C002").build()).build();
        RequestBuilder request = MockMvcRequestBuilders.post("/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(txn));
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalAmount", is(txn.getTotalAmount())));
    }

    @Test
    @Order(2)
    public void getCustomer() throws Exception {
        Transaction txn = Transaction.builder().transactionId("txn0004").totalAmount(120).customer(Customer.builder().customerId("C002").build()).build();
        RequestBuilder request = MockMvcRequestBuilders.post("/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(txn));

        given(service.purchase(txn)).willReturn(txn);

        mvc.perform(MockMvcRequestBuilders.get("/transaction")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].totalAmount", is(100)));

    }
}
