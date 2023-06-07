package com.ateam.backend.controllers;

import com.ateam.backend.components.JwtUtilities;
import com.ateam.backend.config.SecurityConfig;
import com.ateam.backend.repositories.UserRepository;
import com.ateam.backend.services.JpaUserDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityConfig.class)
@WebMvcTest(HomeRestController.class)
class HomeRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtUtilities jwtUtilities;

    @MockBean
    private JpaUserDetailsService jpaUserDetailsService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AuthenticationProvider authenticationProvider;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    void returnHello() throws Exception{
        this.mockMvc
                .perform(get("/hello"))
                .andExpect(status().is(200));
    }

    @Test
    @WithMockUser
    void user() throws Exception{
        this.mockMvc.perform(get("/user")).andExpect(status().is(200));
    }
}