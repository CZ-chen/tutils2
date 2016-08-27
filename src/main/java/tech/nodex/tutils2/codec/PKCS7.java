package tech.nodex.tutils2.codec;

import tech.nodex.tutils2.lang.Strings;

import java.util.Arrays;

/**
 * Created by cz on 2016-8-27.
 */
public class PKCS7 {
    static int BLOCK_SIZE = 32;

    /**
     * 获得对明文进行补位填充的字节.
     *
     * @param data 需要进行填充补位操作的明文数据
     * @return 补齐用的字节数组
     */
    static byte[] encode(byte[] data) {
        // 计算需要填充的位数
        int amountToPad = BLOCK_SIZE - (data.length % BLOCK_SIZE);
        if (amountToPad == 0) {
            amountToPad = BLOCK_SIZE;
        }
        // 获得补位所用的字符
        char padChr = chr(amountToPad);
        String tmp = new String();
        for (int index = 0; index < amountToPad; index++) {
            tmp += padChr;
        }
        ByteGroup byteGroup = new ByteGroup();
        byteGroup.addBytes(data);
        byteGroup.addBytes(Strings.getBytes(tmp));
        return byteGroup.toBytes();
    }

    /**
     * 删除解密后明文的补位字符
     *
     * @param decrypted 解密后的明文
     * @return 删除补位字符后的明文
     */
    static byte[] decode(byte[] decrypted) {
        int pad = (int) decrypted[decrypted.length - 1];
        if (pad < 1 || pad > 32) {
            pad = 0;
        }
        return Arrays.copyOfRange(decrypted, 0, decrypted.length - pad);
    }

    /**
     * 将数字转化成ASCII码对应的字符，用于对明文进行补码
     *
     * @param a 需要转化的数字
     * @return 转化得到的字符
     */
    static char chr(int a) {
        byte target = (byte) (a & 0xFF);
        return (char) target;
    }
}
