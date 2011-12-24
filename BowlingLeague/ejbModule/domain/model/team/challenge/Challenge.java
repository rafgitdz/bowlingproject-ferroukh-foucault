package domain.model.team.challenge;

import java.io.Serializable;

import javax.ejb.EJB;
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

	@EJB
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

	Challenge() {
	}

	Challenge(Team firstT, Team secondT) {

		firstTeam = firstT;
		secondTeam = secondT;
	}

	public Team getWinner() {
		return getScoreFirstTeam() > getScoreSecondTeam() ? firstTeam
				: secondTeam;
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

	public int getScoreFirstTeam() {
		
		int score = 0;
		for (int i = 0 ; i < Team.TEAM_SIZE; ++i) {
			Player p = firstTeam.getPlayer(i);
			Player p2 = secondTeam.getPlayer(i);
			if (duelService.getDuelStatus(p,p2) != DuelStatus.Over)
				throw new ChallengeException(CHALLENGE_NOT_OVER);
			if (p.getScore() > p.getOpponent().getScore())
				score++;
		}
		return score;
	}

	public int getScoreSecondTeam() {
		
		int score = 0;
		for (int i = 0 ; i < Team.TEAM_SIZE; ++i) {
			Player p = secondTeam.getPlayer(i);
			Player p2 = firstTeam.getPlayer(i);
			
			if (duelService.getDuelStatus(p, p2) != DuelStatus.Over)
				throw new ChallengeException(CHALLENGE_NOT_OVER);
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
		for (int i = 0 ; i < Team.TEAM_SIZE; ++i)
			if (duelService.getDuelStatus(firstTeam.getPlayer(i), secondTeam.getPlayer(i)) != DuelStatus.Over)
				return false;
		
		return true;
	}
}
