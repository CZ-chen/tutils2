package tech.nodex.tutils2.nxconf.confsource;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by cz on 2017-2-15.
 */
public interface ConfigSource {
    /**
     * 取得配置
     * @param configId
     * @return
     * @throws IOException
     */
    Properties getConfig(String configId)throws IOException;
}
