package domain.model.team;

import javax.ejb.Remote;

@Remote
public interface RepositoryTeam {

	public Team save(Team team);

	public Team load(String name);

	public void delete(String name);
	
	public void clearAll();
}
