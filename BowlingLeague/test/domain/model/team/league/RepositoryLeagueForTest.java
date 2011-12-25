package domain.model.team.league;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.model.team.Team;

public class RepositoryLeagueForTest implements RepositoryLeague {

	Map<String, League> entityManager = new HashMap<String, League>();
	
	@Override
	public League load(String id) {
		return entityManager.get(id);
	}

	@Override
	public League save(League entity) {
		return entityManager.put(entity.getName(), entity);
	}

	@Override
	public League save(League entity, String id) {
		return entityManager.put(id, entity);
	}

	@Override
	public void delete(String id) {
		entityManager.remove(id);
	}

	@Override
	public List<League> loadAll() {
		return new ArrayList<League>(entityManager.values());
	}

	@Override
	public League update(League entity) {
		return entityManager.put(entity.getName(), entity);
	}

	@Override
	public List<Team> query(String leagueName) {
		return entityManager.get(leagueName).getTeams();
	}

}
