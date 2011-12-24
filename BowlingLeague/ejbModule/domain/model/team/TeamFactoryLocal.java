package domain.model.team;

import javax.ejb.Local;

@Local
public interface TeamFactoryLocal {
	
	public Team newTeam(String nameTeam);
}
