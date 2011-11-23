package domain.model.team;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.IndexColumn;

import domain.model.player.Player;

@Entity
public class Team implements Serializable {

	private static final long serialVersionUID = 2133149340833314015L;
	public static final int MAX_TEAM_SIZE = 5;
	private static final String ERROR_ALREADY_IN_TEAM = "is already in the team ";
	private static final String ERROR_TEAM_IS_FULL = "This team is full, you cannot add another player";
	private static final String UNKONWN_PLAYER_TEAM = "Unknown player in this team -> ";
	public static final String UNKNOWN_TEAM = "Unknown team !";

	@Id
	private String teamName;
	@OneToMany(cascade = CascadeType.ALL, targetEntity = Player.class, fetch = FetchType.EAGER)
	@IndexColumn(base = 0, name = "PlayerName")
	private List<Player> players;

	public Team() {
	}

	public Team(String name) {
		this.teamName = name;
		players = new ArrayList<Player>();
	}

	public void addPlayer(Player player) {

		if (isInTeam(player))
			displayError(player.getName() + ERROR_ALREADY_IN_TEAM + teamName);

		else if (isFull())
			displayError(ERROR_TEAM_IS_FULL);

		else
			players.add(player);
	}

	public String getName() {
		return teamName;
	}

	public ArrayList<Player> getPlayers() {

		ArrayList<Player> playersNames = new ArrayList<Player>();
		for (Player p : players)
			playersNames.add(p);

		return playersNames;
	}

	public ArrayList<String> getPlayersNames() {

		ArrayList<String> playersNames = new ArrayList<String>();
		for (Player p : players)
			playersNames.add(p.getName());

		return playersNames;
	}

	private boolean isInTeam(Player player) {

		return players.contains(player);
	}

	private boolean isFull() {

		return players.size() == MAX_TEAM_SIZE;
	}

	private void displayError(String message) {
		throw new TeamException(message);
	}

	public Player getPlayer(int i) {
		return players.get(i);
	}

	public void setPlayer(int i, Player p) {
		players.set(i, p);
	}

	public Player getPlayer(String name) {

		Player p;
		for (int i = 0; i < players.size(); i++) {
			p = players.get(i);
			if (p.getName().equals(name))
				return p;
		}
		throw new TeamException(UNKONWN_PLAYER_TEAM + name);
	}
}
