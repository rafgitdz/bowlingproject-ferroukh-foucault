package domain.model.team.league;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import domain.model.team.Team;
import domain.model.team.challenge.ChallengeFactoryLocal;

@Stateless
public class LeagueFactory implements LeagueFactoryLocal {
	
	@EJB
	ChallengeFactoryLocal challengeFactory;
	
	@Override
	public League newLeague(String name) {

		League league = new League();
		league.setName(name);
		league.setSchedule(new Schedule());
		league.setTeams(new ArrayList<Team>());
		return league;
	}

	@Override
	public League rebuildLeague(League league) {
		Schedule sched = league.getSchedule();
		if (sched != null)
			sched.challengeFactory = this.challengeFactory;
		return league;
	}
}
