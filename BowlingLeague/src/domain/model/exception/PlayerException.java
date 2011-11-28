package domain.model.exception;

public class PlayerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PlayerException(String message) {
		super(message);
	}
}
