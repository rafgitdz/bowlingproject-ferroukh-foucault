package domain.model.team;

import java.util.ArrayList;

import domain.model.duel.Duel;
import domain.model.duel.Player;

public class Challenge {

	private static final String CHALLENGE_NOT_OVER = "The challenge is not over !";
	private static final int FIVE = 5;
	private Team firstTeam;
	private Team secondTeam;
	private int scoreFirstTeam;
	private int scoreSecondTeam;
	private ArrayList<Duel> duels;

	public Challenge(String firstT, String secondT) {

		firstTeam = new Team(firstT);
		secondTeam = new Team(secondT);
		scoreFirstTeam = 0;
		scoreSecondTeam = 0;
		duels = new ArrayList<Duel>(5);
	}

	public Team getWinner() {
		if (scoreFirstTeam + scoreSecondTeam < FIVE)
			throw new ChallengeException(CHALLENGE_NOT_OVER);
		return scoreFirstTeam > scoreSecondTeam ? firstTeam : secondTeam;
	}

	public void updateScoreChallenge(int numOfDuel) {

		if (memberOf(duels.get(numOfDuel).getWinner(), firstTeam))
			scoreFirstTeam++;
		else
			scoreSecondTeam++;
	}

	private boolean memberOf(Player win, Team team) {
		return team.getPlayersNames().contains(win.getName());
	}

	public Team getFirstTeam() {
		return firstTeam;
	}

	public Team getSecondTeam() {
		return secondTeam;
	}

	public void constructDuels() {

		for (int i = 0; i < 5; i++) {

			Player p1 = firstTeam.getPlayer(i);
			Player p2 = secondTeam.getPlayer(i);
			duels.add(new Duel(p1, p2));
		}
	}

	public Player getFirstTeamPlayer(int i) {
		return firstTeam.getPlayer(i);
	}

	public Player getSecondTeamPlayer(int i) {
		return secondTeam.getPlayer(i);
	}

	public Duel getDuel(int i) {
		return duels.get(i);
	}

	public int getScoreFirstTeam() {
		return scoreFirstTeam;
	}

	public int getScoreSecondTeam() {
		return scoreSecondTeam;
	}

	public boolean isOver() {
		return (scoreFirstTeam + scoreSecondTeam == FIVE) ? true : false;
	}
}
