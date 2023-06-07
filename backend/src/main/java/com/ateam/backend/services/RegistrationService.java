package com.ateam.backend.services;

import com.ateam.backend.models.appUser.AppUser;
import com.ateam.backend.repositories.UserRepository;
import jakarta.validation.ValidationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class RegistrationService {

    private final UserRepository userRepository;

    private final EmailSenderService emailService;

    private final PasswordEncoder encoder;

    public RegistrationService(UserRepository userRepository, EmailSenderService emailService, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.encoder = encoder;
    }

    public void register(AppUser user) throws ValidationException {
        if (user.getUsername() == null || !StringUtils.hasText(user.getUsername())) {
            throw new ValidationException("User name is empty");
        }
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new ValidationException("User already exists");
        }
        if (user.getPassword() == null || !StringUtils.hasText(user.getPassword())) {
            throw new ValidationException("Password is empty");
        }
        if (user.getEmail() == null || !StringUtils.hasText(user.getEmail())) {
            throw new ValidationException("Email is empty");
        }
        if (user.getPassword().length() <= 8) {
            throw new ValidationException("Password should have at least eight characters");
        }
        if (!passwordContainsAllRequirements(user.getPassword())) {
            throw new ValidationException("Password must contain at least one capital letter, one numeric character, and one special character");
        }
        AppUser newUser = new AppUser(null, user.getUsername(), encoder.encode(user.getPassword()), user.getEmail(), null, user.getPhone(), user.getCity(), user.getRole(), false);

        String activationCode = GenerateAlphanumericCode.generate(48);
        System.out.println("activationCode: " + activationCode);

        newUser.setActivationCode(activationCode);
        userRepository.save(newUser);
        emailService.sendEmail
                (newUser.getEmail(), "Activate your account", "Please use the following activation code to activate your account: " + activationCode);
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

    public void activate(String username, String activationCode) throws ValidationException {
        Optional<AppUser> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new ValidationException("User does not exist");
        }
        if (user.get().getEmail().isEmpty()) {
            throw new ValidationException("email is missing");
        }
        if (user.get().getPassword().isEmpty()) {
            throw new ValidationException("User is missing");
        }
        if (user.get().isActive()) {
            throw new ValidationException("User is already active");
        }
        if (!activationCode.equals(user.get().getActivationCode())) {
            throw new ValidationException("Activation code is not correct");
        }
        AppUser activatedUser = user.get();
        activatedUser.setActive(true);
        activatedUser.setActivationCode(null);
        userRepository.save(activatedUser);
    }
}