package application.service.player;

import javax.ejb.Remote;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import domain.model.player.Player;
import domain.model.player.PlayerStatus;

@WebService(name = "playerService")
@SOAPBinding(style = Style.RPC)
@Remote
public interface PlayerServiceRemote {

	@WebMethod
	public void roll(String name, int roll);

	@WebMethod
	public int getScore(String name);

	@WebMethod
	public Player newPlayer(String name);

	@WebMethod
	public void deletePlayer(String name);

	@WebMethod
	public PlayerStatus getPlayerStatus(String playerName);

	@WebMethod
	void rollAlonePlayer(String name, int roll);

}
