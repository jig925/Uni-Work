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

	static String sha1(String input) throws NoSuchAlgorithmException {
		MessageDigest mDigest = MessageDigest.getInstance("SHA1");
		byte[] result = mDigest.digest(input.getBytes());
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < result.length; i++) {
			sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
		}

		return sb.toString();
	}

	static String randomString(int len) {
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}

	public Hasher() throws NoSuchAlgorithmException {
		i = 0;
		counter = 0;
		matchedBits = 15;
		hashTable = new Hashtable<String, String>();
		Set<String> plainText = hashTable.keySet();
		int test = 0;
		while (i == 0) {
			
			toCompare = randomString(50);
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

		// Hashtable<String, String> h1 = new Hashtable<String, String>();

	}

}
