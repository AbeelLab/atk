package atk.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utility class to calculate an MD5 digest from various input types
 * 
 * @author Thomas Abeel
 *
 */
public class MD5Tools {

	/**
	 * Returns the hexadecimal representation of an MD5 hash.
	 *
	 * @param pass
	 *            input string
	 * @return hashed string
	 */
	@Deprecated
	static public String md5(String pass) {
		return string(pass);
	}

	/**
	 * Returns the hexadecimal representation of an MD5 hash.
	 *
	 * @param pass
	 *            input string
	 * @return hashed string
	 */
	static public String string(String pass) {
		return hex(calcMd5(pass));
	}

	static public String file(File file)  {
		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			
			InputStream is = Files.newInputStream(Paths.get(file.toString()));
			
			byte[] bytes = new byte[256*1024];
			int numBytes;
			while ((numBytes = is.read(bytes)) != -1) {
				md.update(bytes, 0, numBytes);
			}
			byte[] digest = md.digest();
			is.close();
			return hex(digest);
		}

		catch (IOException e) {
			System.err.println("Failed calculating MD5");
			throw new RuntimeException(e);
		} catch (NoSuchAlgorithmException e) {
			System.err.println("MD5 not found");
			throw new RuntimeException(e);
		}

	}

	/**
	 * Returns the hexadecimal representation of an MD5 hash.
	 *
	 * @param pass
	 *            input byte array
	 * @return hashed string
	 */
	static public String bytes(byte[] data) {
		try {
			MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
			digest.update(data, 0, data.length);
			byte[] hash = digest.digest();
			return hex(hash);
		} catch (NoSuchAlgorithmException e) {
			System.err.println("No MD5 algorithm found");
			throw new RuntimeException(e);
		}
	}

	/**
	 * make an md5 hash from a string
	 *
	 * @param pass
	 *            string to be hashed
	 * @return the hash as an byte array
	 */
	static private byte[] calcMd5(String pass) {
		try {
			MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
			digest.update(pass.getBytes(), 0, pass.length());
			byte[] hash = digest.digest();
			return hash;
		} catch (NoSuchAlgorithmException e) {
			System.err.println("No MD5 algorithm found");
			throw new RuntimeException(e);
		}
	}

	/**
	 * Convert an array of bytes to an uppercase hexadecimal representation
	 *
	 * @param array
	 *            a byte array
	 * @return the byte array as a hexadecimal string representation
	 */
	static private String hex(byte[] array) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.length; ++i) {
			sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).toUpperCase().substring(1, 3));
		}
		return sb.toString();
	}

}
