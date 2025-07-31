package com.pedrogaspari.picpaydesafiobackend.DTOs;

import com.pedrogaspari.picpaydesafiobackend.domain.user.UserType;

import java.math.BigDecimal;

public record UserDTO(String name, String CPF, BigDecimal balance, String email, String password, UserType userType) {

}
