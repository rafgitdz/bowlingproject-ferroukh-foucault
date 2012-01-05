package application.service.team;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import application.service.player.PlayerServiceRemote;
import domain.model.exception.TeamException;
import domain.model.player.Player;
import domain.model.team.RepositoryTeam;
import domain.model.team.Team;
import domain.model.team.TeamFactoryLocal;
import domain.model.team.league.League;
import domain.model.team.league.LeagueFactoryLocal;
import domain.model.team.league.RepositoryLeague;

@Stateless
@WebService(endpointInterface = "application.service.team.TeamServiceRemote", serviceName = "teamService")
public class TeamService implements TeamServiceRemote {

	private static final String ERROR_TEAM_IN_LEAGUE = "Your team is in a league, you cannot delete it";
	private static final String ERROR_UNKNOWN_TEAM = "Unknown team: ";
	private static final String ERROR_PLAYER_IN_A_TEAM = "The player is alredy in a team";
	private static final String ERROR_DUPLICATE_TEAM = "The team already exists";
	
	@EJB
	private RepositoryLeague repositoryLeague;
	@EJB
	private RepositoryTeam repositoryTeam;
	@EJB
	private TeamFactoryLocal teamFactory;
	@EJB
	private LeagueFactoryLocal leagueFactory;
	
	@EJB
	private PlayerServiceRemote playerService;

	@Override
	public Team newTeam(String teamName, String leagueName) {

		if (repositoryTeam.load(teamName) != null)
			throw new TeamException(ERROR_DUPLICATE_TEAM); 
		
		Team t = teamFactory.newTeam(teamName);
		t = repositoryTeam.save(t);

		League league = repositoryLeague.load(leagueName);
		if (league == null) {
			league = leagueFactory.newLeague(leagueName);
			league = repositoryLeague.save(league);
		}

		league.addTeam(t);
		repositoryLeague.update(league);
		return t;
	}

	
	
	@Override
	public void deleteTeam(String teamName) {
		Team team = loadTeam(teamName);
		if (team.getLeague() != null) {
			throw new TeamException(ERROR_TEAM_IN_LEAGUE);
		}

		repositoryTeam.delete(teamName);
	}

	@Override
	public String[] getPlayersNames(String teamName) {
		Team team = loadTeam(teamName);
		team = teamFactory.rebuildTeam(team);
		
		return (String[]) team.getPlayersNames().toArray(new String[team.getPlayersNames().size()]);
	}

	@Override
	public Player[] getPlayers(String nameTeam) {
		Team team = loadTeam(nameTeam);
		team = teamFactory.rebuildTeam(team);
		return (Player[]) team.getPlayers().toArray(new Player[team.getPlayers().size()]);
	}

	@Override
	public void addPlayer(String teamName, String playerName) {
		Team t = loadTeam(teamName);
		t = teamFactory.rebuildTeam(t);
		
		Player p = playerService.loadPlayer(playerName);
		
		if (p.getTeam() != null)
			throw new TeamException(ERROR_PLAYER_IN_A_TEAM);
		t.addPlayer(p);
		repositoryTeam.update(t);
	}
	
	@Override
	public Team[] getAllTeams() {
		List<Team> teams = repositoryTeam.loadAll();
		return (Team[]) teams.toArray(new Team[teams.size()]);
	}
	
	private Team loadTeam(String name) {

		Team team = repositoryTeam.load(name);
		if (team == null)
			throw new TeamException(ERROR_UNKNOWN_TEAM + name);
		return team;
	}

}
