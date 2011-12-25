package application.service.league;

import java.util.List;

import javax.ejb.Remote;

import domain.model.team.Team;

@Remote
public interface LeagueServiceRemote {

	public void deleteLeague(String name);
	
	public void startLeague(String leagueName);
	
	public List<Team> getTeams(String leagueName);
}
