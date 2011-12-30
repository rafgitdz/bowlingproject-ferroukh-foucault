package domain.model.player;

import javax.ejb.Local;

import domain.service.DuelServiceLocal;

@Local
public interface PlayerFactoryLocal {

	public Player newPlayer(String name);

	public Player newGame(Player player);
	
	public Player rebuildPlayer(Player playe, DuelServiceLocal duelService);
	
}
