package service.team;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remote;

import domain.model.player.Player;
import domain.model.team.Team;

@Remote
public interface TeamServiceRemote {
	
	public List<Player> getPlayers();

	public ArrayList<String> getPlayersNames();

	public void getStat();

	public Team loadTeam(String name);

	public void saveTeam(Team team);

	Team newTeam(String name, List<String> players);
	
	public void deleteTeam(String name);

}
