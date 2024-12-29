package com.hmsapp.controller;

import com.hmsapp.entity.User;
import com.hmsapp.payload.LoginDto;
import com.hmsapp.repository.UserRepository;
import com.hmsapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")

public class AuthController {
    private final UserRepository userRepository;

    private final UserService userService;

    public AuthController(UserRepository userRepository, UserService userService){
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> createUser(
        @RequestBody User user
        ){

        Optional<User> opUsername = userRepository.findByUsername(user.getUsername());
        if(opUsername.isPresent()){
            return new ResponseEntity("Username already exists", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Optional<User> opEmail = userRepository.findByEmail(user.getEmail());
        if(opEmail.isPresent()){
            return new ResponseEntity("Email already exists", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Optional<User> opMobile = userRepository.findByMobile(user.getMobile());
        if(opMobile.isPresent()){
            return new ResponseEntity("Mobile already exists", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // This line is used for the encryption of the password
        //Here BCrypt.hashpw() & the BCrypt.gensalt(10) this two methods are used to encrypt the password
        user.setPassword(BCrypt.hashpw(user.getPassword(),BCrypt.gensalt(10)));
        User savedUser = userRepository.save(user);

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }


//  This code is used only for the message after the sign-up
//    @GetMapping("/message")
//    public String getMessage(){
//        return "Hello";
//    }

// *************** This code is used for the Verify login and if the username & the password is valid then it return the valid otherwise it returns the invalid     //

//    @PostMapping("/login")
//    public String login(@RequestBody LoginDto loginDto){
//
//        boolean val = userService.verifyLogin(loginDto);
//        if(val){
//            return "Login Successful";
//        }
//        return "Invalid Username/Password";
//    }


        @PostMapping("/login")
        public ResponseEntity<?> login(@RequestBody LoginDto loginDto){

            String token = userService.verifyLogin(loginDto);
            if(token!=null){
                return new ResponseEntity<>(token, HttpStatus.OK);
            }
            return new ResponseEntity<>("Invalid", HttpStatus.INTERNAL_SERVER_ERROR);
        }

}
