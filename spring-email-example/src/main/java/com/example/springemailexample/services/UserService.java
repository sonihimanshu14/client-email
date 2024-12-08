package com.example.springemailexample.services;
import com.example.springemailexample.entity.UserAdmin;
import com.example.springemailexample.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;



    @Service
    public class UserService implements UserDetailsService {

        private final UserRepository userRepository; // Inject the UserRepository

        public UserService(UserRepository userRepository) {
            this.userRepository = userRepository;
        }

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            // Fetch user from database
            UserAdmin user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            // Return a UserDetails object containing the user's username, password, and roles
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")) // Example role; you can make it dynamic based on the user
            );
        }
    }
