package domain.model.league;

import java.util.List;

import javax.ejb.Local;

import domain.model.team.Team;

@Local
public interface LeagueFactoryLocal {

	public League newLeague(String name, List<Team> teams);
	
}
