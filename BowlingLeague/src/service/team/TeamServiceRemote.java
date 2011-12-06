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

	Team newTeam(String nameTeam, String nameLeague);

	public Team loadTeam(String name);

	public Team loadTeamEager(String name);

	public void saveTeam(Team team);

	public void deleteTeam(String name);

	public void addPlayer(String nameTeam, Player newPlayer);

	public Team rebuildTeam(String nameTeam);

}
