package tech.nodex.tutils2.nxconf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.nodex.tutils2.nxconf.confsource.*;

import java.io.IOException;
import java.util.*;

/**
 * 配置加载链
 * Created by cz on 2017-2-15.
 */
public class ConfigSourceChain extends ArrayList<ConfigSource>{
    private static final Logger logger = LoggerFactory.getLogger(ConfigSourceChain.class);
    private Map<String,NxProperties> confCache = new HashMap<String,NxProperties>();

    public boolean add(ConfigSource confSource){
        if(confSource!=null){
            super.add(confSource);
            return true;
        }
        return false;
    }

    public NxProperties getConfig(String configId){
        if(confCache.containsKey(configId)){
            return confCache.get(configId);
        }
        for(ConfigSource source:this){
            try {
                Properties p = source.getConfig(configId);
                if(p!=null){
                    logger.info("config (id="+ configId +")found in config source : " + source);
                    NxProperties nxProperties = new NxProperties(p);
                    confCache.put(configId,nxProperties);
                    return nxProperties;
                }
            } catch (Exception e) {
                logger.info("config not found : configId = " + configId + " , configSource = " + source.getClass().getName());
            }
        }
        throw new ConfNotfoundException("config not found (id="+ configId +")");
    }

    public void clearCache() {
        confCache.clear();
    }
}
