package domain.model.team.challenge;

import domain.model.team.Team;
import domain.service.DuelServiceForTest;

public class ChallengeFactoryForTest implements ChallengeFactoryLocal {
	
	public Challenge newChallenge(Team t1, Team t2) {
		Challenge c = new Challenge();
		c.firstTeam = t1;
		c.secondTeam = t2;
		c.duelService = new DuelServiceForTest();
		
		return c;
	}

}
