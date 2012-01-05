package domain.model.player;

import javax.ejb.Local;

import domain.service.DuelServiceLocal;

@Local
public interface PlayerFactoryLocal {

	public Player newPlayer(String name);

	public void newGame(Player player);
	
	public Player rebuildPlayer(Player player, DuelServiceLocal duelService);
	
	public Player rebuildPlayerForTraining(Player player);

	public void newTrainingGame(Player player);
	
}
