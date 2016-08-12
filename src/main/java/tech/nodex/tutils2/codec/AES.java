package tech.nodex.tutils2.codec;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class AES {
	static final String AES_PASS = "dpnZKwkdu6+ImW4JRw3u3Q==";
	
	public static byte[] encrypt(String content, String password) {
		try {
			SecretKeySpec sKeySpec = new SecretKeySpec(Base64.decode(password), "AES");  
	        Cipher cipher = Cipher.getInstance("AES");  
	        cipher.init(Cipher.ENCRYPT_MODE, sKeySpec);  
			byte[] result = cipher.doFinal(content.getBytes());
			return result; // 加密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] decrypt(byte[] content, String password) {
		try {
			SecretKeySpec sKeySpec = new SecretKeySpec(Base64.decode(password), "AES");  
	        Cipher cipher = Cipher.getInstance("AES");  
	        cipher.init(Cipher.DECRYPT_MODE, sKeySpec);  
			byte[] result = cipher.doFinal(content);
			return result; // 加密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) throws NoSuchAlgorithmException {
		byte[] b0 = "abc".getBytes();
		byte[] b1 = AES.encrypt("abc", AES_PASS);
		byte[] b2 = AES.decrypt(b1, AES_PASS);
		System.out.println(Arrays.toString(b0));
		System.out.println(Arrays.toString(b1));
		System.out.println(Arrays.toString(b2));
	}
}
