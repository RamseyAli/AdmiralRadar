package security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

/*
Credit for encrpytion method goes to Java2S.com
*/

///

public class DesEncrypter {
	Cipher ecipher;

	public DesEncrypter(SecretKey key) throws Exception {
		ecipher = Cipher.getInstance("DES");
		ecipher.init(Cipher.ENCRYPT_MODE, key);
	}

	//@SuppressWarnings("restriction")
	@SuppressWarnings("restriction")
	public String encrypt(String str) throws Exception {
		// Encode the string into bytes using utf-8
		byte[] utf8 = str.getBytes("UTF8");
		// Encrypt
		byte[] enc = ecipher.doFinal(utf8);
		// Encode bytes to base64 to get a string
		return new sun.misc.BASE64Encoder().encode(enc);
	}
}
