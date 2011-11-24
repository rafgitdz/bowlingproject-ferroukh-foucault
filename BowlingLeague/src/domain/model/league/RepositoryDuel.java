package domain.model.league;

import javax.ejb.Remote;

@Remote
public interface RepositoryDuel {

	public Duel save(Duel duel);

	public Duel load(int id);

	public void delete(int id);
}
