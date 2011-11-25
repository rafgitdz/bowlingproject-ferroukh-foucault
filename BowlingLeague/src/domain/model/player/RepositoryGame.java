package domain.model.player;

import javax.ejb.Remote;

import domain.model.RepositoryGeneric;

@Remote
public interface RepositoryGame extends RepositoryGeneric<Game, Integer>{

}
