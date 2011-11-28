package domain.model.exception;

public class GameException extends RuntimeException {

	private static final long serialVersionUID = -1863752134063789545L;
	
	public GameException(String message) {
		super(message);
	}

}
