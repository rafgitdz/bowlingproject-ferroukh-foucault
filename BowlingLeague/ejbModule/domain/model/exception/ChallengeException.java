package domain.model.exception;

import javax.ejb.ApplicationException;

@ApplicationException
public class ChallengeException extends RuntimeException {

	private static final long serialVersionUID = -6338533028490861648L;

	public ChallengeException(String message) {
		super(message);
	}
}
