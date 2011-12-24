package domain.model.team.challenge;

import javax.ejb.Local;

import domain.model.team.Team;

@Local
public interface ChallengeFactoryLocal {

	public Challenge newChallenge(Team t1, Team t2);
}
