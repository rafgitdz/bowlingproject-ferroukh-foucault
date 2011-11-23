package service.duel;

import javax.ejb.Remote;

import domain.model.duel.Duel;
import domain.model.duel.Player;

@Remote
public interface DuelServiceRemote {

	public Duel createDuel(Player player1, Player player2);
	public Player getWinner();
}
