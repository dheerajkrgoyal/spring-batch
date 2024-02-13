package com.dheeraj.springbatchpractice.football.entity;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PlayerSummaryId implements Serializable {
    private String playerId;
    private int gameYear;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerSummaryId that = (PlayerSummaryId) o;
        return getGameYear() == that.getGameYear() && Objects.equals(getPlayerId(), that.getPlayerId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPlayerId(), getGameYear());
    }
}
