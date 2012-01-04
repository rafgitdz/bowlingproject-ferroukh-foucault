package domain.model.team;

import java.util.List;

import javax.ejb.Remote;

import domain.model.RepositoryGeneric;
import domain.model.player.Player;

@Remote
public interface RepositoryTeam extends RepositoryGeneric<Team, String> {
	
	public List<Player> getPlayers(String teamName);
}
