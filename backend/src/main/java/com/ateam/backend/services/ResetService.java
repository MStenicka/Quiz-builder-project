package com.ateam.backend.services;

import com.ateam.backend.models.appUser.AppUser;
import com.ateam.backend.models.appUser.PasswordResetReqData;
import com.ateam.backend.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ResetService {

    private final UserRepository userRepository;

    private EmailSenderService emailSenderService;

    private final PasswordEncoder encoder;

    public ResetService(UserRepository userRepository, EmailSenderService emailSenderService, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.emailSenderService = emailSenderService;
        this.encoder = encoder;
    }

    public Optional<AppUser> sendResetCode(PasswordResetReqData passwordResetReqData) {
        String resetCode = RandomStringUtils.randomAlphanumeric(48);
        System.out.println("resetCode: " + resetCode);
        Optional<AppUser> user;
        if (passwordResetReqData.getUsername() != null) {
            user = userRepository.findByUsername(passwordResetReqData.getUsername());
        }
        else {
            user = userRepository.findByEmail(passwordResetReqData.getEmail());
        }

        user.get().setResetCode(resetCode);
        userRepository.save(user.get());

        emailSenderService.sendEmail(user.get().getEmail(), "Quiz Builder Password reset code", "Use the following reset code to reset your password: " + resetCode);
        return user;
    }

    public Boolean validatePassword(String resetCode, String password) {
        Optional<AppUser> user = userRepository.findByResetCode(resetCode);
        if (!user.isPresent()) {
            return false;
        }

        AppUser appUser = user.get();
        appUser.setPassword(encoder.encode(password));
        appUser.setResetCode(null);
        userRepository.save(appUser);

        return true;
    }
}
