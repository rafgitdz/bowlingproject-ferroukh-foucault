package context;

import javax.naming.Context;
import javax.naming.NamingException;

import service.team.TeamServiceRemote;

public final class TeamRemoteGeneration {

	private static TeamServiceRemote teamRemote = null;

	TeamRemoteGeneration() {
	}

	public static TeamServiceRemote getInstance() {

		try {
			Context context = UniqueContext.getInstanceContext();
			if (teamRemote == null)
				teamRemote = (TeamServiceRemote) context
						.lookup("TeamService/remote");

		} catch (NamingException e) {
			e.printStackTrace();
		}
		return teamRemote;
	}
}
