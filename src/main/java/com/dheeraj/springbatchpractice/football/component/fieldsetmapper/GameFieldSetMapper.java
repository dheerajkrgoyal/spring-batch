package com.dheeraj.springbatchpractice.football.component.fieldsetmapper;

import com.dheeraj.springbatchpractice.football.entity.Game;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class GameFieldSetMapper implements FieldSetMapper<Game> {
    @Override
    public Game mapFieldSet(FieldSet fs) throws BindException {
        if (fs == null) {
            return null;
        }

        Game game = new Game();
        game.setPlayerId(fs.readString("playerId"));
        game.setGameYear(fs.readInt("gameYear"));
        game.setTeam(fs.readString("team"));
        game.setGameWeek(fs.readInt("gameWeek"));
        game.setOpponent(fs.readString("opponent"));
        game.setCompletes(fs.readInt("completes"));
        game.setAttempts(fs.readInt("attempts"));
        game.setPassingYards(fs.readInt("passingYards"));
        game.setPassingTd(fs.readInt("passingTd"));
        game.setInterceptions(fs.readInt("interceptions"));
        game.setRushes(fs.readInt("rushes"));
        game.setRushYards(fs.readInt("rushYards"));
        game.setReceptions(fs.readInt("receptions", 0));
        game.setReceptionYards(fs.readInt("receptionYards"));
        game.setTotalTd(fs.readInt("totalTd"));

        return game;
    }
}
