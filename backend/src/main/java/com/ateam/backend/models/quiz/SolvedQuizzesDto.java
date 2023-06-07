package com.ateam.backend.models.quiz;

import java.util.ArrayList;
import java.util.List;

public class SolvedQuizzesDto {
    private String username;
    private Integer numOfSolvedQuizzes;

    public SolvedQuizzesDto(String username, Integer numOfSolvedQuizzes) {
        this.username = username;
        this.numOfSolvedQuizzes = numOfSolvedQuizzes;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getNumOfSolvedQuizzes() {
        return numOfSolvedQuizzes;
    }

    public void setNumOfSolvedQuizzes(Integer numOfSolvedQuizzes) {
        this.numOfSolvedQuizzes = numOfSolvedQuizzes;
    }

    @Override
    public String toString() {
        return "SolvedQuizzesDto{" +
                "username='" + username + '\'' +
                ", numOfSolvedQuizzes=" + numOfSolvedQuizzes +
                '}';
    }
}
