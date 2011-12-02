package service.team;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import domain.model.player.Player;
import domain.model.player.RepositoryPlayer;
import domain.model.team.RepositoryTeam;
import domain.model.team.Team;
import domain.model.team.TeamException;
import domain.model.team.TeamFactoryLocal;

@Stateless
public class TeamService implements TeamServiceRemote {

	@EJB
	private RepositoryTeam repositoryTeam;

	@EJB
	private RepositoryPlayer repositoryPlayer;

	@EJB
	private TeamFactoryLocal teamFactory;

	@Override
	public Team newTeam(String name, List<String> players) {

		List<Player> playersList = new ArrayList<Player>();
		for (String p : players)
			playersList.add(repositoryPlayer.load(p));

		return repositoryTeam.save(teamFactory.newTeam(name, playersList));
	}

	@Override
	public void saveTeam(Team team) {
		repositoryTeam.save(team, team.getName());
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
	public void getStat(String nameTeam) {
	}

	@Override
	public List<Player> getPlayers(String nameTeam) {
		return repositoryTeam.load(nameTeam).getPlayers();
	}
}
