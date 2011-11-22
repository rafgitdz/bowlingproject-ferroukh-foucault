package domain.model.game;

import javax.ejb.Remote;

@Remote
public interface RepositoryGame {

	public Game save(Game obj);

	public Game load(int id);

	public void delete(Game game);
}
