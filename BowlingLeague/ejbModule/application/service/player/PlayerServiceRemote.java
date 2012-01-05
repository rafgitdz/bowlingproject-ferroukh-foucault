package application.service.player;

import javax.ejb.Remote;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import domain.model.player.Game;
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
	public Player newPlayer(String playerName);

	@WebMethod
	public void deletePlayer(String playerName);

	@WebMethod
	public PlayerStatus getPlayerStatus(String playerName);

	@WebMethod
	public Game rollAlonePlayer(String name, int roll);

	@WebMethod
	public String getOpponentName(String playerName);

	@WebMethod
	public int[] getFrames(String playeName);

	@WebMethod
	public int[] getTotalsScores(String playerName);
	
	@WebMethod
	public void newGame(String playerName);

}
