package com.ateam.backend.repositories;

import com.ateam.backend.models.quiz.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Answer save(Answer answer);

    Optional<Answer> findById(Long id);

    List<Answer> findByDeletedFalse();
}

