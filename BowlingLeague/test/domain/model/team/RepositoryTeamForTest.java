package domain.model.team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepositoryTeamForTest implements RepositoryTeam {

	private Map<String, Team> teams;
	
	public RepositoryTeamForTest() {
		teams = new HashMap<String, Team>();
	}
	
	@Override
	public Team load(String id) {
		return teams.get(id);
	}

	@Override
	public Team save(Team entity) {
		teams.put(entity.getName(), entity);
		return entity;
	}

	@Override
	public Team save(Team entity, String id) {
		teams.put(id, entity);
		return entity;
	}

	@Override
	public void delete(String id) {
		teams.remove(id);

	}

	@Override
	public List<Team> loadAll() {
		return new ArrayList<Team>(teams.values());
	}

	@Override
	public Team update(Team entity) {
		return teams.put(entity.getName(), entity);
	}

}
