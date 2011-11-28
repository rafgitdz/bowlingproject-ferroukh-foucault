package domain.model.exception;

public class LeagueException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public LeagueException(String message) {
		super(message);
	}

}
