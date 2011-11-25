package domain.model.team;

import javax.ejb.Remote;

import domain.model.RepositoryGeneric;

@Remote
public interface RepositoryTeam extends RepositoryGeneric<Team, String>{

	public void clearAll();

}
