package com.dheeraj.springbatchpractice.football.repository;

import com.dheeraj.springbatchpractice.football.entity.PlayerSummary;
import com.dheeraj.springbatchpractice.football.entity.PlayerSummaryId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlayerSummaryRepository extends JpaRepository<PlayerSummary, PlayerSummaryId> {
    @Query(value = "SELECT new com.dheeraj.springbatchpractice.football.entity.PlayerSummary(g.playerId, g.gameYear, SUM(completes), " +
            "SUM(attempts), SUM(passingYards), SUM(passingTd), SUM(interceptions), SUM(rushes), SUM(rushYards)," +
            " SUM(receptions), SUM(receptionYards), SUM(totalTd) )" +
            "from Game AS g, Player AS p where p.Id = g.playerId " +
            "group by g.playerId, g.gameYear")
    Page<PlayerSummary> getPlayerSummaryReport(Pageable pageable);
}
