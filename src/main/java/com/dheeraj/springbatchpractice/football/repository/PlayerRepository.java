package com.dheeraj.springbatchpractice.football.repository;

import com.dheeraj.springbatchpractice.football.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository  extends JpaRepository<Player, String> {
}
