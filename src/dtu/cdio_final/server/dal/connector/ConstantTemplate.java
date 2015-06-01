package dtu.cdio_final.server.dal.connector;


// erstat konstanterne nedenfor

public abstract class ConstantTemplate
{
	public static final String
		server		= "",  // database-serveren
		database	=  "",  //"jdbcdatabase", // navnet paa din database = dit studienummer
		username	= "", // dit brugernavn = dit studienummer 
		password	= ""; // dit password som du har valgt til din database
	
	public static final int
		port		= 3306;
}
