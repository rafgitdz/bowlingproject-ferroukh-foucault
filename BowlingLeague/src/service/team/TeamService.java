package service.team;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.Stateful;

<<<<<<< HEAD
import domain.model.challenge.Team;
import domain.model.challenge.RepositoryTeam;
import domain.model.challenge.TeamException;
import domain.model.duel.Player;
=======

import domain.model.player.Player;
import domain.model.team.RepositoryTeam;
import domain.model.team.Team;
>>>>>>> 7dc33c52d1693596c0790dd09d4ae428bc9f0ee9

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
		if (team == null)
			throw new TeamException(Team.UNKNOWN_TEAM);
		return team;
	}
}
