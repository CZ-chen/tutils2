package tech.nodex.tutils2.nxconf;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cz on 2017-2-15.
 */
public class NxProperties extends Properties {
    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\$\\{([^\\}]+)\\}");
    public NxProperties(Properties p){
        super.putAll(p);
        super.putAll(System.getenv());
        super.putAll(System.getProperties());
    }

    public NxProperties() {
        super.putAll(System.getenv());
        super.putAll(System.getProperties());
    }

    public String getProperty(String key) {
        String value = super.getProperty(key);
        return replacePlaceholder(value);
    }

    public Integer getInt(String key){
        String value = getProperty(key);
        if(value==null){
            return null;
        }
        return Integer.parseInt(value);
    }

    public Long getLong(String key){
        String value = getProperty(key);
        if(value==null){
            return null;
        }
        return Long.parseLong(value);
    }

    public Float getFloat(String key){
        String value = getProperty(key);
        if(value==null){
            return null;
        }
        return Float.parseFloat(value);
    }

    public Double getDouble(String key){
        String value = getProperty(key);
        if(value==null){
            return null;
        }
        return Double.parseDouble(value);
    }

    public String replacePlaceholder(String value){
        if(value!=null && value.contains("${")){
            Matcher m = PLACEHOLDER_PATTERN.matcher(value);
            while(m.find()){
                String placeholder = m.group(1);
//				if(key.equals(placeholder)){
//					break;
//				}
                String phValue = getProperty(placeholder);
                if(phValue!=null){
                    value = value.replace(m.group(), phValue);
                }
            }
        }
        return value;
    }
}
