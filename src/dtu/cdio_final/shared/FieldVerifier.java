package dtu.cdio_final.shared;

import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;


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
public class FieldVerifier {

	/**
	 * Verifies that the specified name is valid for our service.
	 * 
	 * In this example, we only require that the name is at least four
	 * characters. In your application, you can use more complex checks to ensure
	 * that usernames, passwords, email addresses, URLs, and other fields have the
	 * proper syntax.
	 * 
	 * @param name the name to validate
	 * @return true if valid, false if invalid
	 */
	
	
	// We use this online regex tool to test patterns https://regex101.com/

	/**
	 * These variables define the limits and patterns that our input should
	 * conform to.
	 */

	private static final RegExp NAME_PATTERN = RegExp.compile("^[a-zA-ZæøåÆØÅ -]{2,20}$");
	private static final RegExp INI_PATTERN = RegExp.compile("^[a-zA-ZæøåÆØÅ]{2,4}$");
	private static final RegExp CPR_PATTERN = RegExp.compile("^\\d{10}$");

	private static final int PASSWORD_MIN = 5;
	private static final int PASSWORD_MAX = 8;

	public static boolean isValidName(String name) {
		if (name == null)
			return false;
		return NAME_PATTERN.exec(name) != null;
	}

	public static boolean isValidCPR(String cpr) {
		if (cpr == null)
			return false;
		return CPR_PATTERN.exec(cpr) != null;
	}

	public static boolean isValidInitials(String ini) {
		if (ini == null)
			return false;
		return INI_PATTERN.exec(ini) != null;
	}

	public static boolean isValidPassword(String password) {
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
	public static boolean isValidNomNetto(double nomNetto) {

		if (nomNetto >= 0.05 && nomNetto <= 20)
			return true;

		return false;
	}

	/** tolerance i området 0,1 ­ 10,0 % */
	public static boolean isValidTolerance(double tolerance) {

		if (tolerance >= 0.1 && tolerance <= 10)
			return true;

		return false;
	}


	private static boolean[] checkPW(String pw) {
		boolean[] results = new boolean[5];
		RegExp[] patterns = new RegExp[4];

		// make a pattern for each category
		patterns[0] = RegExp.compile(".*\\d+.*");
		patterns[1] = RegExp.compile(".*[a-z]+.*");
		patterns[2] = RegExp.compile(".*[A-Z]+.*");
		patterns[3] = RegExp.compile(".*[\\+\\-_?=!\\.]+.*");

		// check against patterns and store results of check
		for (int i = 0; i < patterns.length; i++) {
			MatchResult m = patterns[i].exec(pw);
			results[i] = m != null;
		}
		// check length of pw
		results[4] = pw.length() >= PASSWORD_MIN && pw.length() <= PASSWORD_MAX;

		return results;
	}
}