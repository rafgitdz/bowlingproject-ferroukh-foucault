package application.service.team;

import javax.ejb.Remote;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import domain.model.player.Player;
import domain.model.team.Team;

@WebService (name = "teamService")
@SOAPBinding(style = Style.RPC)
@Remote
public interface TeamServiceRemote {

	@WebMethod
	public Player[] getPlayers(String teamName);

	@WebMethod
	public String[] getPlayersNames(String teamName);

	@WebMethod
	public Team newTeam(String teamName, String leagueName);

	@WebMethod
	public void deleteTeam(String name);

	@WebMethod
	public void addPlayer(String nameTeam, String playerName);

	@WebMethod
	public Team[] getAllTeams();

}
