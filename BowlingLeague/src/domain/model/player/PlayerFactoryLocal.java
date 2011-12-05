package domain.model.player;

import javax.ejb.Local;

@Local
public interface PlayerFactoryLocal {

	public Player newPlayer(String name);

	public Player newGame(Player player);
	
	
}
