package dtu.cdio_final.shared;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordManager
{
	//Class variables
	private static Random rand = new Random();
	private static final String[] ALPHABET = {"0123456789", 
		"abcdefghijklmnopqrstuvwxyz", 
		"ABCDEFGHIJKLMNOPQRSTUVWXYZ", 
	".+-_?=!"};
	private static final int PASSWORD_MIN = 6;
	private static final int HASH_BYTE_SIZE = 64; // 512 bits
	private static final int PBKDF2_ITERATIONS = 1000;
	
	//-----------------------------------------------------------------------------------------------------------
	//Public Methods
	//-----------------------------------------------------------------------------------------------------------
	
	/**
	 * Generates a random password, matching the needed criteria, and with length 8
	 * @return The randomly generated password.
	 */
	public static String generatePassword()
	{	
		return generatePasswordRekursive(rand.nextInt(ALPHABET.length), PASSWORD_MIN + 2, "", 2);
	}

	/**
	 * Checks if the given password matches the criteria
	 * @param pass The password to check
	 * @return true if the password matches 3 of 4 categories, and has a length >= 6, false otherwise.
	 */
	public static boolean checkPassword(String pass)
	{
		boolean[] check = checkPW(pass);

		int passedCategories = 0;
		for (int i = 0; i < 4; i++)
			if(check[i])
				passedCategories++;

		if(passedCategories > 2 && check[4])
			return true;

		return false;
	}
	
	//-----------------------------------------------------------------------------------------------------------
	//Private Methods
	//-----------------------------------------------------------------------------------------------------------
	
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

	/**
	 * 
	 * @param cat a number marking what category to pull from
	 * @param max the max length of the password
	 * @param pass the password anything added here will be appended to the start of the pass
	 * @param catCount number of categories that we still need to fill, minus the current one.
	 * @return A string containing a random password.
	 */
	private static String generatePasswordRekursive(int cat, int max, String pass, int catCount)
	{
		//if we dont fill the required number of categories, or if the password is too short:
		if(catCount > 0 || pass.length() < max)
		{
			//figure out how many chars we should max pick in this iteration
			int limit = max - pass.length() - (catCount);
			//select a random number between 0 and that limit
			int random = rand.nextInt(limit + 1);
			//if the number is zero we skip the category that we are currently in, if not:
			if(random > 0)
			{
				//pick a random char from the current category, and add it to the password,
				//repeat till we reach the random limit we picked. 
				for (int i = 0; i < random; i++)
					pass += ALPHABET[cat].charAt(rand.nextInt(ALPHABET[cat].length()));
				//go to next category
				cat = (cat + 1) % 4;
				//we picked more than zero chars from the category, so the number of missing categories
				//is reduced by 1, unless we already cover more than we need and are just filling in password length.
				if(catCount != 0)
					catCount--;
			}
			//recursion untill we no longer hit this if-statement
			pass = generatePasswordRekursive(cat, max, pass, catCount);
		}
		//the password should now be valid, so return.
		return pass;
	}

	//add code to generate salt (server)
	private static byte[] generateSalt(int nrOfBits)
	{	
		Random rand = new SecureRandom();
		byte[] salt = new byte[nrOfBits];
		rand.nextBytes(salt);
		
		return salt;
	}
	
	//add code to generate hash from pass and salt (server and client)
	private static byte[] generateHash(String pass, byte[] salt)
	{
		try
		{
			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
			PBEKeySpec keySpec = new PBEKeySpec(pass.toCharArray(), salt, PBKDF2_ITERATIONS, HASH_BYTE_SIZE * 8);
			SecretKey digest = skf.generateSecret(keySpec);
			
			return digest.getEncoded();
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		catch (InvalidKeySpecException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	//add code to compare hashes (server)

	//add code to encrypt and decrypt base16 (server and client)

	//kun til tests, slettes før endelig version.
	public static void main(String[] args)
	{
		byte[] results = generateHash("hej", generateSalt(512));
		for (int i = 0; i < results.length; i++)
			System.out.println(i + "  " + Integer.toString(results[i] & 0xff,2));
		
	}

}
