package com.message.Repository;

import com.message.Entity.Users;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {

   Optional<Users> findByEmail(String email);
   Optional<Users> findByMobile(String mobile);
   Optional<Users> findByUsername(String username);
  // Users usernameExist(String username);
}