package domain.model.league;

import java.util.List;

import javax.ejb.Local;

import domain.model.team.Team;

@Local
public interface LeagueFactoryLocal {

	public League newLeague(String name, List<Team> teams);

	public void updateLeague(String name, Team team);

	public void StartLeague(String name, List<Team> teams);

}
