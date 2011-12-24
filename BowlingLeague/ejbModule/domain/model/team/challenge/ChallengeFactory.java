package domain.model.team.challenge;

import javax.ejb.Stateless;

import domain.model.team.Team;

@Stateless
public class ChallengeFactory implements ChallengeFactoryLocal {

	@Override
	public Challenge newChallenge(Team t1, Team t2) {
		Challenge c = new Challenge();
		c.firstTeam = t1;
		c.secondTeam = t2;
		return c;
	}

}
