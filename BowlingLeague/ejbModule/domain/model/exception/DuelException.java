package domain.model.exception;

import javax.ejb.ApplicationException;

@ApplicationException
public class DuelException extends RuntimeException {

	private static final long serialVersionUID = -443230563674771674L;

	public DuelException(String message) {
		super(message);
	}
}

