package domain.model.team;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import domain.model.duel.Duel;
import domain.model.duel.Player;

public class Challenge implements Observer {

	private static final String CHALLENGE_NOT_OVER = "The challenge is not over !";
	private Team firstTeam;
	private Team secondTeam;
	private int scoreFirstTeam;
	private int scoreSecondTeam;
	private ArrayList<Duel> duels;

	public Challenge(Team firstT, Team secondT) {

		firstTeam = firstT;
		secondTeam = secondT;
		scoreFirstTeam = 0;
		scoreSecondTeam = 0;
		duels = new ArrayList<Duel>(Team.MAX_TEAM_SIZE);
	}

	public Team getWinner() {
		if (scoreFirstTeam + scoreSecondTeam < Team.MAX_TEAM_SIZE)
			throw new ChallengeException(CHALLENGE_NOT_OVER);
		return scoreFirstTeam > scoreSecondTeam ? firstTeam : secondTeam;
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

	public void setDuels() {

		for (int i = 0; i < 5; i++) {

			Player p1 = firstTeam.getPlayer(i);
			Player p2 = secondTeam.getPlayer(i);
			Duel d = new Duel(p1, p2);
			d.addObserver(this);
			duels.add(d);
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
		
		return (scoreFirstTeam + scoreSecondTeam == Team.MAX_TEAM_SIZE) ? true : false;
	}

	@Override
	public void update(Observable o, Object arg) {
		
		Duel duel = (Duel)o;
		if (memberOf(duel.getWinner(), firstTeam))
			scoreFirstTeam++;
		else
			scoreSecondTeam++;
	}
}
