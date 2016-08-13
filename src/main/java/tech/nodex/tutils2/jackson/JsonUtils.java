package tech.nodex.tutils2.jackson;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.io.StringWriter;

public class JsonUtils {
	private static final ObjectMapper DEFAULT_MAPPER = new ObjectMapper();
	
	public static String toJson(Object object){
		return toJson(object,DEFAULT_MAPPER);
	}
	
	public static String toJson(Object object,ObjectMapper mapper){
		StringWriter sw = new StringWriter();
		try {
			mapper.writeValue(sw,object);
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
		return sw.toString();
	}
	
	public static <T>T fromJson(String json,Class<T> type){
		try {
			return DEFAULT_MAPPER.readValue(json, type);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	public static <T>T fromJson(String json,TypeReference<T> typeReference){
		try {
			return DEFAULT_MAPPER.readValue(json, typeReference);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	public static <T>T fromJson(byte[] json,Class<T> type){
		return fromJson(json,type,DEFAULT_MAPPER);
	}
	
	public static <T>T fromJson(byte[] json,Class<T> type,ObjectMapper mapper){
		try {
			return mapper.readValue(json, type);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	public static ObjectNode readJsonObject(byte[] json){
		try {
			return (ObjectNode)DEFAULT_MAPPER.readTree(json);
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		} 
	}
	
	public static ObjectNode readJsonObject(String json){
		try {
			return (ObjectNode)DEFAULT_MAPPER.readTree(json);
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		} 
	}
	
	public static long readJsonNumber(String json){
		try {
			return DEFAULT_MAPPER.readValue(json, Long.class);
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		} 
	}
	
	public static long readJsonNumber(byte[] json){
		try {
			return DEFAULT_MAPPER.readValue(json, Long.class);
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		} 
	}
}
