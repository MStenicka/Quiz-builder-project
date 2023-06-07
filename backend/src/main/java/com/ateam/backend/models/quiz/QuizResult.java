package com.ateam.backend.models.quiz;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class QuizResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long quizId;
    private String username;
    private Integer score;
    private int quizSuccessRate;
    private int numOfQuestions;

    public QuizResult(Long quizId, String username, Integer score, int quizSuccessRate, int numOfQuestions) {
        this.quizId = quizId;
        this.username = username;
        this.score = score;
        this.quizSuccessRate = quizSuccessRate;
        this.numOfQuestions = numOfQuestions;
    }
    public QuizResult() {
    }
    public Long getQuizId() {
        return quizId;
    }
    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public int getQuizSuccessRate() {
        return quizSuccessRate;
    }

    public void setQuizSuccessRate(int quizSuccessRate) {
        this.quizSuccessRate = quizSuccessRate;
    }
}
