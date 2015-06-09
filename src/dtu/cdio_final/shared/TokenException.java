package dtu.cdio_final.shared;

public class TokenException extends Exception
{	
	private static final long serialVersionUID = 1L;
	
	private final static String[] messages = {"Your token has timed out","Your token is invalid"};
	private int selection = -1;
	
	public TokenException ()
	{
		super();
	}
	
	public TokenException(String message)
	{
		super(message);
	}
	public TokenException(int selection)
	{
		super(messages[selection]);
		this.selection = selection;
	}
	public TokenException(int selection, String message)
	{
		super(message);
		this.selection = selection;
	}
}
