package domain.service;

import javax.ejb.Stateless;

import domain.model.player.Player;

@Stateless
public class DuelService implements DuelServiceLocal{

	@Override
	public void startDuel(Player p1, Player p2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void finishTurn(Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Player getWinner(Player p1, Player p2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DuelStatus getDuelSatus(Player p1) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
