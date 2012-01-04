package infrastructure;

import java.util.List;

import javax.ejb.Stateful;

import domain.model.player.Player;
import domain.model.team.RepositoryTeam;
import domain.model.team.Team;

@Stateful
public class RepositoryTeamJPA extends RepositoryGenericJPA<Team, String>
		implements RepositoryTeam {

	@Override
	public List<Player> getPlayers(String teamName) {
		Team team = em.find(Team.class, teamName);
		team.getPlayers().size();
		return team.getPlayers();
	}
}
