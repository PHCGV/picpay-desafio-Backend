package com.pedrogaspari.picpaydesafiobackend.controllers;

import com.pedrogaspari.picpaydesafiobackend.DTOs.TransactionDTO;
import com.pedrogaspari.picpaydesafiobackend.DTOs.UserDTO;
import com.pedrogaspari.picpaydesafiobackend.domain.transaction.Transaction;
import com.pedrogaspari.picpaydesafiobackend.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping
    private ResponseEntity<Transaction> createTransaction(@RequestBody TransactionDTO transaction) throws Exception {
        Transaction newTransaction = this.transactionService.createTransaction(transaction);
        return new ResponseEntity<>(newTransaction, HttpStatus.OK);

    }
}
