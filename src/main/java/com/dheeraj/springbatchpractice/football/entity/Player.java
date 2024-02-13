package com.dheeraj.springbatchpractice.football.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "PLAYER_INFO")
public class Player implements Serializable {
    @Id
    private String id;
    private String lastName;
    private String firstName;
    private String position;
    private String birthYear;
    private String debutYear;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(getId(), player.getId()) && Objects.equals(getLastName(), player.getLastName()) && Objects.equals(getFirstName(), player.getFirstName()) && Objects.equals(getPosition(), player.getPosition()) && Objects.equals(getBirthYear(), player.getBirthYear()) && Objects.equals(getDebutYear(), player.getDebutYear());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLastName(), getFirstName(), getPosition(), getBirthYear(), getDebutYear());
    }
}
