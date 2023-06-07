package com.ateam.backend.models.quiz;

import java.util.List;

public class QuizzesResponse {
    private final List<Quiz> authorQuizzes;
    private final List<Quiz> publicAndEnabledQuizzesWithoutAuthorQuizzes;

    public QuizzesResponse(List<Quiz> authorQuizzes, List<Quiz> publicAndEnabledQuizzesWithoutAuthorQuizzes) {
        this.authorQuizzes = authorQuizzes;
        this.publicAndEnabledQuizzesWithoutAuthorQuizzes = publicAndEnabledQuizzesWithoutAuthorQuizzes;
    }

    public List<Quiz> getAuthorQuizzes() {
        return authorQuizzes;
    }

    public List<Quiz> getPublicAndEnabledQuizzesWithoutAuthorQuizzes() {
        return publicAndEnabledQuizzesWithoutAuthorQuizzes;
    }
}
