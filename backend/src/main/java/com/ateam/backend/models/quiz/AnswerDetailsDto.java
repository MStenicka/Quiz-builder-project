package com.ateam.backend.models.quiz;

import jakarta.persistence.ManyToOne;

import java.util.List;

public class AnswerDetailsDto {


    private Long id;
    private String title;
    private String description;
    private boolean isCorrect;

    private List<AnswerDetailsDto> answerDetails;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
    public void setAnswerDetails(List<AnswerDetailsDto> answerDetails) {
        this.answerDetails = answerDetails;
    }
}
