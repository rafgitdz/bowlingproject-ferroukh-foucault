package service.team;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remote;

import domain.model.player.Player;
import domain.model.team.Team;

@Remote
public interface TeamServiceRemote {
	
	public List<Player> getPlayers(String nameTeam);

	public ArrayList<String> getPlayersNames(String nameTeam);

	public void getStat(String nameTeam);

	public Team loadTeam(String name);

	public void saveTeam(Team team);

	Team newTeam(String name, List<String> players);
	
	public void deleteTeam(String name);

}
