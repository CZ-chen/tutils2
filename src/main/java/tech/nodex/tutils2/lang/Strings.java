package tech.nodex.tutils2.lang;

import java.nio.charset.Charset;
import java.util.Random;

/**
 * Created by 陈朝(chenzhao@rongcapital.cn) on 2016-7-12.
 */
public class Strings {
    public static final Charset UTF8 = Charset.forName("UTF-8");
    public static final String RANDOM_BASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String fromBytes(byte[] bytes){
        return new String(bytes,UTF8);
    }

    public static byte[] getBytes(String str){
        return str.getBytes(UTF8);
    }

    public static String getRandomStr(int lenght) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < lenght; i++) {
            int number = random.nextInt(RANDOM_BASE.length());
            sb.append(RANDOM_BASE.charAt(number));
        }
        return sb.toString();
    }
}
