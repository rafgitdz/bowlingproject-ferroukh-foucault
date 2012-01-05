package domain.model.team.league;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import domain.model.team.Team;
import domain.model.team.TeamFactoryLocal;
import domain.model.team.challenge.Challenge;
import domain.model.team.challenge.ChallengeFactoryLocal;

@Stateless
public class LeagueFactory implements LeagueFactoryLocal {

	@EJB
	ChallengeFactoryLocal challengeFactory;
	@EJB
	TeamFactoryLocal teamFactory;
	@EJB
	RepositoryLeague repositoryLeague;

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
		if (sched != null) {
			sched.challengeFactory = this.challengeFactory;

			// We rebuild the challenges of the league, to set their
			// duelServices
			// This part may be optimized
			for (LeagueRound lr : sched.getSchedule())
				for (Challenge c : lr.getChallenges())
					challengeFactory.rebuildChallenge(c);
		}
		league.teams = repositoryLeague.getTeams(league.getName());
		for (Team team : league.teams) {
			teamFactory.rebuildTeam(team);
		}
		return league;
	}

	@Override
	public League rebuildLeagueWithTeams(League league) {
		league.teams = repositoryLeague.getTeams(league.getName());
		for (Team team : league.teams) {
			teamFactory.rebuildTeam(team);
		}
		return league;
	}
	
}
