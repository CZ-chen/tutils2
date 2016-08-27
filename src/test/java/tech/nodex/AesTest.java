package tech.nodex;

import org.junit.Assert;
import org.junit.Test;
import tech.nodex.tutils2.codec.AES;
import tech.nodex.tutils2.lang.Strings;

/**
 * Created by cz on 2016-8-27.
 */
public class AesTest {
    byte[] content = Strings.getBytes("test content");

    @Test
    public void test(){
        String cbcPassword = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFG=";
        byte[] encoded = AES.encryptCBc(content,cbcPassword);
        System.out.println(Strings.fromBytes(AES.decryptCBc(encoded,cbcPassword)));
        Assert.assertArrayEquals(content,AES.decryptCBc(encoded,cbcPassword));
    }
}
