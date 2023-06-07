package com.ateam.backend.services;

import com.ateam.backend.models.quiz.QuizResult;
import com.ateam.backend.models.quiz.QuizResultSummaryDto;
import com.ateam.backend.repositories.QuizResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizResultService {
    @Autowired
    private QuizResultRepository quizResultRepository;

    public QuizResult saveQuizResult(QuizResult quizResult) {
        return quizResultRepository.save(quizResult);
    }

    public List<QuizResultSummaryDto> getPlayersResultsByQuizIdAndByScoreDesc(Long quizId, Integer capacity) {
        return quizResultRepository.findByQuizIdOrderByScoreDesc(quizId, PageRequest.of(0, capacity));
    }
}
