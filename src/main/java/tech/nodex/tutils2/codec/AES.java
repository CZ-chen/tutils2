package tech.nodex.tutils2.codec;

import org.apache.commons.codec.binary.Base64;
import tech.nodex.tutils2.lang.Strings;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * 异常java.security.InvalidKeyException:illegal Key Size的解决方案：
 * 在官方网站下载JCE无限制权限策略文件，下载后解压，
 * 可以看到local_policy.jar和US_export_policy.jar以及readme.txt，
 * 如果安装了JRE，将两个jar文件放到%JRE_HOME%\lib\security目录下覆盖原来的文件；
 * 如果安装了JDK，将两个jar文件放到%JDK_HOME%\jre\lib\security目录下覆盖原来文件
 *  FOR java8 : http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html
 *  FOR java7 ：http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html
 *  FOR java6 ：http://www.oracle.com/technetwork/java/javase/downloads/jce-6-download-429243.html
* **/
public class AES {
	static final String AES_PASS = "dpnZKwkdu6+ImW4JRw3u3Q==";

	public static final String TRANSFORMATION_AES = "AES";
	public static final String TRANSFORMATION_CBC = "AES/CBC/NoPadding";

	public static byte[] encrypt(String content, String password) {
		return encrypt(Strings.getBytes(content),password);
	}

	public static byte[] encrypt(byte[] content, String password) {
		try {
			SecretKeySpec sKeySpec = new SecretKeySpec(Base64.decodeBase64(password), "AES");
			Cipher cipher = Cipher.getInstance(TRANSFORMATION_AES);
			cipher.init(Cipher.ENCRYPT_MODE, sKeySpec);
			byte[] result = cipher.doFinal(content);
			return result; // 加密
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	public static byte[] encryptCBc(byte[] content, String password) {
		return encrypt(content,password,TRANSFORMATION_CBC,0,16);
	}

	public static byte[] encrypt(byte[] content, String password,String transformation,int ivOffset,int ivLength) {
		try {
			byte[] pass = Base64.decodeBase64(Strings.getBytes(password));
			SecretKeySpec sKeySpec = new SecretKeySpec(pass, "AES");
			Cipher cipher = Cipher.getInstance(transformation);
			IvParameterSpec iv = new IvParameterSpec(pass, ivOffset, ivLength);
			cipher.init(Cipher.ENCRYPT_MODE, sKeySpec,iv);
			byte[] data = PKCS7.encode(content);
			System.out.println(data.length);
			byte[] result = cipher.doFinal(data);
			return result; // 加密
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	public static byte[] decrypt(byte[] content, String password) {
		try {
			SecretKeySpec sKeySpec = new SecretKeySpec(Base64.decodeBase64(password), "AES");
			Cipher cipher = Cipher.getInstance(TRANSFORMATION_AES);
			cipher.init(Cipher.DECRYPT_MODE, sKeySpec);

			return cipher.doFinal(content);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	public static byte[] decryptCBc(byte[] content, String password) {
		return decrypt(content,password,TRANSFORMATION_CBC,0,16);
	}

	public static byte[] decrypt(byte[] content, String password,String transformation,int ivOffset,int ivLength) {
		try {
			byte[] pass = Base64.decodeBase64(password);
			SecretKeySpec sKeySpec = new SecretKeySpec(pass, "AES");
			Cipher cipher = Cipher.getInstance(transformation);
			IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(pass, ivOffset, ivLength));
			cipher.init(Cipher.DECRYPT_MODE, sKeySpec,iv);
			byte[] result = cipher.doFinal(content);
			return PKCS7.decode(result); // 去除补位字节
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}
}
