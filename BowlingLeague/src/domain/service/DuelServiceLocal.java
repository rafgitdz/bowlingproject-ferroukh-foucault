package domain.service;

import javax.ejb.Local;

import domain.model.player.Player;

@Local
public interface DuelServiceLocal {
	
	public void startDuel(Player p1, Player p2);
	
	public void finishTurn(Player player);
	
	public Player getWinner(Player p1, Player p2);
	
	public DuelStatus getDuelSatus(Player p1);

}
