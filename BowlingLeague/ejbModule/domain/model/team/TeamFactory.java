package domain.model.team;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class TeamFactory implements TeamFactoryLocal {

	@EJB
	private RepositoryTeam repositoryTeam;
	
	@Override
	public Team newTeam(String name) {
		return new Team(name);
	}

	@Override
	public Team rebuildTeam(Team team) {
		team.players = repositoryTeam.getPlayers(team.getName());
		return team;
	}

}
