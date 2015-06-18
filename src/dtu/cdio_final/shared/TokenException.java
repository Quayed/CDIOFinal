package dtu.cdio_final.shared;

public class TokenException extends Exception {
	private static final long serialVersionUID = 1L;

	private final static String[] messages = { "Your token has timed out", "Your token is invalid" };

	public TokenException() {
		super();
	}

	public TokenException(String message) {
		super(message);
	}

	public TokenException(int selection) {
		super(messages[selection]);
	}
}
