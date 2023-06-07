package com.ateam.backend.services;

import com.ateam.backend.components.JwtUtilities;
import com.ateam.backend.models.appUser.AppUser;
import com.ateam.backend.models.AuthenticationRequest;
import com.ateam.backend.models.AuthenticationResponse;
import com.ateam.backend.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtUtilities jwtUtilities;
    private final JpaUserDetailsService jpaUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(UserRepository userRepository, JwtUtilities jwtUtilities, JpaUserDetailsService jpaUserDetailsService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtilities = jwtUtilities;
        this.jpaUserDetailsService = jpaUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest authenticationRequest) {
        Optional<AppUser> myUser = userRepository.findByUsernameOrEmail(authenticationRequest.getUsername(),
                authenticationRequest.getUsername());

        if (!myUser.get().isActive()) {
            return ResponseEntity.status(400).body(new AuthenticationResponse("User is not activated"));
        }

        if(!myUser.isPresent()) {
            return ResponseEntity.status(400).body(new AuthenticationResponse("Token wasn\'t generated, username is incorrect or is empty and password can not be reached."));
        } else {
            String password = myUser.get().getPassword();
            if (myUser.isPresent() && !passwordDecoder(authenticationRequest.getPassword(), password)) {
                return ResponseEntity.status(400).body(new AuthenticationResponse("Token wasn\'t generated, password is incorrect or is empty"));
            }
        }

        final UserDetails userDetails = jpaUserDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        var token = jwtUtilities.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

    private boolean passwordDecoder(String password, String password1) {
        boolean matches = passwordEncoder.matches(password, password1);

        return matches;
    }
}
