package com.ateam.backend.controllers;

import com.ateam.backend.components.JwtRequestFilter;
import com.ateam.backend.models.appUser.AppUser;
import com.ateam.backend.models.appUser.Role;
import com.ateam.backend.repositories.AnswerRepository;
import com.ateam.backend.repositories.QuestionRepository;
import com.ateam.backend.repositories.QuizRepository;
import com.ateam.backend.repositories.UserRepository;
import com.ateam.backend.services.RegistrationService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(UserController.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private PasswordEncoder encoder;
    @MockBean
    private QuizRepository quizRepository;
    @MockBean
    private AnswerRepository answerRepository;
    @MockBean
    private QuestionRepository questionRepository;
    @MockBean
    private JwtRequestFilter jwtRequestFilter;
    @MockBean
    private RegistrationService registrationService;

    @Test
    @WithMockUser
    void findsAllUsers() throws Exception {
        this.mvc.perform(get("/all_users"))
                .andExpect(status().is(200));
    }

    @Test
    void findsUserByCorrectId() throws Exception {
        AppUser user = new AppUser(5L,
                "Mr.T5",
                "boss5",
                "mr.t5@bigguy.com",
                null,
                123456789L,
                "Laurence Tureaud",
                Role.ADMIN,
                true);

        given(userRepository.findById(5L)).willReturn(Optional.of(user));

        this.mvc.perform(get("/{id}", 5L))
                .andExpect(status().is(200));
    }

    @Test
    void DoesntFindUserByWrongId() throws Exception {
        AppUser user = new AppUser(5L,
                "Mr.T5",
                "boss5",
                "mr.t5@bigguy.com",
                null,
                123456789L,
                "Laurence Tureaud",
                Role.ADMIN,
                true);

        given(userRepository.findById(5L)).willReturn(Optional.of(user));

        this.mvc.perform(get("/{id}", 6L))
                .andExpect(status().is(400));
    }

    @Test
    void registerNewUser() throws Exception {
        String username = "username";
        String email = "username@useremail.com";
        String password = "Password1@";

        JSONObject requestJson = new JSONObject();
        requestJson.put("username", username);
        requestJson.put("password", password);
        requestJson.put("email", email);

        RequestBuilder request = MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson.toString());

        MvcResult result = mvc.perform(request)
                .andReturn();

        System.out.println("----------" + result);
    }
}