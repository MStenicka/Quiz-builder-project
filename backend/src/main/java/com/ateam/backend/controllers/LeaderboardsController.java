package com.ateam.backend.controllers;

import com.ateam.backend.exceptions.ResultsNotFoundException;
import com.ateam.backend.models.quiz.PlayersStatistics;
import com.ateam.backend.models.quiz.QuizzesSuccessRateDto;
import com.ateam.backend.models.quiz.SolvedQuizzesDto;
import com.ateam.backend.models.quiz.QuizResultSummaryDto;
import com.ateam.backend.services.PlayersStatisticsService;
import com.ateam.backend.services.QuizResultService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leaderboards")
public class LeaderboardsController {

    private final QuizResultService quizResultService;
    private final PlayersStatisticsService playersStatisticsService;

    public LeaderboardsController(QuizResultService quizResultService, PlayersStatisticsService playersStatisticsService) {
        this.quizResultService = quizResultService;
        this.playersStatisticsService = playersStatisticsService;
    }

     @GetMapping({"/{capacity}/quizzes/mostSolved","/quizzes/mostSolved"})
     public ResponseEntity<List<SolvedQuizzesDto>> getSolvedQuizzesLeaderboard(@PathVariable(required = false) Integer capacity) {

        List<PlayersStatistics> playersStatisticsList = playersStatisticsService.getSolvedQuizzesStatistic();
        List<SolvedQuizzesDto> solvedQuizzesDtoList = playersStatisticsService.convertToSolvedQuizzesDtoList(playersStatisticsList);

        int size = capacity != null ? capacity : 3;
        solvedQuizzesDtoList = solvedQuizzesDtoList.subList(0, Math.min(size, solvedQuizzesDtoList.size()));

        if (solvedQuizzesDtoList.isEmpty()) { throw new ResultsNotFoundException();}

        return ResponseEntity.ok(solvedQuizzesDtoList);
     }

    @GetMapping({"/{capacity}/quizzes/successRate","/quizzes/successRate"})
    public ResponseEntity<List<QuizzesSuccessRateDto>> getSuccessRateLeaderboard(@PathVariable(required = false) Integer capacity) {

        List<PlayersStatistics> playersStatisticsList = playersStatisticsService.getPlayersStatistic();
        List<QuizzesSuccessRateDto> quizzesSuccessRateDtoList = playersStatisticsService.convertToQuizzesSuccessRateDto(playersStatisticsList);

        int size = capacity != null ? capacity : 3;
        quizzesSuccessRateDtoList = quizzesSuccessRateDtoList.subList(0, Math.min(size, quizzesSuccessRateDtoList.size()));

        if (quizzesSuccessRateDtoList.isEmpty()) { throw new ResultsNotFoundException();}

        return ResponseEntity.ok(quizzesSuccessRateDtoList);
    }


    @GetMapping({"/{capacity}/quizzes/{quiz_id}","/quizzes/{quiz_id}"})
    public ResponseEntity<List<QuizResultSummaryDto>> getQuizLeaderboard(@PathVariable(required = false) Integer capacity,
                                                                         @PathVariable Long quiz_id) {

        if (capacity == null) { capacity = 3;
            List<QuizResultSummaryDto> quizResults = quizResultService.getPlayersResultsByQuizIdAndByScoreDesc(quiz_id, capacity);
            if (quizResults.isEmpty()) { throw new ResultsNotFoundException();}
            return ResponseEntity.ok(quizResults);
        } else {
            List<QuizResultSummaryDto> quizResults = quizResultService.getPlayersResultsByQuizIdAndByScoreDesc(quiz_id, capacity);
            if (quizResults.isEmpty()) { throw new ResultsNotFoundException();}
            return ResponseEntity.ok(quizResults);
            }
        }
}
