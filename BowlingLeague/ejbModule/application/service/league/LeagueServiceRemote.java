package application.service.league;

import javax.ejb.Remote;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import domain.model.team.Team;
import domain.model.team.league.LeagueStatus;

@WebService(name = "leagueService")
@SOAPBinding(style = Style.RPC)
@Remote
public interface LeagueServiceRemote {

	@WebMethod
	public void deleteLeague(String name);

	@WebMethod
	public void startLeague(String leagueName);

	@WebMethod
	public Team[] getTeams(String leagueName);

	@WebMethod
	public LeagueStatus getLeagueStatus(String leagueName);

	@WebMethod
	public void goNextRound(String leagueName);

	@WebMethod
	public boolean isCurrentRoundOver(String leagueName);

	@WebMethod
	public String[] getRanking(String leagueName);
	
	@WebMethod
	public int getScore(String teamName);
	
	@WebMethod
	public String[] getLeagues();
	
	@WebMethod
	public String[] getTeamsLeftSideSchedule(String leagueName, int round);
	
	@WebMethod
	public String[] getTeamsRightSideSchedule(String leagueName, int round);
	
	@WebMethod
	public String getScoreChallenge(String leagueName, int round, String team1, String team2);
	
	@WebMethod
	public int getNumberRounds(String leagueName);
}
