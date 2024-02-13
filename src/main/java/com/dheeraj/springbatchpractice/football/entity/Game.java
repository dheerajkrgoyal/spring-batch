package com.dheeraj.springbatchpractice.football.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "GAME_INFO")
public class Game {

    @Id
    @GeneratedValue
    private long gameId;
    private String playerId;
    private int gameYear;
    private String team;
    private int gameWeek;
    private String opponent;
    private int completes;
    private int attempts;
    private int passingYards;
    private int passingTd;
    private int interceptions;
    private int rushes;
    private int rushYards;
    private int receptions;
    private int receptionYards;
    private int totalTd;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return getGameId() == game.getGameId() && getGameYear() == game.getGameYear() && getGameWeek() == game.getGameWeek() && getCompletes() == game.getCompletes() && getAttempts() == game.getAttempts() && getPassingYards() == game.getPassingYards() && getPassingTd() == game.getPassingTd() && getInterceptions() == game.getInterceptions() && getRushes() == game.getRushes() && getRushYards() == game.getRushYards() && getReceptions() == game.getReceptions() && getReceptionYards() == game.getReceptionYards() && getTotalTd() == game.getTotalTd() && Objects.equals(getPlayerId(), game.getPlayerId()) && Objects.equals(getTeam(), game.getTeam()) && Objects.equals(getOpponent(), game.getOpponent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGameId(), getPlayerId(), getGameYear(), getTeam(), getGameWeek(), getOpponent(), getCompletes(), getAttempts(), getPassingYards(), getPassingTd(), getInterceptions(), getRushes(), getRushYards(), getReceptions(), getReceptionYards(), getTotalTd());
    }
}
