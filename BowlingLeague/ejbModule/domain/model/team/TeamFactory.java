package domain.model.team;

import javax.ejb.Stateless;

@Stateless
public class TeamFactory implements TeamFactoryLocal {

	@Override
	public Team newTeam(String name) {
		return new Team(name);
	}

}
