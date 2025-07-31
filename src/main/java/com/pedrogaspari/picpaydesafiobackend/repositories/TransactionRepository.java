package com.pedrogaspari.picpaydesafiobackend.repositories;

import com.pedrogaspari.picpaydesafiobackend.domain.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {

}
