package domain.model.team;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.IndexColumn;

import domain.model.player.Player;
import domain.model.team.league.League;

@Entity
public class Team implements Serializable {

	private static final long serialVersionUID = 2133149340833314015L;
	public static final int TEAM_SIZE = 5;
	public static final String UNKNOWN_TEAM = "Unknown team !";

	@Id
	private String teamName;
	@OneToMany(targetEntity = Player.class, fetch = FetchType.EAGER)
	@IndexColumn(base = 0, name = "PlayerIndex")
	List<Player> players;

	@ManyToOne
	@JoinColumn(name = "League_Id")
	League league;

	protected Team() {
	}

	Team(String name) {
		this.teamName = name;
		players = new ArrayList<Player>();
	}

	public String getName() {
		return teamName;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void addPlayer(Player p) {
		players.add(p);
	}

	public ArrayList<String> getPlayersNames() {

		ArrayList<String> playersNames = new ArrayList<String>();
		for (Player p : players)
			playersNames.add(p.getName());

		return playersNames;
	}

	public Player getPlayer(int i) {
		return players.get(i);
	}

	public League getLeague() {
		return league;
	}

	public void setLeague(League league) {
		this.league = league;
	}

}
