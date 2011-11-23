package service.challenge;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import domain.model.challenge.Challenge;
import domain.model.challenge.Team;
import domain.model.challenge.RepositoryChallenge;
import domain.model.duel.Duel;
import domain.model.duel.Player;
import domain.model.game.GameException;

@Stateful
public class ChallengeService implements ChallengeServiceRemote {

	private static final String CHALLENGE_OVER = "CHALLENGE OVER !";

	@EJB
	private RepositoryChallenge eCJPA;

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
		challenge.getFirstTeam().getPlayer(player).roll(roll);
	}

	@Override
	public void rollSecondTeam(String player, int roll) {

		controlEndChallenge();
		Player p = challenge.getSecondTeam().getPlayer(player);
		p.roll(roll);

		if (p.getGame().isOver())
			countDuels++;
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

	@Override
	public Duel getDuel(int num) {
		return challenge.getDuel(num);
	}

	@Override
	public Challenge newChallenge(String name1, String name2) {

		Challenge challenge = new Challenge(new Team(name1), new Team(name2));
		return challenge;
	}

	@Override
	public Challenge saveChallenge() {
		return eCJPA.save(challenge);
	}

	@Override
	public Challenge loadChallenge(int id) {
		return challenge = eCJPA.load(id);
	}
}
