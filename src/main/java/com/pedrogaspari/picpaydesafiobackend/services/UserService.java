package com.pedrogaspari.picpaydesafiobackend.services;

import com.pedrogaspari.picpaydesafiobackend.DTOs.UserDTO;
import com.pedrogaspari.picpaydesafiobackend.domain.user.User;
import com.pedrogaspari.picpaydesafiobackend.domain.user.UserType;
import com.pedrogaspari.picpaydesafiobackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public void ValidateTransaction(User sender, BigDecimal amount) throws Exception {
        if(sender.getUserType() == UserType.MERCHANT){
            throw new Exception("Lojistas não podem realizar transações");
        }

        if(sender.getBalance().compareTo(amount) == 0){
            throw new Exception("Saldo insuficiente");
        }
    }

    public User createUser(UserDTO data) {
        User newUser = new User(data);
        this.saveUser(newUser);
        return newUser;
    }

    public List<User> getAllUsers() {
        return this.repository.findAll();
    }

    public User findUserById(Long id) throws Exception {
        return this.repository.findUserById(id).orElseThrow(() -> new Exception("Usuário não encontrado"));
    }

    public void saveUser(User user) {
        this.repository.save(user);
    }
}
