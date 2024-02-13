package com.dheeraj.springbatchpractice.football.repository;

import com.dheeraj.springbatchpractice.football.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, String> {
}
