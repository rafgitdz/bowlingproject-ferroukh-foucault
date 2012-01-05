package domain.model.player;

import javax.ejb.Remote;

import domain.model.RepositoryGeneric;
import domain.model.team.Team;

@Remote
public interface RepositoryPlayer extends RepositoryGeneric<Player, String> {

	Game loadTrainingGame(String playerName);

	Team getTeam(String playerName);

}
