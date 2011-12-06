package domain.model.team.league;

import java.util.List;

import javax.ejb.Local;

import domain.model.team.Team;

@Local
public interface LeagueFactoryLocal {

	public League newLeague(String name);

	public League buildLeague(String nameLeague);
	
	public void StartLeague(String name, List<Team> teams);

}
