package application.service.team;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import domain.model.exception.TeamException;
import domain.model.player.Player;
import domain.model.player.RepositoryPlayer;
import domain.model.team.RepositoryTeam;
import domain.model.team.Team;
import domain.model.team.TeamFactoryLocal;
import domain.model.team.league.League;
import domain.model.team.league.LeagueFactoryLocal;
import domain.model.team.league.RepositoryLeague;

@Stateless
public class TeamService implements TeamServiceRemote {

	private static final String ERROR_TEAM_IN_LEAGUE = "Your team is in a league, you cannot delete it";
	@EJB
	private RepositoryLeague repositoryLeague;
	@EJB
	private RepositoryTeam repositoryTeam;
	@EJB
	private TeamFactoryLocal teamFactory;
	@EJB
	private LeagueFactoryLocal leagueFactory;
	@EJB
	private RepositoryPlayer repositoryPlayer;

	@Override
	public Team newTeam(String teamName, String leagueName) {

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
	public Team loadTeam(String name) {

		Team team = repositoryTeam.load(name);
		if (team == null)
			throw new TeamException(Team.UNKNOWN_TEAM);
		return team;
	}

	@Override
	public void deleteTeam(String teamName) {
		Team team = repositoryTeam.load(teamName);
		if (team == null)
			throw new TeamException(Team.UNKNOWN_TEAM);
		if (team.getLeague() != null) {
			throw new TeamException(ERROR_TEAM_IN_LEAGUE);
		}
		
		repositoryTeam.delete(teamName);
	}

	@Override
	public ArrayList<String> getPlayersNames(String nameTeam) {
		return repositoryTeam.load(nameTeam).getPlayersNames();
	}

	@Override
	public List<Player> getPlayers(String nameTeam) {
		return repositoryTeam.load(nameTeam).getPlayers();
	}

	@Override
	public void addPlayer(String teamName, String playerName) {
		Team t = loadTeam(teamName);
		Player p = repositoryPlayer.load(playerName);
		t.addPlayer(p);
		repositoryTeam.update(t);
	}
}
