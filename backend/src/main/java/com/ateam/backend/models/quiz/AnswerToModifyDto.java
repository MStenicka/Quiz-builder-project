package com.ateam.backend.models.quiz;

import java.util.List;

public class AnswerToModifyDto {

    private String title;
    private String description;
    private boolean isCorrect;

    private List<AnswerDetailsDto> answerDetails;

    public AnswerToModifyDto(String title, String description, boolean isCorrect) {
        this.title = title;
        this.description = description;
        this.isCorrect = isCorrect;
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
