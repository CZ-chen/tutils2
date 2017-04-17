package tech.nodex.tutils2.nxconf.confsource;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by cz on 2017-2-15.
 */
public interface ConfigSource {
    /**
     * 取得配置
     * @param configId - 配置ID
     * @return - 配置id对应的配置信息
     * @throws IOException - 当配置信息最终无法找到时抛出
     */
    Properties getConfig(String configId)throws IOException;
}
