package domain.model.team.league;

import java.util.ArrayList;

import javax.ejb.Stateless;

import domain.model.team.Team;

@Stateless
public class LeagueFactory implements LeagueFactoryLocal {
	
	@Override
	public League newLeague(String name) {

		League league = new League();
		league.setName(name);
		league.setSchedule(new Schedule());
		league.setTeams(new ArrayList<Team>());
		return league;
	}
}
