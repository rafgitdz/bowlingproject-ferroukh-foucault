package domain.model.team.league;

import javax.ejb.Local;

@Local
public interface LeagueFactoryLocal {

	public League newLeague(String name);
	
	public League rebuildLeague(League league);
	
	public League rebuildLeagueWithTeams(League league);

}
