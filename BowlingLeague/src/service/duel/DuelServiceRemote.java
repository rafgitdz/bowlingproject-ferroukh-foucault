package service.duel;

import javax.ejb.Remote;

import domain.model.league.Duel;
import domain.model.player.Player;

@Remote
public interface DuelServiceRemote {

	public Duel createDuel(Player player1, Player player2);
	public Player getWinner();
	public Duel newDuel(Player player1, Player player2);
	public Duel loadDuel(int id);
	public void deleteDuel(int id);
}
