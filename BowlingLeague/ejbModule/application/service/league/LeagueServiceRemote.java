package application.service.league;

import java.util.List;

import javax.ejb.Remote;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import domain.model.team.Team;
import domain.model.team.league.LeagueStatus;

@WebService(name = "leagueService")
@SOAPBinding(style = Style.DOCUMENT)
@Remote
public interface LeagueServiceRemote {

	@WebMethod
	public void deleteLeague(String name);

	@WebMethod
	public void startLeague(String leagueName);

	@WebMethod
	public List<Team> getTeams(String leagueName);

	@WebMethod
	public LeagueStatus getLeagueStatus(String leagueName);

	@WebMethod
	public void goNextRound(String leagueName);

	@WebMethod
	public boolean isCurrentRoundOver(String leagueName);

	@WebMethod
	public List<Team> getRanking(String leagueName);

}
