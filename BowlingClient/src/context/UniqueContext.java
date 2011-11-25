package context;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public final class UniqueContext {

	private static Context context = null;

	UniqueContext() {
	}

	public static Context getInstanceContext() {

		try {
			if (context == null)
				context = new InitialContext();

		} catch (NamingException e) {
			e.printStackTrace();
		}
		return context;
	}
}
