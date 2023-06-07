package com.ateam.backend.models.quiz;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "answers")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JsonProperty("question_id")
    private Question question;

    private String title;

    private String description;

    @JsonProperty("is_correct")
    private boolean isCorrect;

    private boolean deleted;

    public Answer() {
    }

    public Answer(Long id, Question question, Object title, String description, boolean isCorrect) {
        this.id = id;
        this.question = question;
        this.description = description;
        setTitle(title);
        this.isCorrect = isCorrect;
    }

    public Object getTitle() {
        return title;
    }

    public void setTitle(Object title) {
        if (title instanceof Integer) {
            this.title = ((Integer) title).toString();
        } else if (title instanceof String) {
            this.title = (String) title;
        }}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Question getQuestion_id() {
        return question;
    }

    public void setQuestion_id(Question question_id) {
        this.question = question_id;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", question_id=" + question +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", isCorrect=" + isCorrect +
                ", deleted=" + deleted +
                '}';
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Object getText() {
        return title;
    }
}