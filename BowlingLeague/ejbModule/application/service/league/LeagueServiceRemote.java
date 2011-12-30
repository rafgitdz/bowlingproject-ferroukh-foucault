package application.service.league;

import java.util.List;

import javax.ejb.Remote;

import domain.model.team.Team;
import domain.model.team.league.LeagueStatus;

@Remote
public interface LeagueServiceRemote {

	public void deleteLeague(String name);
	
	public void startLeague(String leagueName);
	
	public List<Team> getTeams(String leagueName);

	public LeagueStatus getLeagueStatus(String leagueName);
	
	public void goNextRound(String leagueName);

	public boolean isCurrentRoundOver(String leagueName);

	public List<Team> getRanking(String leagueName);

}
