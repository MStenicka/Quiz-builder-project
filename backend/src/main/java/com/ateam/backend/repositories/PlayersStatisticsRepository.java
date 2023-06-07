package com.ateam.backend.repositories;

import com.ateam.backend.models.quiz.PlayersStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayersStatisticsRepository extends JpaRepository<PlayersStatistics, Long> {

    PlayersStatistics findByUsername(String username);

    List<PlayersStatistics> findAllByOrderByAverageSuccessRateDesc();

    List<PlayersStatistics> findAllByOrderByNumOfSolvedQuizzesDesc();

}
