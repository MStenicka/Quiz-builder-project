package com.ateam.backend.services;

import com.ateam.backend.models.quiz.Quiz;
import com.ateam.backend.models.quiz.QuizDetailsDto;
import com.ateam.backend.models.quiz.QuizStatus;
import com.ateam.backend.models.quiz.QuizVisibility;

import java.util.List;
import java.util.Optional;

public interface QuizService {

    List<Quiz> findByDeletedFalse();

    Optional<Quiz> findById(Long id);


    List<Quiz> getQuizzesByAuthor(String author);

    List<Quiz> getPublicAndEnabledQuizzesWithoutAuthorQuizzes(QuizVisibility visibility, QuizStatus status, String author);

    QuizDetailsDto getQuizDetails(Long id);

    Quiz save(Quiz quiz);



}
