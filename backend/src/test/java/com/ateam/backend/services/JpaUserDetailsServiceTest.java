package com.ateam.backend.services;

import com.ateam.backend.repositories.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class JpaUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;
    private JpaUserDetailsService underTest;

    @BeforeEach
    void setUp() {
        underTest = new JpaUserDetailsService(userRepository);
    }

    @Test
    void findsUserByUsernameOrEmail() {
        underTest.findByUsernameOrEmail("Mr.T", "mr.t@bigguy.com");

        verify(userRepository).findByUsernameOrEmail("Mr.T", "mr.t@bigguy.com");
    }
}