package tech.nodex.tutils2.codec;

import tech.nodex.tutils2.lang.Strings;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * User: rizenguo
 * Date: 2014/10/23
 * Time: 15:43
 */
public class SHA1 {
    public static String encode(String str) {
    	MessageDigest crypt;
		try {
			crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
	        crypt.update(str.getBytes(Strings.UTF8));
	        String sign = Hex.byteToHex(crypt.digest());
	        return sign;
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
    }
}
