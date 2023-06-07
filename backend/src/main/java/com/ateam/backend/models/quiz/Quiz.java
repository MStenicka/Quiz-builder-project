package com.ateam.backend.models.quiz;

import com.ateam.backend.models.appUser.AppUser;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "quizzes")

public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @OneToMany(mappedBy = "quiz_id", cascade = CascadeType.ALL)
    private List<Question> questions;
    @Enumerated(EnumType.STRING)
    private QuizStatus status;

    @Enumerated(EnumType.STRING)
    private QuizVisibility visibility;

    @Enumerated(EnumType.STRING)
    private QuizGrading grading;

    @ManyToOne
    private AppUser author;

    @OneToMany(mappedBy = "quizToSolve", cascade = CascadeType.ALL)
    private List<AppUser> allowedUsers;

    private boolean isAuthor;

    private boolean deleted;

    public Quiz(Long id, String title, String description, QuizStatus status, QuizVisibility visibility,
                QuizGrading grading, AppUser author, boolean isAuthor, List<AppUser> allowedUsers) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.visibility = visibility;
        this.grading = grading;
        this.author = author;
        this.isAuthor = isAuthor;
        this.allowedUsers = allowedUsers;
        this.questions = new ArrayList<>();
    }

    public Quiz() {

    }

    public boolean getIsAuthor() {
        return isAuthor;
    }

    public void setIsAuthor(boolean author) {
        isAuthor = author;
    }

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

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public QuizStatus getStatus() {
        return status;
    }

    public void setStatus(QuizStatus status) {
        this.status = status;
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

    public AppUser getAuthor() {
        return author;
    }

    public void setAuthor(AppUser author) {
        this.author = author;
    }

    public Question getQuestionById(Long id) {
        for (Question question : this.questions) {
            if (question.getId().equals(id)) {
                return question;
            }
        }
        return null;
    }
    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public List<AppUser> getAllowedUsers() {
        return allowedUsers;
    }

    public void setAllowedUsers(List<AppUser> allowedUsers) {
        this.allowedUsers = allowedUsers;
    }

    public boolean checkAnswers(List<AnswerRequest> answers) {
        for (AnswerRequest answer : answers) {
            Long questionId = answer.getQuestionId();
            Optional<Question> optionalQuestion = questions.stream()
                    .filter(q -> q.getId().equals(questionId))
                    .findFirst();
            if (optionalQuestion.isPresent()) {
                Question question = optionalQuestion.get();
            } else {
                return false;
            }
        }
        return true;
    }
}