package com.ateam.backend.services;

import com.ateam.backend.exceptions.QuizNotFoundException;
import com.ateam.backend.exceptions.UserNotFoundException;
import com.ateam.backend.models.appUser.AppUser;
import com.ateam.backend.models.appUser.SecurityUser;
import com.ateam.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public JpaUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<AppUser> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<AppUser> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> optionalFindByUsername = userRepository.findByUsername(username);
        Optional<AppUser> optionalFindByEmail = userRepository.findByEmail(username);
        UserDetails userDetails = null;
        if (optionalFindByUsername.isPresent()) {
            userDetails = userRepository
                    .findByUsername(username)
                    .map(SecurityUser::new)
                    .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));}

        if(optionalFindByEmail.isPresent()) {
                userDetails = userRepository
                        .findByEmail(username)
                        .map(SecurityUser::new)
                        .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
            }

        return userDetails;
    }

    public Optional<AppUser> findByUsernameOrEmail(String username, String email) {
        return userRepository.findByUsernameOrEmail(username, email);
    }
}