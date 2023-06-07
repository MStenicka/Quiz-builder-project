package com.ateam.backend.services;

import com.ateam.backend.models.quiz.Answer;
import com.ateam.backend.repositories.AnswerRepository;
import com.ateam.backend.repositories.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DatabaseAnswerService extends AnswerService {
    public final AnswerRepository answerRepository;
    public final QuestionRepository questionRepository;

    public DatabaseAnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public Answer save(Answer answer) {
        return answerRepository.save(answer);
    }
}
