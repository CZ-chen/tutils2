package tech.nodex.tutils2.codec;

import tech.nodex.tutils2.lang.Strings;

import java.security.MessageDigest;

/**
 * User: rizenguo
 * Date: 2014/10/23
 * Time: 15:43
 */
public class MD5 {
    /**
     * MD5编码
     * @param origin 原始字符串
     * @return 经过MD5加密之后的结果
     */
    public static String encode(String origin) {
        String resultString = null;
        try {
            resultString = origin;
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(resultString.getBytes(Strings.UTF8));
            resultString = Hex.byteToHex(md.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultString;
    }

}
