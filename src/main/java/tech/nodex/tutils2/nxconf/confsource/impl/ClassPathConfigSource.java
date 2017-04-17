package tech.nodex.tutils2.nxconf.confsource.impl;

import tech.nodex.tutils2.nxconf.confsource.ConfigSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ClassPathConfigSource implements ConfigSource {
	public Properties getConfig(String configId) throws IOException {
		Properties properties = new Properties();
		InputStream is = ClassPathConfigSource.class.getResourceAsStream(configId+".properties");
		try {
			properties.load(is);
		} catch (Exception e) {
			is = ClassPathConfigSource.class.getResourceAsStream("/"+configId+".properties");
			try {
				properties.load(is);
			} catch (Exception e1) {
				is = ClassPathConfigSource.class.getResourceAsStream("./"+configId+".properties");
				properties.load(is);
			}
		}
		return properties;
	}
}
