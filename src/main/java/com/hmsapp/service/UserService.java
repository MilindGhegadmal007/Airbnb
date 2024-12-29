package com.hmsapp.service;

import com.hmsapp.entity.User;
import com.hmsapp.payload.LoginDto;
import com.hmsapp.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private JWTService jwtService;


    public UserService(UserRepository userRepository, JWTService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    // b=verifyLogin function is used for the login purpose
//    public boolean verifyLogin(
//            LoginDto loginDto
//    ) {
//        Optional<User> opUser = userRepository.findByUsername(loginDto.getUsername());
//        if (opUser.isPresent()) {
//            User user = opUser.get();
//
//            if (BCrypt.checkpw(loginDto.getPassword(), user.getPassword())) {
//
//                return true;
//            } else {
//                return false;
//            }
//
//        }
//
//        return false;
//    }





    public String verifyLogin(
            LoginDto loginDto
    ) {
        Optional<User> opUser = userRepository.findByUsername(loginDto.getUsername());
        if (opUser.isPresent()) {
            User user = opUser.get();

            if (BCrypt.checkpw(loginDto.getPassword(), user.getPassword())) {
                String token = jwtService.generateToken(user.getUsername());
                return token;
            } else {
                return null;
            }

        }

        return null;
    }
}
