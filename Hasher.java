import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.Charset;
import java.util.*;

public class Hasher {

	private static String original, toCompare, hashedOriginal, hashedToCompare, testBit;
	private int i, matchedBits, counter;
	// private double counter;
	private Hashtable<String, String> hashTable;

	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	/**
	 * Converts a string into its hashed equivalent
	 * @param input the string to hash
	 * @return returns the hash of the input string
	 * @throws NoSuchAlgorithmException
	 */
	static String sha1(String input) throws NoSuchAlgorithmException {
		MessageDigest mDigest = MessageDigest.getInstance("SHA1");
		byte[] result = mDigest.digest(input.getBytes());
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < result.length; i++) {
			sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}

	/**
	 * Randomly generates an alphanumerical string
	 * @param len the length of the randomly generated string
	 * @return this returns a string of length len
	 */
	static String randomString(int len) {
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}

	/**
	 * Converts a randomly generated string into hash and stores it in a hash table
	 * Compares a randomly generated string's hash to other hashes in the table
	 * Makes sure that identical input strings are not matched
	 * Prints both strings with a matching hash of the firsts x nibbles
	 * @throws NoSuchAlgorithmException
	 */
	public Hasher() throws NoSuchAlgorithmException {
		i = 0;
		counter = 0;
		matchedBits = 15;
		hashTable = new Hashtable<String, String>();
		Set<String> plainText = hashTable.keySet();
		while (i == 0) { 
			toCompare = randomString(30);
			hashedToCompare = sha1(toCompare);
			hashedToCompare = hashedToCompare.substring(0, matchedBits);

			counter = counter + 1;
			if (counter % 10000 == 0) {
				System.out.println(counter);
			}

			if (hashTable.size() < 20000000) {
				hashTable.put(toCompare, hashedToCompare);
			} else if (hashTable.containsValue(hashedToCompare)
					&& hashTable.containsKey(toCompare) == false) {
				for (String key : plainText) {
					if (hashTable.get(key).equals(hashedToCompare))
					{
						System.out.println("String matches found!");
						System.out.println("String 1 and 2 have " + matchedBits + " matching starting consecutive bits.");
						System.out.println("String 1 = " + key);
						System.out.println("String 2 = " + toCompare);
						System.out.println("String 1 and 2 have the following matching bits = " + hashedToCompare);
						System.out.println("String 1 Full Hash = " + sha1(key));
						System.out.println("String 2 full Hash = " + sha1(toCompare));
						i = 1;
						break;
					}
				}

			}

		}
	}

	public static void main(String[] Args) throws NoSuchAlgorithmException {

		Hasher test = new Hasher();
	}

}
