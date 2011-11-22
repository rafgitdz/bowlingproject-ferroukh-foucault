package domain.model.challenge;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.IndexColumn;

import domain.model.duel.Duel;
import domain.model.duel.Player;

@Entity
public class Challenge implements Observer, Serializable {

	private static final long serialVersionUID = -3828260190036366964L;
	private static final String CHALLENGE_NOT_OVER = "The challenge is not over !";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int Id;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "firstTeam")
	private Team firstTeam;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "secondTeam")
	private Team secondTeam;
	@OneToMany(cascade = CascadeType.ALL, targetEntity = Duel.class, fetch = FetchType.EAGER)
	@IndexColumn(base = 0, name = "Duel")
	private List<Duel> duels;

	private int scoreFirstTeam;
	private int scoreSecondTeam;

	public Challenge() {
	}

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

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	private boolean memberOf(Player win, Team team) {
		return team.getPlayersNames().contains(win.getName());
	}

	@Override
	public void update(Observable arg0, Object arg1) {

		Duel duel = (Duel) arg0;
		if (memberOf(duel.getWinner(), firstTeam))
			scoreFirstTeam++;
		else
			scoreSecondTeam++;
	}
}
