package com.ateam.backend.services;

import com.ateam.backend.models.quiz.Question;
import com.ateam.backend.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface QuestionService {
    Question save (Question question) ;

    Optional<Question> findById(Long id);

    List<Question> findByDeletedFalse();
}
