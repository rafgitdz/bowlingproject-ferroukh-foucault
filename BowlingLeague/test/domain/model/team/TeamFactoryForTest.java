package domain.model.team;

public class TeamFactoryForTest implements TeamFactoryLocal {

	public Team newTeam(String name) {
		Team t = new Team(name);
		return t;
	}

	@Override
	public Team rebuildTeam(Team team) {
		// TODO Auto-generated method stub
		return null;
	}
}
