package domain.model.team;

public class TeamFactoryForTest implements TeamFactoryLocal {

	public Team newTeam(String name) {
		Team t = new Team(name);
		return t;
	}
}
