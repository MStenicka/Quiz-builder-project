package com.ateam.backend.models.quiz;

import java.util.List;

public class QuizResultRequest {
    private String quizTitle;
    private Long userId;
    private List<AnswerRequest> answers;

    public QuizResultRequest() {
    }

    public QuizResultRequest(String quizTitle, Long userId, List<AnswerRequest> answers) {
        this.quizTitle = quizTitle;
        this.userId = userId;
        this.answers = answers;
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    public void setQuizTitle(String quizTitle) {
        this.quizTitle = quizTitle;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<AnswerRequest> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerRequest> answers) {
        this.answers = answers;
    }
}