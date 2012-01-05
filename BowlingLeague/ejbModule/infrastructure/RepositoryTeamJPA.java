package infrastructure;

import java.util.List;

import javax.ejb.Stateful;

import domain.model.player.Player;
import domain.model.team.RepositoryTeam;
import domain.model.team.Team;
import domain.model.team.league.League;

@Stateful
public class RepositoryTeamJPA extends RepositoryGenericJPA<Team, String>
		implements RepositoryTeam {

	@Override
	public List<Player> getPlayers(String teamName) {
		Team team = em.find(Team.class, teamName);
		team.getPlayers().size();
		return team.getPlayers();
	}

	@Override
	public League getLeague(String teamName) {
		Team team = em.find(Team.class, teamName);
		League league = team.getLeague();
		if (league != null)
			league.getName();
		return league;
	}
}
