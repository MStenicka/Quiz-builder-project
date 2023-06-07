
package com.ateam.backend.services;

import com.ateam.backend.models.quiz.Answer;
import com.ateam.backend.repositories.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {


    @Autowired
    private AnswerRepository answerRepository;

    public List<Answer> findByDeletedFalse() {
        return answerRepository.findByDeletedFalse();
    }

    public Answer findById(Long id) {
        Optional<Answer> optionalAnswer = answerRepository.findById(id);
        return optionalAnswer.orElse(null);
    }

    public Answer save(Answer answer) {
        return answerRepository.save(answer);
    }
}