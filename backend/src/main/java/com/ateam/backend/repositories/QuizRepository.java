package com.ateam.backend.repositories;

import com.ateam.backend.models.quiz.Quiz;
import com.ateam.backend.models.quiz.QuizStatus;
import com.ateam.backend.models.quiz.QuizVisibility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository <Quiz, Long>{
    List<Quiz> findAll();

    @Override
    Optional<Quiz> findById(Long aLong);

    Optional<Quiz> findByTitle(String title);

    List<Quiz> findByAuthor(String author);

    List<Quiz> findByVisibilityAndStatusAndAuthorNot(QuizVisibility visibility, QuizStatus status, String author);

    List<Quiz> findByDeletedFalse();
}
