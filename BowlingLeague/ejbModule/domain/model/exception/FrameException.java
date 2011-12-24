package domain.model.exception;

import javax.ejb.ApplicationException;

@ApplicationException
public class FrameException extends RuntimeException {
	private static final long serialVersionUID = -1863752134063789545L;

	public FrameException(String message) {
		super(message);
	}
}
