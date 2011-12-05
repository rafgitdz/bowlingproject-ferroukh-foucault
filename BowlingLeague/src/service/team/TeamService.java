package service.team;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import domain.model.player.Player;
import domain.model.player.RepositoryPlayer;
import domain.model.team.RepositoryTeam;
import domain.model.team.Team;
import domain.model.team.TeamException;
import domain.model.team.TeamFactoryLocal;

@Stateful
public class TeamService implements TeamServiceRemote {

	@EJB
	private RepositoryTeam eTJPA;

	@EJB
	private RepositoryPlayer ePJPA;

	@EJB
	private TeamFactoryLocal teamFactory;

	private Team team;


	@Override
	public List<Player> getPlayers() {
		return team.getPlayers();
	}

	@Override
	public ArrayList<String> getPlayersNames() {
		return team.getPlayersNames();
	}

	@Override
	public void getStat() {
	}

	@Override
	public Team newTeam(String name, List<String> players) {

		List<Player> playersList = new ArrayList<Player>();
		for(String p : players)
			playersList.add(ePJPA.load(p));
		
		team = eTJPA.save(teamFactory.newTeam(name,playersList));
		return team;
	}

	@Override
	public void saveTeam(Team team) {
		eTJPA.save(team, team.getName());
	}

	@Override
	public Team loadTeam(String name) {

		Team team = eTJPA.load(name);
		if (team == null)
			throw new TeamException(Team.UNKNOWN_TEAM);
		return team;
	}

	@Override
	public void deleteTeam(String name) {
		eTJPA.delete(name);
	}

	@Override
	public void clearAll() {
		eTJPA.clearAll();
	}

	@Override
	public Team loadTeamEager(String name) {
		
		Team team = eTJPA.load(name);
		if (team == null)
			throw new TeamException(Team.UNKNOWN_TEAM);
		team = teamFactory.rebuildTeam(team);
		
		return team;
		
	}
	
	
}
