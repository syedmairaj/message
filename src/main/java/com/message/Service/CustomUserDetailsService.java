package com.message.Service;

import com.message.Entity.Users;
import com.message.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users users = usersRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("username not found"));
//
//        Converting your DB user to UserDetails
//        Spring Security does not know about your Users entity. It only works with UserDetails.
        return new org.springframework.security.core.userdetails.User(
                users.getEmail(),
                users.getPassword(),
                List.of(new SimpleGrantedAuthority("Role_USER"))
        );


    }


}