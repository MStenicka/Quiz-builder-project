package com.ateam.backend.models.quiz;


import java.util.List;

public class QuizToCreateOrModifyDto {
    private String title;
    private String description;
    private QuizStatus statusOfQuiz;
    private QuizVisibility visibility;
    private QuizGrading grading;

    private List<Long> allowedUsersIds;

    public QuizToCreateOrModifyDto(String title, String description, QuizStatus status, QuizVisibility visibility, QuizGrading grading) {
        this.title = title;
        this.description = description;
        this.statusOfQuiz = status;
        this.visibility = visibility;
        this.grading = grading;
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

    public QuizStatus getStatusOfQuiz() {
        return statusOfQuiz;
    }

    public void setStatusOfQuiz(QuizStatus statusOfQuiz) {
        this.statusOfQuiz = statusOfQuiz;
    }

    public QuizVisibility getVisibility() {
        return visibility;
    }

    public void setVisibility(QuizVisibility visibility) {
        this.visibility = visibility;
    }

    public QuizGrading getGrading() {
        return grading;
    }

    public void setGrading(QuizGrading grading) {
        this.grading = grading;
    }

    public List<Long> getAllowedUsersIds() {
        return allowedUsersIds;
    }

    public void setAllowedUsersIds(List<Long> allowedUsersIds) {
        this.allowedUsersIds = allowedUsersIds;
    }
}
