package domain.model.league;

import java.util.List;

public class LeagueRound {

	private List<Challenge> challenges;
	
	protected LeagueRound() {};
	
	protected LeagueRound(List<Challenge> challenges) {
		this.challenges = challenges;
	}
	
	public List<Challenge> getChallenges() {
		return challenges;
	}
}
