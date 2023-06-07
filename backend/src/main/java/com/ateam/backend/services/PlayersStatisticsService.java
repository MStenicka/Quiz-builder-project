package com.ateam.backend.services;

import com.ateam.backend.models.quiz.PlayersStatistics;
import com.ateam.backend.models.quiz.QuizzesSuccessRateDto;
import com.ateam.backend.models.quiz.SolvedQuizzesDto;
import com.ateam.backend.repositories.PlayersStatisticsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlayersStatisticsService {

    private PlayersStatisticsRepository playersStatisticsRepository;
    //private PlayersStatistics playersStatistics;

    public PlayersStatisticsService(PlayersStatisticsRepository playersStatisticsRepository) {
        this.playersStatisticsRepository = playersStatisticsRepository;
    }

    public void updatePlayerStatistics(String username, int quizSuccessRate) {
        PlayersStatistics playerStatistics = getUsernameStatistic(username);
        if (playerStatistics == null) {
            playerStatistics = new PlayersStatistics(username, 1, quizSuccessRate);
        } else {
            int numOfSolvedQuizzes = playerStatistics.getNumOfSolvedQuizzes() + 1;
            int totalSuccessRate = playerStatistics.getAverageSuccessRate() * playerStatistics.getNumOfSolvedQuizzes() + quizSuccessRate;
            int averageSuccessRate = totalSuccessRate / numOfSolvedQuizzes;
            playerStatistics.setNumOfSolvedQuizzes(numOfSolvedQuizzes);
            playerStatistics.setAverageSuccessRate(averageSuccessRate);
        }
        save(playerStatistics);
    }

    public static List<SolvedQuizzesDto> convertToSolvedQuizzesDtoList(List<PlayersStatistics> playersStatisticsList) {
        List<SolvedQuizzesDto> solvedQuizzesDtoDtoList = new ArrayList<>();
        for (PlayersStatistics playersStatistics : playersStatisticsList) {
            SolvedQuizzesDto solvedQuizzesDto = new SolvedQuizzesDto(
                    playersStatistics.getUsername(),
                    playersStatistics.getNumOfSolvedQuizzes()
            );
            solvedQuizzesDtoDtoList.add(solvedQuizzesDto);
        }
        return solvedQuizzesDtoDtoList;
    }

    public static List<QuizzesSuccessRateDto> convertToQuizzesSuccessRateDto(List<PlayersStatistics> playersStatisticsList) {
        List<QuizzesSuccessRateDto> quizzesSuccessRateDtoList = new ArrayList<>();
        for (PlayersStatistics playersStatistics : playersStatisticsList) {
            QuizzesSuccessRateDto quizzesSuccessRateDto = new QuizzesSuccessRateDto(
                    playersStatistics.getUsername(),
                    playersStatistics.getNumOfSolvedQuizzes(),
                    playersStatistics.getAverageSuccessRate()
            );
            quizzesSuccessRateDtoList.add(quizzesSuccessRateDto);
        }
        return quizzesSuccessRateDtoList;
    }

    public PlayersStatistics getUsernameStatistic(String username) {
        return playersStatisticsRepository.findByUsername(username);
    }

    public void save(PlayersStatistics playerStatistics) {
        playersStatisticsRepository.save(playerStatistics);
    }

    public List<PlayersStatistics> getPlayersStatistic(){
        return playersStatisticsRepository.findAllByOrderByAverageSuccessRateDesc();
    }

    public List<PlayersStatistics> getSolvedQuizzesStatistic(){
        return playersStatisticsRepository.findAllByOrderByNumOfSolvedQuizzesDesc();
    }

}
