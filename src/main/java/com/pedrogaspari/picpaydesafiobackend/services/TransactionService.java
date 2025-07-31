package com.pedrogaspari.picpaydesafiobackend.services;

import com.pedrogaspari.picpaydesafiobackend.DTOs.TransactionDTO;
import com.pedrogaspari.picpaydesafiobackend.domain.transaction.Transaction;
import com.pedrogaspari.picpaydesafiobackend.domain.user.User;
import com.pedrogaspari.picpaydesafiobackend.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TransactionService {
    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NotificationService notificationService;

    public Transaction createTransaction(TransactionDTO transaction) throws Exception {
        User sender = userService.findUserById(transaction.senderId());
        User receiver = userService.findUserById(transaction.receiverId());
        userService.ValidateTransaction(sender, transaction.value());


        boolean isAuthorized = this.AuthorizeTransaction(sender, transaction.value());
        if(!isAuthorized) {
            throw new Exception("Transação não autorizada");
        }

        Transaction transactionEntity = new Transaction();
        transactionEntity.setSender(sender);
        transactionEntity.setReceiver(receiver);
        transactionEntity.setAmount(transaction.value());
        transactionEntity.setTimestamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transaction.value()));
        receiver.setBalance(receiver.getBalance().add(transaction.value()));

        this.repository.save(transactionEntity);
        this.userService.saveUser(sender);
        this.userService.saveUser(receiver);

        this.notificationService.sendNotification(sender, "Transação realizada com sucesso!");
        this.notificationService.sendNotification(receiver, "Você recebeu uma transação de " + sender.getName() + " no valor de " + transaction.value());


        return transactionEntity;
    }

    public boolean AuthorizeTransaction(User sender, BigDecimal value) throws Exception {
                ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize", Map.class);
        if (authorizationResponse.getStatusCode() == HttpStatus.OK && authorizationResponse.getBody().get("status").equals("success")) {
            String message = (String) authorizationResponse.getBody().get("message");
            return true;
        } else return false;
    }
}
