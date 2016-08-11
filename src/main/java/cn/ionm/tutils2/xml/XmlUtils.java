package cn.ionm.tutils2.xml;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import static cn.ionm.tutils2.jackson.JsonUtils.fromJson;
import static cn.ionm.tutils2.jackson.JsonUtils.toJson;

public class XmlUtils {
	private static final XmlMapper DEFAULT_MAPPER = new XmlMapper();

	static{
		DEFAULT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}
	
	public static String toXml(Object object){
		return toJson(object,DEFAULT_MAPPER);
	}
	
	public static String toXml(Object object,ObjectMapper mapper){
		StringWriter sw = new StringWriter();
		try {
			mapper.writeValue(sw,object);
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
		return sw.toString();
	}
	
	public static <T>T fromXml(String xml,Class<T> type){
		try {
			return DEFAULT_MAPPER.readValue(xml, type);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	public static <T>T fromXml(String xml,TypeReference<T> typeReference){
		try {
			return DEFAULT_MAPPER.readValue(xml, typeReference);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public static Map<String,Object> toMap(byte[] xml){
		try {
			return DEFAULT_MAPPER.readValue(xml,Map.class);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public static Map<String,Object> toMap(String xml){
		try {
			return DEFAULT_MAPPER.readValue(xml,Map.class);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	public static <T>T fromXml(byte[] xml,Class<T> type){
		return fromJson(xml,type,DEFAULT_MAPPER);
	}
	
	public static <T>T fromXml(byte[] xml,Class<T> type,XmlMapper mapper){
		try {
			return mapper.readValue(xml, type);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
}
