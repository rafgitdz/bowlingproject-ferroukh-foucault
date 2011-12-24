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
	public Team newTeam(String nameTeam, String nameLeague) {

		League league = null;
		Team t = teamFactory.newTeam(nameTeam);
		repositoryTeam.save(t);
		if (repositoryLeague.find(nameLeague) == null) {

			league = leagueFactory.newLeague(nameLeague);
			repositoryLeague.save(league);
			league = repositoryLeague.load(league.getName());
		} else
			league = leagueFactory.buildLeague(nameLeague);

		t = loadTeam(t.getName());
		t.setLeague(league);
		repositoryTeam.update(t);
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
	public void deleteTeam(String name) {
		repositoryTeam.delete(name);
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
	public Team loadTeamEager(String name) {

		Team team = repositoryTeam.load(name);
		if (team == null)
			throw new TeamException(Team.UNKNOWN_TEAM);
		// team = teamFactory.rebuildTeam(team);

		return team;
	}

	@Override
	public void addPlayer(String teamName, String playerName) {
		Team t = loadTeam(teamName);
		Player p = repositoryPlayer.load(playerName);
		t.addPlayer(p);
		repositoryTeam.update(t);
	}
}
