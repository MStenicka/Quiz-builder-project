package com.ateam.backend.repositories;

import com.ateam.backend.models.quiz.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository <Question, Long>{
    Question save(Question question);

    List<Question> findByDeletedFalse();
}