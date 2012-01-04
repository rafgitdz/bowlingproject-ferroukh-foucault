package application.service.team;

import java.util.List;

import javax.ejb.Remote;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import domain.model.player.Player;
import domain.model.team.Team;

@WebService (name = "teamService")
@SOAPBinding(style = Style.DOCUMENT)
@Remote
public interface TeamServiceRemote {

	@WebMethod
	public List<Player> getPlayers(String teamName);

	@WebMethod
	public List<String> getPlayersNames(String teamName);

	@WebMethod
	public Team newTeam(String teamName, String leagueName);

	@WebMethod
	public void deleteTeam(String name);

	@WebMethod
	public void addPlayer(String nameTeam, String playerName);

	@WebMethod
	public List<Team> getAllTeams();

}
