package domain.model.player;

import javax.ejb.Remote;

@Remote
public interface RepositoryPlayer {

	public Player save(Player player);

	public Player load(String name);

	public void delete(Player player);
}
