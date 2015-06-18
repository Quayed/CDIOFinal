package dtu.cdio_final.shared;

/**
 * <p>
 * FieldVerifier validates that the name the user enters is valid.
 * </p>
 * <p>
 * This class is in the <code>shared</code> package because we use it in both
 * the client code and on the server. On the client, we verify that the name is
 * valid before sending an RPC request so the user doesn't have to wait for a
 * network round trip to get feedback. On the server, we verify that the name is
 * correct to ensure that the input is correct regardless of where the RPC
 * originates.
 * </p>
 * <p>
 * When creating a class that is used on both the client and the server, be sure
 * that all code is translatable and does not use native JavaScript. Code that
 * is not translatable (such as code that interacts with a database or the file
 * system) cannot be compiled into client-side JavaScript. Code that uses native
 * JavaScript (such as Widgets) cannot be run on the server.
 * </p>
 */
public class FieldVerifier
{	
	// We use this online regex tool to test patterns https://regex101.com/

	/**
	 * These variables define the limits and patterns that our input should
	 * conform to.
	 */
	private static final String NAME_PATTERN = "^[a-zA-ZæøåÆØÅ -]{2,20}$";
	private static final String INI_PATTERN = "^[a-zA-ZæøåÆØÅ]{2,4}$";
	private static final String CPR_PATTERN = "^((0[1-9]|[12][0-9]|3[01])(0[13578]|1[02])[0-9]{6})|((0[1-9]|[12][0-9]|30)(0[469]|11)[0-9]{6})|((0[1-9]|1[0-9]|2[0-9])02([02468][048]|[13579][26])[0-9]{4})|((0[1-9]|1[0-9]|2[0-8])02[0-9]{6})$"; //tager højde for alt andet end de sidste 4 cifre
	private static final String ID_PATTERN = "^[0-9]{1,8}$"; // 1­99999999
	private static final String PROVIDER_PATTERN = "^[a-zA-ZæøåÆØÅ -\\+\\-_?=!\\.]{2,20}$"; //Bogstaver,tal,forskellige tegn
	private static final String QUANTITY_PATTERN = "^[0-9]+(\\.[0-9]{1,4})?$";
	private static final String TOLERANCE_PATTERN = "^[0-9]+(\\.[0-9]{1,2})?$";

	private static final int PASSWORD_MIN = 5;

	public static boolean isValidName(String name)
	{
		if (name == null)
			return false;
		return name.matches(NAME_PATTERN);
	}

	public static boolean isValidCPR(String cpr)
	{
		if (cpr == null)
			return false;
		return cpr.matches(CPR_PATTERN);
	}

	public static boolean isValidInitials(String ini)
	{
		if (ini == null)
			return false;
		return ini.matches(INI_PATTERN);
	}

	public static boolean isValidID(String id)
	{		
			if (id == null || id.equals("0"))
				return false;
			return id.matches(ID_PATTERN);
	}
	
	public static boolean isValidProvider(String provider)
	{
		if (provider == null)
			return false;
		return provider.matches(PROVIDER_PATTERN);
	}
	
	public static boolean isValidQuantity(String quantity)
	{
		if(quantity == null)
			return false;
		return quantity.matches(QUANTITY_PATTERN);
	}
	
	/**
	 * Checks if the given password matches the criteria
	 * @param pass The password to check
	 * @return true if the password matches 3 of 4 categories, and has a length >= 6, false otherwise.
	 */
	public static boolean isValidPassword(String password)
	{
		boolean[] check = checkPW(password);

		int passedCategories = 0;
		for (int i = 0; i < 4; i++)
			if (check[i])
				passedCategories++;

		if (passedCategories > 2 && check[4])
			return true;

		return false;
	}
	
	/** nominel nettomængde i området 0,05 ­ 20,0 kg */
	public static boolean isValidNomNetto(String nomNetto)
	{
		
		if(nomNetto.matches(QUANTITY_PATTERN)){
			double doubleNomNetto = Double.valueOf(nomNetto);
				if (doubleNomNetto >= 0.05 && doubleNomNetto <= 20)
					return true;
		}
		return false;
	}

	/** tolerance i området 0,1 ­ 10,0 % */
	public static boolean isValidTolerance(String tolerance)
	{
		if(tolerance.matches(TOLERANCE_PATTERN)){
			double doubleTolerance = Double.valueOf(tolerance);
				if (doubleTolerance >= 0.1 && doubleTolerance <= 10)
					return true;
		}
		return false;
	}

	/**
	 * checks if the password matches the requirements, returns a boolean array
	 *  of length = 5 where:
	 * 0 = [0-9]
	 * 1 = [a-z]
	 * 2 = [A-Z]
	 * 3 = [\\+\\-_?=]
	 * 4 = length >= 6
	 * @param pw The password to check
	 * @return An array containing true for all passed test, and false for all failed ones.
	 */
	private static boolean[] checkPW(String pw)
	{
		boolean[] results = new boolean[5];
		String[] patterns = new String[4];

		//make a pattern for each category
		patterns[0] = ".*\\d+.*";
		patterns[1] = ".*[a-z]+.*";
		patterns[2] = ".*[A-Z]+.*";
		patterns[3] = ".*[\\+\\-_?=!\\.]+.*";

		//check against patterns and store results of check
		for (int i = 0; i < patterns.length; i++)
			results[i] = pw.matches(patterns[i]);
		
		//check length of pw
		results[4] = pw.length() >= PASSWORD_MIN;

		return results;
	}

	public static boolean isValidRole(int role) {
		if(0 < role && role < 5)
			return true;
		return false;
	}

	public static boolean isValidUserStatus(int status) {
		if(status == 0 || status == 1)
			return true;
		return true;
	}
}