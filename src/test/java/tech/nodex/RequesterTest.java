package tech.nodex;

import tech.nodex.tutils2.http.HttpResult;
import tech.nodex.tutils2.http.Requester;
import tech.nodex.tutils2.lang.Strings;
import org.junit.Test;

/**
 * Created by 陈朝(chenzhao@rongcapital.cn) on 2016-8-11.
 */
public class RequesterTest {

    @Test
    public void test(){
        HttpResult result = Requester.instance()
                .setUrl("https://api.weixin.qq.com/cgi-bin/token")
                .addUrlParm("a",10).addUrlParm("b","汉字")
                .execute();
        System.out.println(Strings.fromBytes(result.getRespBody()));
    }
}
