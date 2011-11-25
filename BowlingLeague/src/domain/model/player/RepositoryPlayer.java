package domain.model.player;

import javax.ejb.Remote;

import domain.model.RepositoryGeneric;

@Remote
public interface RepositoryPlayer extends RepositoryGeneric<Player, String> {

}
