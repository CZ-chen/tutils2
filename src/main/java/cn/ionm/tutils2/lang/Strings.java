package cn.ionm.tutils2.lang;

import java.nio.charset.Charset;

/**
 * Created by 陈朝(chenzhao@rongcapital.cn) on 2016-7-12.
 */
public class Strings {
    public static final Charset UTF8 = Charset.forName("UTF-8");

    public static String fromBytes(byte[] bytes){
        return new String(bytes,UTF8);
    }

    public byte[] getBytes(String str){
        return str.getBytes(UTF8);
    }
}
