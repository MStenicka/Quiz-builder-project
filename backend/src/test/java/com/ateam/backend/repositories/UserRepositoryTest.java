package com.ateam.backend.repositories;

import com.ateam.backend.BackendApplication;
import com.ateam.backend.models.appUser.AppUser;
import com.ateam.backend.models.appUser.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest(classes = BackendApplication.class)
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    void itFindsUserById() {
        AppUser user = new AppUser(1L, "Mr.T2", passwordEncoder.encode("boss2"), "mr.t2@bigguy.com", null, 123456789L, "Laurence Tureaud", Role.ADMIN, true);

        userRepository.save(user);

        assertEquals(userRepository.findById(1L).get().getUsername(), user.getUsername());
    }
}