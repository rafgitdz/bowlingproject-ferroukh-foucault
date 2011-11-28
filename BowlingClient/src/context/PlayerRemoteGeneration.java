package context;

import javax.naming.Context;
import javax.naming.NamingException;

import service.player.PlayerServiceRemote;

public final class PlayerRemoteGeneration {

	private static PlayerServiceRemote playerRemote = null;

	PlayerRemoteGeneration() {
	}

	public static PlayerServiceRemote getInstance() {

		try {
			Context context = OnlyOneContext.getInstanceContext();
			if (playerRemote == null)
				playerRemote = (PlayerServiceRemote) context
						.lookup("PlayerService/remote");

		} catch (NamingException e) {
			e.printStackTrace();
		}
		return playerRemote;
	}

	public static void cleanInstance() {
		playerRemote = null;		
	}
}
