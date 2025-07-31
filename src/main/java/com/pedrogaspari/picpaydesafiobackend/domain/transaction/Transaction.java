package com.pedrogaspari.picpaydesafiobackend.domain.transaction;

import com.pedrogaspari.picpaydesafiobackend.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name= "transactions")
@Table(name="transactions")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of="id")
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private BigDecimal amount;
    @ManyToOne
    @JoinColumn(name="senderId", nullable = false)

    private User sender;
    @ManyToOne
    @JoinColumn(name="receiverId", nullable = false)
    private User receiver;
    private LocalDateTime timestamp;
}
