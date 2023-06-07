package com.ateam.backend.controllers;

import com.ateam.backend.exceptions.QuizNotFoundException;
import com.ateam.backend.exceptions.UserNotFoundException;
import com.ateam.backend.models.appUser.AppUser;
import com.ateam.backend.repositories.UserRepository;
import com.ateam.backend.services.RegistrationService;
import jakarta.validation.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Optional;

@RestController
public class UserController {
    private final UserRepository users;

    private final RegistrationService registrationService;

    public UserController(UserRepository users, RegistrationService registrationService) {
        this.users = users;
        this.registrationService = registrationService;
    }

    @GetMapping("/all_users")
    public Iterable<AppUser> findAll() {
        return users.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        Optional<AppUser> appUser = users.findById(id);
        if(!appUser.isPresent()) {
            return ResponseEntity.badRequest().body("User doesnt found!");
        }
        return ResponseEntity.ok().body(appUser.get());
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AppUser user) {
        System.out.println(user);
        try {
            registrationService.register(user);
            return ResponseEntity.ok().build();
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/confirm/{activationCode}")
    public ResponseEntity<String> activate(@RequestBody String username, @PathVariable String activationCode) {
        try {
            registrationService.activate(username, activationCode);
            return ResponseEntity.ok().build();
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
