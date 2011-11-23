package service.duel;

import javax.ejb.EJB;
import javax.ejb.Stateful;

<<<<<<< HEAD
import domain.model.duel.Duel;
import domain.model.duel.Player;
import domain.model.duel.PlayerException;
import domain.model.duel.RepositoryDuel;
=======
import domain.model.league.Duel;
import domain.model.player.Player;
>>>>>>> 7dc33c52d1693596c0790dd09d4ae428bc9f0ee9

@Stateful
public class DuelService implements DuelServiceRemote {

	@EJB
	private RepositoryDuel eDJPA;

	private Duel duel;

	@Override
	public Duel createDuel(Player player1, Player player2) {
		return duel = new Duel(player1, player2);
	}

	@Override
	public Player getWinner() {
		return duel.getWinner();
	}

	@Override
	public Duel newDuel(Player player1, Player player2) {

		eDJPA.save(duel = new Duel(player1, player2));
		return duel;
	}

	@Override
	public Duel loadDuel(int id) {

		Duel duel = eDJPA.load(id);
		if (duel == null)
			throw new PlayerException(Duel.DUEL_NOT_EXIST);
		return duel;
	}

	@Override
	public void deleteDuel(int id) {

		Duel duel = eDJPA.load(id);
		if (duel == null)
			throw new PlayerException(Duel.DUEL_NOT_EXIST);
		eDJPA.delete(id);
	}
}
