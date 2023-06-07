package com.ateam.backend.models.quiz;

import jakarta.persistence.*;

@Entity
@Table
public class PlayersStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private Integer numOfSolvedQuizzes;
    private int averageSuccessRate;

    public PlayersStatistics() {
    }

    public PlayersStatistics(String username, Integer numOfSolvedQuizzes, int averageSuccessRate) {
        this.username = username;
        this.numOfSolvedQuizzes = numOfSolvedQuizzes;
        this.averageSuccessRate = averageSuccessRate;
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

    public int getAverageSuccessRate() {
        return averageSuccessRate;
    }

    public void setAverageSuccessRate(int averageSuccessRate) {
        this.averageSuccessRate = averageSuccessRate;
    }
}
