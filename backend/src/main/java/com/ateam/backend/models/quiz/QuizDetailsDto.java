package com.ateam.backend.models.quiz;

import java.util.List;

public class QuizDetailsDto {

    private Long id;
    private String title;
    private List<QuestionDetailsDto> questionDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<QuestionDetailsDto> getQuestionDetails() {
        return questionDetails;
    }

    public void setQuestionDetails(List<QuestionDetailsDto> questionDetails) {
        this.questionDetails = questionDetails;
    }
}
