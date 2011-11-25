package domain.model.league;

import javax.ejb.Remote;

import domain.model.RepositoryGeneric;

@Remote
public interface RepositoryDuel extends RepositoryGeneric<Duel, Integer>{

}
