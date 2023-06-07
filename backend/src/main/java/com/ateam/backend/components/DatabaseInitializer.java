package com.ateam.backend.components;

import com.ateam.backend.models.appUser.AppUser;
import com.ateam.backend.models.appUser.Role;
import com.ateam.backend.models.quiz.*;
import com.ateam.backend.repositories.AnswerRepository;
import com.ateam.backend.repositories.QuestionRepository;
import com.ateam.backend.repositories.QuizRepository;
import com.ateam.backend.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final AnswerRepository answerRepository;
    private  final QuestionRepository questionRepository;

    private final QuizRepository quizRepository ;

    public DatabaseInitializer(UserRepository userRepository, PasswordEncoder encoder,  QuizRepository quizRepository,AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if(userRepository.count() == 0 &&
                questionRepository.count() == 0 &&
                answerRepository.count() == 0 &&
                quizRepository.count() == 0) {

            AppUser user1 = new AppUser(1L, "Mr.T", encoder.encode("boss"), "mr.t@bigguy.com", null, 123456789L, "Laurence Tureaud", Role.ADMIN, true);
            userRepository.save(user1);
            AppUser user2 = new AppUser(2L, "John", encoder.encode("colonel"), "john@smith.com", null, 987654321L, "Detroit", Role.USER, true);
            userRepository.save(user2);

            List<AppUser> first = new ArrayList<>();
            first.add(user2);
            List<AppUser> second = new ArrayList<>();
            first.add(user1);

            Quiz quiz1 = new Quiz(1L, "Capital quiz", "See how well you know Europe", QuizStatus.ENABLED, QuizVisibility.PUBLIC, QuizGrading.POINT_PER_ANSWER, user1, true, first);
            quiz1 = quizRepository.save(quiz1);

            quizRepository.save(new Quiz(2L, "H", "See how", QuizStatus.ENABLED, QuizVisibility.PUBLIC, QuizGrading.POINT_PER_ANSWER, user2, true, second));

            Question question1 = new Question(1L, quiz1, QuestionType.SINGLE_CHOICE, "What is the answer to universe?", "Choose the correct option from the following choices:", true);
            question1 = questionRepository.save(question1);

            Answer answer11 = new Answer(1L, question1, 42, "The answer to universe is:", true);
            answer11 = answerRepository.save(answer11);

            Answer answer12 = new Answer(2L, question1, 2541, "The answer to universe is:", false);
            answer12 = answerRepository.save(answer12);

            Question question2 = new Question(2L, quiz1, QuestionType.MULTIPLE_CHOICE, "What is the capital of Slovakia?", "Choose the correct option from the following choices:", false);
            question2 = questionRepository.save(question2);

            Answer answer21 = new Answer(3L, question2, "Bratislava", "The capital of Slovakia is:", true);
            answer21 = answerRepository.save(answer21);

            Answer answer22 = new Answer(4L, question2, "Praha", "The capital of Slovakia is:", false);
            answer22 = answerRepository.save(answer22);

            Answer answer23 = new Answer(5L, question2, "Prešporok", "The capital of Slovakia is:", true);
            answer23 = answerRepository.save(answer23);

            Question question3 = new Question(3L, quiz1, QuestionType.FILL_OUT, "What is the currency of Slovakia?", "Enter your answer:", true);
            question3 = questionRepository.save(question3);

            Answer answer31 = new Answer(6L, question3, "Euro", "The currency of Slovakia is:", true);
            answer31 = answerRepository.save(answer31);

            question1.addCorrectAnswerId(answer11.getId());
            questionRepository.save(question1);

            question2.addCorrectAnswerId(answer21.getId());
            question2.addCorrectAnswerId(answer23.getId());
            questionRepository.save(question2);

            question3.addCorrectAnswerId(answer31.getId());
            question3 = questionRepository.save(question3);
        }
    }
}
