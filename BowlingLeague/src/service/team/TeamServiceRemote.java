package service.team;

import java.util.ArrayList;

import javax.ejb.Remote;


import domain.model.player.Player;
import domain.model.team.Team;

@Remote
public interface TeamServiceRemote {

	public Team createTeam(String name, ArrayList<String> team);

	public ArrayList<Player> getPlayers();

	public ArrayList<String> getPlayersNames();

	public void getStat();

	public Team loadTeam(String name);

	public void saveTeam(Team team);

	public Team newTeam(String name);

}
