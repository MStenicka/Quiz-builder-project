package com.ateam.backend.controllers;

import com.ateam.backend.models.quiz.*;
import com.ateam.backend.repositories.QuizRepository;
import com.ateam.backend.repositories.QuizResultRepository;
import com.ateam.backend.services.PlayersStatisticsService;
import com.ateam.backend.services.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/quiz")
public class QuizResultController {
    private QuizService quizService;
    private QuizRepository quizRepository;
    private QuizResultRepository quizResultRepository;
    private PlayersStatisticsService playersStatisticsService;

    public QuizResultController(QuizService quizService, QuizRepository quizRepository, QuizResultRepository quizResultRepository, PlayersStatisticsService playersStatisticsService) {
        this.quizService = quizService;
        this.quizRepository = quizRepository;
        this.quizResultRepository = quizResultRepository;
        this.playersStatisticsService = playersStatisticsService;
    }

    @PostMapping("/{quizId}/submit")
    public ResponseEntity<?> submitQuiz(@PathVariable Long quizId, @RequestBody QuizResultRequest quizResultRequest, @AuthenticationPrincipal UserDetails userDetails) {
        if (quizResultRequest.getAnswers() == null || quizResultRequest.getAnswers().isEmpty()) {
            return ResponseEntity.badRequest().body("Answers not found!");
        }
        Optional<Quiz> quiz = quizRepository.findById(quizId);
        if (!quiz.isPresent()) {
            return ResponseEntity.badRequest().body("Quiz not found!");
        }
        List<AnswerRequest> answers = quizResultRequest.getAnswers();
        boolean allCorrect = quiz.get().checkAnswers(answers);
        int score = 0;

        List<Question> questions = answers.stream().map(AnswerRequest::getQuestionId)
                .distinct().map(q -> quiz.get().getQuestionById(q)).toList();
        for(Question question : questions) {
            List<Object> correctAnswerIds = question.getCorrectAnswerIds().stream().map(id -> question.getAnswer(id).getTitle()).toList();
            List<String> myAnwers = answers.stream().filter(answerRequest -> answerRequest.getQuestionId().equals(question.getId())).map(AnswerRequest::getAnswerText).toList();
            if(new HashSet<>(correctAnswerIds).containsAll(myAnwers)){
                score++;
            }
        }

        String username = userDetails.getUsername();
        int numOfQuestions = quiz.get().getQuestions().size();

        int quizSuccessRate = Math.round((float) score/numOfQuestions * 100);
        playersStatisticsService.updatePlayerStatistics(username, quizSuccessRate);

        QuizResult quizResult = new QuizResult(quiz.get().getId(), username, score, quizSuccessRate, numOfQuestions);
        quizResultRepository.save(quizResult);
        QuizResultResponse quizResultResponse = new QuizResultResponse(score);
        return ResponseEntity.ok(quizResultResponse);
    }}
