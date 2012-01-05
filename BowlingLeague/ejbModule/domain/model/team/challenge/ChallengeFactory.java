package domain.model.team.challenge;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import domain.model.team.Team;
import domain.service.DuelServiceLocal;

@Stateless
public class ChallengeFactory implements ChallengeFactoryLocal {

	@EJB
	DuelServiceLocal duelService;
	
	@Override
	public Challenge newChallenge(Team t1, Team t2) {
		Challenge c = new Challenge();
		c.firstTeam = t1;
		c.secondTeam = t2;
		c.duelService = this.duelService;
		return c;
	}

	@Override
	public Challenge rebuildChallenge(Challenge challenge) {
		challenge.duelService = this.duelService;
		return challenge;
	}

}
