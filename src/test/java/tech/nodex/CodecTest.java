package tech.nodex;

import org.junit.Assert;
import org.junit.Test;
import tech.nodex.tutils2.codec.AES;
import tech.nodex.tutils2.codec.DES;
import tech.nodex.tutils2.codec.Hex;
import tech.nodex.tutils2.lang.Strings;

import java.util.Base64;

/**
 * Created by cz on 2016-8-27.
 */
public class CodecTest {
    byte[] content = Strings.getBytes("test content");

    @Test
    public void test(){
        String cbcPassword = "5/XS64xhpZBTDVB6aJILoM6AdR3x1p2X3mpe2na8oSw=";
        byte[] encoded = AES.encryptCBc(content,cbcPassword);
//        System.out.println(Strings.fromBytes(AES.decryptCBc(,cbcPassword)));
        Assert.assertArrayEquals(content,AES.decryptCBc(Hex.hex2byte("762d20bfacf0caf5b311f41fd5848c65075f142be0d239bae57dde11829f7a29"),cbcPassword));
    }

    @Test
    public void testDES(){
        String key = "gScwf19rYDeWFoEYJVdgN29b";
        String encoded = DES.encode(key,"abc");
        System.out.println(encoded);
        System.out.println(DES.decode(key,encoded));
    }
}
