package domain.model.team;

import java.util.List;

import domain.model.player.Player;

public class TeamFactoryForTest implements TeamFactoryLocal {

	@Override
	public Team newTeam(String name) {
		Team t = new Team(name);
		return t;
	}
	
	@Override
	public Team newTeam(String name, List<Player> players) {
		Team t = newTeam(name);
		for (Player p: players) {
			t.addPlayer(p);
		}
		
		return t;
	}

}
