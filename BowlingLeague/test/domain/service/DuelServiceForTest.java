package domain.service;

import domain.model.exception.DuelException;
import domain.model.player.Player;
import domain.model.player.PlayerFactoryForTest;
import domain.model.player.PlayerFactoryLocal;

public class DuelServiceForTest implements DuelServiceLocal {

	private static final String GAME_NOT_OVER = "Game doesn't over for ";
	private static final String NO_DRAW_DUEL = "No Draw Match !";
	private static final String NOT_SAME_PLAYER_IN_DUEL = "The two players have to be different";

	private PlayerFactoryLocal playerFactory;

	public DuelServiceForTest() {
		playerFactory = new PlayerFactoryForTest();
	}

	@Override
	public void startDuel(Player p1, Player p2) {

		if (p1.getName().equals(p2.getName()))
			throw new DuelException(NOT_SAME_PLAYER_IN_DUEL);
		p1.setOpponent(p2);
		playerFactory.newGame(p1);
		p2.setOpponent(p1);
		playerFactory.newGame(p2);
		p1.play();
	}

	@Override
	public void finishTurn(Player player) {
		if (player.getOpponent() != null) {
			player.waitForOpponent();
			player.getOpponent().play();
		}

	}

	@Override
	public Player getWinner(Player p1, Player p2) {

		if (!p1.getGame().isOver())
			throw new DuelException(GAME_NOT_OVER + p1.getName());
		if (!p2.getGame().isOver())
			throw new DuelException(GAME_NOT_OVER + p2.getName());

		if (p1.getScore() > p2.getScore())
			return p1;
		else if (p1.getScore() < p2.getScore())
			return p2;
		else {
			throw new DuelException(NO_DRAW_DUEL);
		}
	}

	@Override
	public DuelStatus getDuelStatus(Player p1, Player p2) {

		if (p1.getOpponent() == null
				|| !p1.getOpponent().getName().equals(p2.getName()))
			return DuelStatus.NotSet;

		if (!p1.getGame().isOver() || !p1.getOpponent().getGame().isOver())
			return DuelStatus.InProgress;

		return DuelStatus.Over;
	}

}
