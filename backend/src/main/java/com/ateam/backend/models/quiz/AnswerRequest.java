package com.ateam.backend.models.quiz;

public class AnswerRequest {
    private Long questionId;
    private Long answerId;
    private String answerText;
    public AnswerRequest() {}

    public AnswerRequest(Long questionId, Long answerId, String answerText) {
        this.questionId = questionId;
        this.answerId = answerId;
        this.answerText = answerText;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }
}