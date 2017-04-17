package tech.nodex;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import tech.nodex.tutils2.jackson.JsonUtils;
import tech.nodex.tutils2.nxconf.NxConfig;
import tech.nodex.tutils2.nxconf.NxProperties;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * Created by cz on 2017-4-17.
 */
public class NxConfTest {
    @Test
    public void testClassPathConf(){
        NxProperties test = NxConfig.getConfig("test");
        Assert.assertEquals(new Integer(1),test.getInt("tech.nodex.test.key1"));
        Assert.assertEquals("classpath",test.getProperty("tech.nodex.test.key2"));
        Assert.assertEquals("placeholdervalue-1",test.getProperty("tech.nodex.test.key3"));
        NxConfig.clearCache();
    }

    @Test
    public void testFSConf() throws Exception {
        Properties properties = new Properties();
        properties.put("tech.nodex.test.key1","2");
        properties.put("tech.nodex.test.key2","file_system");
        properties.put("tech.nodex.test.key3","placeholdervalue-${tech.nodex.test.key1}");

        File testConfFile = new File(System.getProperty("user.home")+File.separator+".nx"+File.separator +"test.properties");
        FileOutputStream fos = new FileOutputStream(testConfFile);
        properties.store(fos,"this is a test nxconfig file!");
        IOUtils.closeQuietly(fos);

        NxProperties test = NxConfig.getConfig("test");
        Assert.assertEquals(new Integer(2),test.getInt("tech.nodex.test.key1"));
        Assert.assertEquals("file_system",test.getProperty("tech.nodex.test.key2"));
        Assert.assertEquals("placeholdervalue-2",test.getProperty("tech.nodex.test.key3"));

        testConfFile.delete();
        NxConfig.clearCache();
    }
}
