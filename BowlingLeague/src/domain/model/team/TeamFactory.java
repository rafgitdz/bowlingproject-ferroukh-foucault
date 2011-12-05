package domain.model.team;

import java.util.List;

import javax.ejb.Stateless;

import domain.model.player.Player;

@Stateless
public class TeamFactory implements TeamFactoryLocal {


	
	private static final String ERROR_TEAM_SIZE = "The team must have "
			+ Team.TEAM_SIZE + "players";

	public Team newTeam(String name, List<Player> players) {

		if (players.size() != Team.TEAM_SIZE)
			throw new TeamException(ERROR_TEAM_SIZE);

		Team t = new Team(name, players);
		return t;
	}

	@Override
	public Team rebuildTeam(Team team) {
		for (Player p : team.getPlayers())
			p.getName();
		
		return team;
	}

}
