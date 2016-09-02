package tech.nodex.tutils2.http;

import tech.nodex.tutils2.lang.Strings;
import net.sf.morph.Morph;
import okhttp3.*;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;


public class Requester {
	private static final Logger logger = LoggerFactory.getLogger(Requester.class);

	static OkHttpClient client;

	static{
		client = new OkHttpClient.Builder()
			.connectTimeout(10, TimeUnit.SECONDS)
			.writeTimeout(30, TimeUnit.SECONDS)
			.readTimeout(30, TimeUnit.SECONDS)
			.connectionPool(new ConnectionPool(10, 60, TimeUnit.SECONDS))
			.build();
	}

    public enum Method{
        POST,GET,PUT,DELETE
    }

    public static Requester instance(){
    	return new Requester();
    }

    private String url;
    private Method method = Method.GET;
    private byte[] body;
    private Map<String,Object> formData;
    private Map<String,Object> urlParams;
	private Map<String,String> headers;
	private MediaType contentType;
	private OkHttpClient udfHttpClient;
	private MultipartBody.Builder multipartBodyBuilder;

	/**
	 * 发起请求并自动下载响应内容
	 *
	 * @return the http result
	 */
	public HttpResult execute(){
		HttpResult result = new HttpResult();
    	result.setCode(-1);
		Response resp = null;
    	try{
			resp = sendRequest();
			result.setCode(resp.code());
			result.setRespBody(resp.body().bytes());
			result.setContentType(resp.header("Content-Type"));
    	}catch(Exception ex){
    		result.setError(ex);
    	}finally {
			IOUtils.closeQuietly(resp);
		}
    	return result;
    }

	public Response sendRequest() throws IOException {
		Response resp = null;
		try{
			Request request = buildRequest();
			logger.info(request.toString());
			if(udfHttpClient==null){
				resp = client.newCall(request).execute();
			}else{
				resp = udfHttpClient.newCall(request).execute();
			}
			return resp;
		}catch(Exception ex){
			throw new IOException(ex);
		}
	}

	/**
	 * 发起请求并返回下载流
	 *	注意：下载流在使用后应保证关闭
	 * @return the download stream
	 * @throws IOException the io exception
	 */
	public DownloadStream download() throws IOException {
		Response resp = null;
		try{
			resp = sendRequest();
			return new DownloadStream(resp);
		}catch(Exception ex){
			IOUtils.closeQuietly(resp);
			throw new IOException(ex);
		}
	}

	private Request buildRequest() {
		Request.Builder reqBuilder = new Request.Builder();
		reqBuilder.url(UrlUtils.setParms(this.url,this.urlParams));
		RequestBody body = null;
		if(this.multipartBodyBuilder!=null){
			body = multipartBodyBuilder.build();
		}else if(this.body!=null){
			body = RequestBody.create(contentType,this.body);
		}else if(this.formData!=null){
			body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"),UrlUtils.toQueryString(this.formData));
		}

		if(headers!=null){
			for(Entry<String, String> header:headers.entrySet()){
				reqBuilder.addHeader(header.getKey(),header.getValue());
			}
		}

		reqBuilder.method(this.method.name(),body);
		return reqBuilder.build();
	}

	public Requester addFormDataFire(String name,String fileName,File file){
		if(multipartBodyBuilder==null){
			multipartBodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
		}
		RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
		multipartBodyBuilder.addFormDataPart(name, fileName,fileBody);
		return this;
	}

	public Requester addPart(String name,String value){
		if(multipartBodyBuilder==null){
			multipartBodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
		}
		multipartBodyBuilder.addFormDataPart(name,value);
		return this;
	}
    
    public Requester addUrlParm(String key,Object value){
    	if(key!=null && value!=null){
    		if(urlParams==null){
        		urlParams = new TreeMap<String,Object>();
        	}
    		urlParams.put(key, value);
    	}
    	return this;
    }
    
    public Requester addFieldsAsUrlParm(Object obj){
    	if(obj!=null){
    		if(urlParams==null){
        		urlParams = new HashMap<String,Object>();
        	}
    		Morph.copy(urlParams, obj);
    	}
    	return this;
    }
    
    public Requester addFieldsAsUrlParm(Object obj,String[] fields){
    	if(obj!=null && fields!=null && fields.length>0){
    		for(String field:fields){
    			addUrlParm(field, Morph.get(obj, field));
    		}
    		Morph.copy(urlParams, obj);
    	}
    	return this;
    }
    
    public Requester addUrlParm(Map<String,Object> map){
    	if(map!=null && map.size()>0){
    		if(urlParams==null){
        		urlParams = new HashMap<String,Object>();
        	}
    		urlParams.putAll(map);
    	}
    	return this;
    }
    
    public Requester addFieldsAsUrlParm(Map<String,Object> map,String[] keys){
    	if(map!=null && map.size()>0){
    		for(String key:keys){
    			addUrlParm(key, map.get(key));
    		}
    	}
    	return this;
    }
    
	public String getUrl() {
		return url;
	}
	public Requester setUrl(String url) {
		this.url = url;
		return this;
	}
	public Map<String, Object> getUrlParams() {
		return urlParams;
	}
	public Requester setUrlParams(Map<String, Object> urlParams) {
		this.urlParams = urlParams;
		return this;
	}
	public Method getMethod() {
		return method;
	}
	public Requester setMethod(Method method) {
		this.method = method;
		return this;
	}
	public Requester setMethod(String method){
		this.method = Method.valueOf(method.toUpperCase());
		return this;
	}
	public static Logger getLogger() {
		return logger;
	}

	public byte[] getBody() {
		return body;
	}

	public Requester setBody(byte[] body) {
		this.body = body;
		return this;
	}
	
	public Requester setBody(String body) {
		this.body = body.getBytes(Strings.UTF8);
		return this;
	}

	public Requester addFormData(Map<String, Object> map) {
		if(map!=null && map.size()>0){
    		if(this.formData==null){
        		formData = new HashMap<String,Object>();
        	}
    		formData.putAll(map);
    	}
    	return this;
	}
	
	public Requester setHeader(String name,String value){
		if(headers==null){
			headers = new HashMap<String,String>();
		}
		headers.put(name, value);
		return this;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public Requester setHeaders(Map<String, String> headers) {
		this.headers = headers;
		return this;
	}

	public Requester setContentType(MediaType contentType){
		this.contentType = contentType;
		return this;
	}
}
