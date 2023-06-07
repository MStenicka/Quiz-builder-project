package com.ateam.backend.controllers;

import com.ateam.backend.models.quiz.*;
import com.ateam.backend.services.PlayersStatisticsService;
import com.ateam.backend.services.QuizResultService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
class LeaderboardsControllerTest {

    @Mock
    private QuizResultService quizResultService;

    @Test
    void get_solved_quizzes_leaderboard_with_default_capacity() { //default capacity is 3
        PlayersStatisticsService playersStatisticsService = Mockito.mock(PlayersStatisticsService.class);
        List<PlayersStatistics> expectedPlayersStatisticsList = new ArrayList<>();
        expectedPlayersStatisticsList.add(new PlayersStatistics("player1", 4, 100));
        expectedPlayersStatisticsList.add(new PlayersStatistics("player2", 3, 50));
        expectedPlayersStatisticsList.add(new PlayersStatistics("player3", 2, 25));
        expectedPlayersStatisticsList.add(new PlayersStatistics("player4", 1, 10));
        Mockito.when(playersStatisticsService.getSolvedQuizzesStatistic()).thenReturn(expectedPlayersStatisticsList);

        LeaderboardsController leaderboardsController = new LeaderboardsController(quizResultService, playersStatisticsService);
        ResponseEntity<List<SolvedQuizzesDto>> response = leaderboardsController.getSolvedQuizzesLeaderboard(null);
        assertEquals("[SolvedQuizzesDto{username='player1', numOfSolvedQuizzes=4}, SolvedQuizzesDto{username='player2', numOfSolvedQuizzes=3}, SolvedQuizzesDto{username='player3', numOfSolvedQuizzes=2}]", response.getBody().toString());
        assertEquals("200 OK", response.getStatusCode().toString());
    }

    @Test
    void get_solved_quizzes_leaderboard_with_capacity_two() {
        PlayersStatisticsService playersStatisticsService = Mockito.mock(PlayersStatisticsService.class);
        List<PlayersStatistics> expectedPlayersStatisticsList = new ArrayList<>();
        expectedPlayersStatisticsList.add(new PlayersStatistics("player1", 4, 50));
        expectedPlayersStatisticsList.add(new PlayersStatistics("player2", 3, 75));
        expectedPlayersStatisticsList.add(new PlayersStatistics("player3", 2, 65));
        expectedPlayersStatisticsList.add(new PlayersStatistics("player4", 1, 40));
        Mockito.when(playersStatisticsService.getSolvedQuizzesStatistic()).thenReturn(expectedPlayersStatisticsList);

        LeaderboardsController leaderboardsController = new LeaderboardsController(quizResultService, playersStatisticsService);
        ResponseEntity<List<SolvedQuizzesDto>> response = leaderboardsController.getSolvedQuizzesLeaderboard(2);
        assertEquals("[SolvedQuizzesDto{username='player1', numOfSolvedQuizzes=4}, SolvedQuizzesDto{username='player2', numOfSolvedQuizzes=3}]", response.getBody().toString());
        assertEquals("200 OK", response.getStatusCode().toString());
    }



    @Test
    void get_quiz_leaderboard_with_capacity_two() {
        QuizResultService quizResultService = Mockito.mock(QuizResultService.class);
        PlayersStatisticsService playersStatisticsService = Mockito.mock(PlayersStatisticsService.class);
        List<QuizResultSummaryDto> expectedQuizResultList = new ArrayList<>();
        expectedQuizResultList.add(new QuizResultSummaryDto("player1", 10, 100));
        expectedQuizResultList.add(new QuizResultSummaryDto("player2", 8, 80));

        Mockito.when(quizResultService.getPlayersResultsByQuizIdAndByScoreDesc(1L, 2)).thenReturn(expectedQuizResultList);

        LeaderboardsController leaderboardsController = new LeaderboardsController(quizResultService, playersStatisticsService);
        ResponseEntity<List<QuizResultSummaryDto>> response = leaderboardsController.getQuizLeaderboard(2,1L);
        assertEquals("[QuizResultSummaryDto{username='player1', score=10, quizSuccessRate=100}, QuizResultSummaryDto{username='player2', score=8, quizSuccessRate=80}]", response.getBody().toString());
        assertEquals("200 OK", response.getStatusCode().toString());
    }
}

