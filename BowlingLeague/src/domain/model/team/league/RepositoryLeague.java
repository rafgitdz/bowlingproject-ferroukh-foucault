package domain.model.team.league;

import java.util.List;

import javax.ejb.Remote;

import domain.model.RepositoryGeneric;
import domain.model.team.Team;

@Remote
public interface RepositoryLeague extends RepositoryGeneric<League, String> {

	List<Team> query(String leagueName);
}
