package com.ateam.backend.models.quiz;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    private Quiz quiz_id;

    @Enumerated(EnumType.STRING)
    private QuestionType type;

    private String title;

    private String description;

    private boolean deleted;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Answer> answers;

    private boolean hasSingleCorrectAnswer;

    private int correctAnswersCount;

    @ElementCollection
    private List<Long> correctAnswerIds;

    public void setCorrectAnswerIds(List<Long> correctAnswerIds) {
        this.correctAnswerIds = correctAnswerIds;
    }

    public List<Long> getCorrectAnswerIds() {
        return correctAnswerIds;
    }

    public void addCorrectAnswerId(Long answerId) {
        if (correctAnswerIds == null) {
            correctAnswerIds = new ArrayList<>();
        }
        correctAnswerIds.add(answerId);
    }

    public Question(Long id, Quiz quiz_id, QuestionType type, String title, String description, boolean hasSingleCorrectAnswer) {
        this.id = id;
        this.quiz_id = quiz_id;
        this.type = type;
        this.title = title;
        this.description = description;
        this.hasSingleCorrectAnswer = hasSingleCorrectAnswer;
    }

    public Question() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Quiz getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(Quiz quiz_id) {
        this.quiz_id = quiz_id;
    }

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz_id = quiz;
    }

    public String getPairs() {
        List<String> pairs = new ArrayList<>();
        for (Answer answer : answers) {
            pairs.add(answer.getDescription());
        }
        return String.join(";", pairs);
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Answer getCorrectAnswer() {
        for (Answer answer : answers) {
            if (answer.isCorrect()) {
                return answer;
            }
        }
        return null;
    }
    public Answer getAnswer(Long answerId) {
        return answers.stream()
                .filter(answer -> answer.getId().equals(answerId))
                .findFirst()
                .orElse(null);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question)) return false;
        Question question = (Question) o;
        return Objects.equals(id, question.id) && Objects.equals(title, question.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }

    public boolean isHasSingleCorrectAnswer() {
        return hasSingleCorrectAnswer;
    }

    public void setHasSingleCorrectAnswer(boolean hasSingleCorrectAnswer) {
        this.hasSingleCorrectAnswer = hasSingleCorrectAnswer;
    }

    public boolean isAnswerCorrect(String answerText) {
        boolean isCorrect = false;
        switch (type) {
            case MULTIPLE_CHOICE:
                for (Answer answer : answers) {
                    if ((answer.getText().equals(answerText)) && answer.isCorrect()) {
                        isCorrect = true;
                        break;
                    }
                }
                break;
            case SINGLE_CHOICE:
                int numCorrectAnswers = 0;
                for (Answer answer : answers) {
                    if ((answer.getText().equals(answerText)) && answer.isCorrect()) {
                        numCorrectAnswers++;
                    }
                }
                if (hasSingleCorrectAnswer && numCorrectAnswers == 1) {
                    isCorrect = true;
                } else if (!hasSingleCorrectAnswer && numCorrectAnswers > 0) {
                    isCorrect = true;
                }
                break;
            case FILL_OUT:
                String answerStr = answerText.toString().toLowerCase().trim();
                String correctAnswerStr = answers.get(0).getText().toString().toLowerCase().trim();
                if (answerStr.equals(correctAnswerStr)) {
                    isCorrect = true;
                }
                break;
            case PAIR_ASSIGN:
                if (answers.size() == 2) {
                    String[] pair = answerText.split(",");
                    String first = pair[0].trim().toLowerCase();
                    String second = pair[1].trim().toLowerCase();
                    String correctFirst = answers.get(0).getText().toString().toLowerCase().trim();
                    String correctSecond = answers.get(1).getText().toString().trim().toLowerCase();
                    if (first.equals(correctFirst) && second.equals(correctSecond)) {
                        isCorrect = true;
                    }
                }
                break;


            default:
                throw new IllegalArgumentException("Unknown question type: " + type);
        }
        return isCorrect;
    }
}
