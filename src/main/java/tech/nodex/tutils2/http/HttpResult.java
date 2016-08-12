package tech.nodex.tutils2.http;

public class HttpResult {
	private int code;
	private Exception error;
	private byte[] respBody;
	private String contentType;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public Exception getError() {
		return error;
	}
	public void setError(Exception error) {
		this.error = error;
	}
	public byte[] getRespBody() {
		return respBody;
	}
	public void setRespBody(byte[] respBody) {
		this.respBody = respBody;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
}
