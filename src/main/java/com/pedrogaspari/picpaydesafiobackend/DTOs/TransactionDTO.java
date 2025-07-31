package com.pedrogaspari.picpaydesafiobackend.DTOs;

import java.math.BigDecimal;

public record TransactionDTO(BigDecimal value, Long senderId, Long receiverId) {

    public TransactionDTO {
        if (value == null || value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor deve ser maior que zero");
        }
        if (senderId == null || receiverId == null) {
            throw new IllegalArgumentException("Sender and receiver IDs must not be null");
        }
    }
}
