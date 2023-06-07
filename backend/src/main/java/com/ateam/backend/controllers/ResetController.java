package com.ateam.backend.controllers;

import com.ateam.backend.models.appUser.PasswordResetReqData;
import com.ateam.backend.services.EmailSenderService;
import com.ateam.backend.services.ResetService;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class ResetController {
    private final ResetService resetService;
    private final EmailSenderService emailSenderService;

    @Autowired
    public ResetController(ResetService resetService, EmailSenderService emailSenderService) {
        this.resetService = resetService;
        this.emailSenderService = emailSenderService;
    }

    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordResetReqData data) {
        if (data.getUsername() == null && data.getEmail() == null) {
            return ResponseEntity.badRequest().body("You need to use username or email.");
        }
        resetService.sendResetCode(data);

        return ResponseEntity.ok().body("Reset code was sent to your email.");
    }

    @PostMapping("/reset/{resetCode}")
    public ResponseEntity<String> updatePassword(@PathVariable String resetCode, @RequestBody Map<String, String> requestParams) {
        String password = requestParams.get("password");
        if (password == null || password.isEmpty()) {
            return ResponseEntity.badRequest().body("Password cannot be empty");
        }

        if (!passwordContainsAllRequirements(password)){
            throw new ValidationException("Password must contain at least one capital letter, one numeric character, and one special character");
        }

        boolean success = resetService.validatePassword(resetCode, password);
        if (success) {
            return ResponseEntity.ok().body("Your password was changed.");
        } else {
            return ResponseEntity.badRequest().body("The reset code is not valid.");
        }
    }

    private boolean passwordContainsAllRequirements(String password) {
        String specialChars = "!@#$%^&*";
        boolean containsCapital = false;
        boolean containsNumber = false;
        boolean containsSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                containsCapital = true;
            }
            if (Character.isDigit(c)) {
                containsNumber = true;
            }
            if (specialChars.contains(String.valueOf(c))) {
                containsSpecial = true;
            }
            if (containsCapital && containsNumber && containsSpecial) {
                return true;
            }
        }
        return false;
    }
}

