package tech.nodex.tutils2.nxconf.confsource.impl;

import org.apache.commons.io.IOUtils;
import tech.nodex.tutils2.nxconf.confsource.ConfigSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class FSConfigSource implements ConfigSource {
	private File dir;
	
	public FSConfigSource(String configDir)throws IOException{
		this.dir = new File(configDir);
		if(!this.dir.exists()){
			this.dir.mkdirs();
		}
		if(!this.dir.isDirectory()){
			throw new IOException(configDir + " is not a valid directory. ");
		}
	}

	public Properties getConfig(String configId)throws IOException{
		FileInputStream fis = null;
		Properties properties = new Properties();
		try {
			fis = new FileInputStream(new File(dir,configId+".properties"));
			properties.load(fis);
		} finally{
			IOUtils.closeQuietly(fis);
		}
		return properties;
	}

}
