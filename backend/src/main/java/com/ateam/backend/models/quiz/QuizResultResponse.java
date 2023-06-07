package com.ateam.backend.models.quiz;

public class QuizResultResponse {
    private int score;

    public QuizResultResponse() {}

    public QuizResultResponse(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}