package infrastructure;

import javax.ejb.Stateful;

import domain.model.league.League;
import domain.model.league.RepositoryLeague;

@Stateful
public class RepositoryLeagueJPA extends RepositoryGenericJPA<League, String>
		implements RepositoryLeague {

}
