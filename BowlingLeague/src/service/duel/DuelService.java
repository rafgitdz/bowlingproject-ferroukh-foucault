package service.duel;

import javax.ejb.Stateful;

import domain.model.duel.Duel;
import domain.model.duel.Player;

@Stateful
public class DuelService implements DuelServiceRemote {

	private Duel duel;

	@Override
	public Duel createDuel(Player player1, Player player2) {
		return duel = new Duel(player1, player2);
	}

	@Override
	public Player getWinner() {
		return duel.getWinner();
	}
}
