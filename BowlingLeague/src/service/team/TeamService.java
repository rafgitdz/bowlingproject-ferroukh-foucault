package service.team;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import domain.model.challenge.Team;
import domain.model.challenge.RepositoryTeam;
import domain.model.duel.Player;

@Stateful
public class TeamService implements TeamServiceRemote {

	@EJB
	private RepositoryTeam eTJPA;
	
	private Team team;

	@Override
	public Team createTeam(String name, ArrayList<String> teamMembers) {

		team = new Team(name);
		for (String s : teamMembers)
			team.addPlayer(new Player(s));

		return team;
	}

	@Override
	public ArrayList<Player> getPlayers() {
		return team.getPlayers();
	}

	@Override
	public ArrayList<String> getPlayersNames() {
		return team.getPlayersNames();
	}

	@Override
	public void getStat() {
	}

	@Override
	public Team newTeam(String name) {

		team = eTJPA.save(new Team(name));
		return team;
	}

	@Override
	public void saveTeam(Team team) {
		eTJPA.save(team);
	}

	@Override
	public Team loadTeam(String name) {

		Team team = eTJPA.load(name);
		return team;
	}
}
