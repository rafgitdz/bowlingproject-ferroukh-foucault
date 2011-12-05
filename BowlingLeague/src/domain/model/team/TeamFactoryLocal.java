package domain.model.team;

import java.util.List;

import javax.ejb.Local;

import domain.model.player.Player;

@Local
public interface TeamFactoryLocal {
	
	public Team newTeam(String name, List<Player> players);
	
	public Team rebuildTeam(Team team);
	
}
