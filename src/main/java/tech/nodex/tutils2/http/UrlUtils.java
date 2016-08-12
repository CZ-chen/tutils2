package tech.nodex.tutils2.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class UrlUtils {
	public static String setParms(String url,Map<String,Object> urlParams ){
		StringBuilder _url = new StringBuilder(url);
        if(urlParams!=null && urlParams.size()>0){
        	int eqPos = url.indexOf("?");
        	if(eqPos<0){
        		_url.append('?');
        	}else if(eqPos<url.length()-1){
        		_url.append('&');
        	}
            
            _url.append(toQueryString(urlParams));
        }
        return _url.toString();
	}
	
	public static String toQueryString(Map<String,Object> params){
		StringBuilder _url = new StringBuilder();
        if(params!=null && params.size()>0){
            for(Map.Entry<String,Object> entry:params.entrySet()){
                String key = entry.getKey();
                String val = null;
                if(entry.getValue()!=null){
                    val = encode(entry.getValue().toString());
                }

                _url.append(key).append('=').append(val).append('&');
            }
            _url.setLength(_url.length()-1);
        }
        return _url.toString();
	}
	
	public static String encode(String str){
		if(str==null){
			return "";
		}
		try {
			return URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return str;
		}
	}
}
