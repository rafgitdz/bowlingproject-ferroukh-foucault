package domain.model.team;

import java.util.List;

import javax.ejb.Stateless;

import domain.model.player.Player;

@Stateless
public class TeamFactory implements TeamFactoryLocal {

	@Override
	public Team newTeam(String name) {
		Team t = new Team(name);
		return t;
	}
	
	public Team newTeam(String name, List<Player> players) {
		Team t = newTeam(name);
		for (Player p: players) {
			t.addPlayer(p);
		}
		
		return t;
	}

}
