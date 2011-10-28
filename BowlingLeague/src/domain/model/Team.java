package domain.model;

import java.util.ArrayList;
import java.util.List;

public class Team {

	public static final int MAX_TEAM_SIZE = 5;
	private static final String PLAYERS_NAMES_SEPARATOR = ", ";
	private static final String ERROR_ALREADY_IN_TEAM = "is already in the team "  ;
	private static final String ERROR_TEAM_IS_FULL = "This team is full, you cannot add another player";
	
	private String teamName;
	private List<Player> players;

	public Team(String name) {
		this.teamName = name;
		players = new ArrayList<Player>();
	}

	
	
	public void addPlayer(Player player) {
		
		if (isInTeam(player))
			displayError(player.getName() + ERROR_ALREADY_IN_TEAM + teamName);
		
		else if (isFull())
			displayError(ERROR_TEAM_IS_FULL);
		
		else players.add(player);
	}






	public String getName() {
		return teamName;
	}

	public String getPlayersNames() {
		String playersNames = "";
		for (Player p : players) {
			playersNames += p.getName();
			if (!isLastPlayer(p)) {
				playersNames += PLAYERS_NAMES_SEPARATOR;
			}
		}
		return playersNames;
	}
	
	private boolean isInTeam(Player player) {
		
		return players.contains(player);
	}

	private boolean isLastPlayer(Player p) {
		
		return players.indexOf(p) == (players.size() - 1);
	}


	private boolean isFull() {
		
		return players.size() == MAX_TEAM_SIZE;
	}
	
	private void displayError(String message) {
		
		throw new TeamException(message);
	}
}
