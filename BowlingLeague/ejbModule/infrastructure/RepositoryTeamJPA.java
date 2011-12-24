package infrastructure;

import javax.ejb.Stateful;

import domain.model.team.RepositoryTeam;
import domain.model.team.Team;

@Stateful
public class RepositoryTeamJPA extends RepositoryGenericJPA<Team, String>
		implements RepositoryTeam {
}
