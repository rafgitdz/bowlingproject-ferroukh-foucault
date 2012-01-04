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

import domain.model.exception.TeamException;
import domain.model.player.Player;
import domain.model.team.league.League;

@Entity
public class Team implements Serializable {

	private static final long serialVersionUID = 2133149340833314015L;
	public static final int TEAM_SIZE = 5;
	public static final String FULL_TEAM_ERROR = "The team is full !";
	public static final String PLAYER_NOT_IN_THIS_TEAM = "The player {0} is not in this team";

	@Id
	private String teamName;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "Team_Id")
	@IndexColumn(name = "PlayerIndex")
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
		if (players.size() == TEAM_SIZE)
			throw new TeamException(FULL_TEAM_ERROR);
		players.add(p);
	}

	public ArrayList<String> getPlayersNames() {

		ArrayList<String> playersNames = new ArrayList<String>();
		for (Player p : players)
			playersNames.add(p.getName());

		return playersNames;
	}

	public void removePlayer(String playerName) {

		int playerIndex = -1;
		for (int i = 0; i < players.size(); i++)
			if (players.get(i).getName().equals(playerName)) {
				playerIndex = i;
				break;
			}
		if (playerIndex == -1)
			throw new TeamException(PLAYER_NOT_IN_THIS_TEAM);
		players.remove(playerIndex);
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
