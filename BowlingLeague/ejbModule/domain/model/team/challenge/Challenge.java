package domain.model.team.challenge;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import domain.model.exception.ChallengeException;
import domain.model.player.Player;
import domain.model.team.Team;
import domain.service.DuelServiceLocal;
import domain.service.DuelStatus;

@Entity
public class Challenge implements Serializable {

	private static final long serialVersionUID = -3828260190036366964L;
	private static final String CHALLENGE_NOT_OVER = "The challenge is not over !";
	public static final String UNKNWON_CHALLENGE = "Unknown challenge !";

	@Transient
	DuelServiceLocal duelService;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int Id;

	@OneToOne
	@JoinColumn(name = "firstTeam")
	Team firstTeam;
	@OneToOne
	@JoinColumn(name = "secondTeam")
	Team secondTeam;

	@OneToOne
	@JoinColumn(name = "winner")
	Team winner;

	int scoreTeam1;
	int scoreTeam2;

	Challenge() {
		scoreTeam1 = -1;
		scoreTeam2 = -1;
	}

	Challenge(Team firstT, Team secondT) {

		firstTeam = firstT;
		secondTeam = secondT;
		scoreTeam1 = -1;
		scoreTeam2 = -1;
	}

	public Team getWinner() {
		if (winner == null)
			setWinner();
		return winner;
	}

	public void setWinner() {

		if (!isOver())
			throw new ChallengeException(CHALLENGE_NOT_OVER);
		scoreTeam1 = getScoreFirstTeam();
		scoreTeam2 = getScoreSecondTeam();
		winner = scoreTeam1 > scoreTeam2 ? firstTeam : secondTeam;
	}

	public Team getFirstTeam() {
		return firstTeam;
	}

	public Team getSecondTeam() {
		return secondTeam;
	}

	public void setDuels() {

		for (int i = 0; i < Team.TEAM_SIZE; i++) {

			Player p1 = firstTeam.getPlayer(i);
			Player p2 = secondTeam.getPlayer(i);
			duelService.startDuel(p1, p2);
		}
	}

	private int getScoreFirstTeam() {

		int score = 0;
		for (int i = 0; i < Team.TEAM_SIZE; ++i) {
			Player p = firstTeam.getPlayer(i);
			if (p.getScore() > p.getOpponent().getScore())
				score++;
		}
		return score;
	}

	private int getScoreSecondTeam() {

		int score = 0;
		for (int i = 0; i < Team.TEAM_SIZE; ++i) {
			Player p = secondTeam.getPlayer(i);
			if (p.getScore() > p.getOpponent().getScore())
				score++;
		}
		return score;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public boolean isOver() {
		if (winner == null) {
			for (int i = 0; i < Team.TEAM_SIZE; ++i)
				if (duelService.getDuelStatus(firstTeam.getPlayer(i),
						secondTeam.getPlayer(i)) != DuelStatus.Over)
					return false;
		}
		return true;
	}

	public int getScoreTeam1() {
		return scoreTeam1;
	}

	public int getScoreTeam2() {
		return scoreTeam2;
	}
}
