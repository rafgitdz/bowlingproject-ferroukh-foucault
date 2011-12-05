package domain.model.exception;

import javax.ejb.ApplicationException;

@ApplicationException
public class LeagueException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public LeagueException(String message) {
		super(message);
	}

}
