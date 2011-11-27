package context;

import javax.naming.Context;
import javax.naming.NamingException;

import service.league.LeagueServiceRemote;

public final class LeagueRemoteGeneration {

	private static LeagueServiceRemote leagueRemote = null;

	LeagueRemoteGeneration() {
	}

	public static LeagueServiceRemote getInstance() {

		try {
			Context context = OnlyOneContext.getInstanceContext();
			if (leagueRemote == null)
				leagueRemote = (LeagueServiceRemote) context
						.lookup("LeagueService/remote");

		} catch (NamingException e) {
			e.printStackTrace();
		}
		return leagueRemote;
	}
}
