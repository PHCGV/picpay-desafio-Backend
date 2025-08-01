package com.pedrogaspari.picpaydesafiobackend.repositories;

import com.pedrogaspari.picpaydesafiobackend.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findUserByCPF(String CPF);

    Optional<User> findUserById(Long id);
}
