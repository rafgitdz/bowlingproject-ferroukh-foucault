package domain.model.team.league;

import java.util.ArrayList;

import domain.model.team.Team;
import domain.model.team.challenge.ChallengeFactoryForTest;

public class LeagueFactoryForTest implements LeagueFactoryLocal {

	RepositoryLeagueForTest repositoryLeague;
	
	public LeagueFactoryForTest(RepositoryLeagueForTest repositoryLeague) {
		this.repositoryLeague = repositoryLeague;
	}

	@Override
	public League newLeague(String name) {

		League league = new League();
		league.setName(name);
		Schedule sched = new Schedule();
		sched.challengeFactory = new ChallengeFactoryForTest();
		league.setSchedule(sched);
		league.setTeams(new ArrayList<Team>());
		return league;
	}

	@Override
	public League rebuildLeague(League league) {
		return league;
	}

}
