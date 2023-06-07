package com.ateam.backend.services;

import com.ateam.backend.exceptions.QuestionNotFoundException;
import com.ateam.backend.exceptions.QuizNotFoundException;
import com.ateam.backend.models.quiz.Question;
import com.ateam.backend.repositories.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DatabaseQuestionService implements QuestionService {
    public final QuestionRepository questionRepository;

    public DatabaseQuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public Question save(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public Optional<Question> findById(Long id) {
        return questionRepository.findById(id);
    }

    @Override
    public List<Question> findByDeletedFalse() {
        return questionRepository.findByDeletedFalse();
    }
}