package domain.model.exception;

import javax.ejb.ApplicationException;

@ApplicationException
public class TeamException extends RuntimeException {
	private static final long serialVersionUID = 1886094837424331827L;

	
	
	public TeamException(String message) {
		super(message);
	}

}
