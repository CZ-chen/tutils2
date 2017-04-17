package tech.nodex.tutils2.nxconf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.nodex.tutils2.nxconf.confsource.impl.ClassPathConfigSource;
import tech.nodex.tutils2.nxconf.confsource.impl.FSConfigSource;

import java.io.File;

/**
 * Created by cz on 2017-3-7.
 */
public class NxConfig{
    private static final Logger logger = LoggerFactory.getLogger(NxConfig.class);

    private static final ConfigSourceChain CHAIN = initChain();

    private static ConfigSourceChain initChain(){
        ConfigSourceChain chain = new ConfigSourceChain();
        try {
            File userHomeDir = new File(System.getProperty("user.home"));
            String defaultNxConfDir = new File(userHomeDir,".nx").getPath();
            chain.add(new FSConfigSource(defaultNxConfDir));
        } catch (Exception e) {
            logger.warn("init FSConfigSource failed.",e);
        }

        chain.add(new ClassPathConfigSource());
        return chain;
    }

    public static NxProperties getConfig(String confId){
        return CHAIN.getConfig(confId);
    }

    public static NxProperties getConfig(String[] confIds){
        NxProperties allConf = new NxProperties();
        for(String confId:confIds){
            NxProperties conf = getConfig(confId);
            if(conf!=null){
                allConf.putAll(conf);
            }
        }
        return allConf;
    }

    public static void clearCache(){
        CHAIN.clearCache();
    }
}
