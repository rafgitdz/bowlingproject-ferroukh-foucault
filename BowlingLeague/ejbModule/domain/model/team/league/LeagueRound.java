package domain.model.team.league;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.IndexColumn;

import domain.model.team.challenge.Challenge;

@Entity
public class LeagueRound implements Serializable {

	private static final long serialVersionUID = 2230628307171477428L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@IndexColumn(base = 0, name = "ChallengeIndex")
	private List<Challenge> challenges;

	protected LeagueRound() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	protected LeagueRound(List<Challenge> challenges) {
		this.challenges = challenges;
	}

	public List<Challenge> getChallenges() {
		return challenges;
	}
}
