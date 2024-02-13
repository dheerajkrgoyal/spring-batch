package com.dheeraj.springbatchpractice.football.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "PLAYER_SUMMARY_INFO")
@IdClass(PlayerSummaryId.class)
public class PlayerSummary {
    @Id
    private String playerId;
    @Id
    private int gameYear;
    private long completes;
    private long attempts;
    private long passingYards;
    private long passingTd;
    private long interceptions;
    private long rushes;
    private long rushYards;
    private long receptions;
    private long receptionYards;
    private long totalTd;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerSummary that = (PlayerSummary) o;
        return getGameYear() == that.getGameYear() && getCompletes() == that.getCompletes() && getAttempts() == that.getAttempts() && getPassingYards() == that.getPassingYards() && getPassingTd() == that.getPassingTd() && getInterceptions() == that.getInterceptions() && getRushes() == that.getRushes() && getRushYards() == that.getRushYards() && getReceptions() == that.getReceptions() && getReceptionYards() == that.getReceptionYards() && getTotalTd() == that.getTotalTd() && Objects.equals(getPlayerId(), that.getPlayerId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPlayerId(), getGameYear(), getCompletes(), getAttempts(), getPassingYards(), getPassingTd(), getInterceptions(), getRushes(), getRushYards(), getReceptions(), getReceptionYards(), getTotalTd());
    }
}
