package domain.model.league;

import javax.ejb.Remote;

@Remote
public interface RepositoryChallenge {

	public Challenge save(Challenge challenge);

	public Challenge load(int id);

	public void delete(int id);
}
