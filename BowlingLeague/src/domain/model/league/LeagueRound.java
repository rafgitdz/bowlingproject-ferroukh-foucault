package domain.model.league;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.IndexColumn;

@Entity
public class LeagueRound {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	
	@OneToMany(cascade = CascadeType.ALL, targetEntity = Challenge.class)
	@IndexColumn(base = 0, name = "ChallengeIndex")
	private List<Challenge> challenges;

	protected LeagueRound() {
	};

	protected LeagueRound(List<Challenge> challenges) {
		this.challenges = challenges;
	}

	public List<Challenge> getChallenges() {
		return challenges;
	}
}
