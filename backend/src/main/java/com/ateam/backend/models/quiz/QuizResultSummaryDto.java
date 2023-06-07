package com.ateam.backend.models.quiz;

public class QuizResultSummaryDto {
    private String username;
    private Integer score;
    private int quizSuccessRate;

    public QuizResultSummaryDto(String username, Integer score, int quizSuccessRate) {
        this.username = username;
        this.score = score;
        this.quizSuccessRate = quizSuccessRate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public int getQuizSuccessRate() {
        return quizSuccessRate;
    }

    public void setQuizSuccessRate(int quizSuccessRate) {
        this.quizSuccessRate = quizSuccessRate;
    }

    @Override
    public String toString() {
        return "QuizResultSummaryDto{" +
                "username='" + username + '\'' +
                ", score=" + score +
                ", quizSuccessRate=" + quizSuccessRate +
                '}';
    }
}
