package com.ateam.backend.repositories;

import com.ateam.backend.models.quiz.QuizResult;
import com.ateam.backend.models.quiz.QuizResultSummaryDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {
    QuizResult save(QuizResult quizResult);

    List<QuizResultSummaryDto> findByQuizIdOrderByScoreDesc(Long quizId, Pageable pageable);
}
