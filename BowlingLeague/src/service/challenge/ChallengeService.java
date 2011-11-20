package service.challenge;

import java.util.ArrayList;

import javax.ejb.Stateless;

import domain.model.duel.Player;
import domain.model.game.GameException;
import domain.model.team.Challenge;
import domain.model.team.Team;

@Stateless
public class ChallengeService implements ChallengeServiceRemote {

	private static final String CHALLENGE_OVER = "CHALLENGE OVER !";
	private Challenge challenge;
	int countDuels;

	@Override
	public void createChallenge(String nameTeam1, String nameTeam2,
			ArrayList<String> listTeam1, ArrayList<String> listTeam2) {

		challenge = new Challenge(new Team(nameTeam1), new Team(nameTeam2));
		Team team1 = challenge.getFirstTeam();
		Team team2 = challenge.getSecondTeam();

		for (int i = 0; i < 5; i++) {

			team1.addPlayer(new Player(listTeam1.get(i)));
			team2.addPlayer(new Player(listTeam2.get(i)));
		}

		challenge.setDuels();
	}

	@Override
	public void rollFirstTeam(String player, int roll) {
		controlEndChallenge();
		challenge.getFirstTeam().getPlayerEqualTo(player).roll(roll);
	}

	@Override
	public void rollSecondTeam(String player, int roll) {
		controlEndChallenge();
		Player p = challenge.getSecondTeam().getPlayerEqualTo(player);
		p.roll(roll);

		if (p.getGame().isOver()) {
		//	challenge.updateScoreChallenge(countDuels);
			countDuels++;
		}
	}

	@Override
	public int getScoreTeam1() {
		return challenge.getScoreFirstTeam();
	}

	@Override
	public int getScoreTeam2() {
		return challenge.getScoreSecondTeam();
	}

	@Override
	public String getWinnerChallenge() {
		return challenge.getWinner().getName();
	}

	@Override
	public String getWinnerOfDuel(int num) {
		return challenge.getDuel(num).getWinner().getName();
	}

	@Override
	public void controlEndChallenge() {
		if (challenge.isOver())
			throw new GameException(CHALLENGE_OVER);
	}
}