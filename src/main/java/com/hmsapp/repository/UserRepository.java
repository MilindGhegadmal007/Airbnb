package com.hmsapp.repository;

import com.hmsapp.entity.User;
import com.hmsapp.payload.LoginDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User>findByUsername(String username);
  Optional<User>findByEmail(String email);
  Optional<User>findByMobile(String mobile);


}