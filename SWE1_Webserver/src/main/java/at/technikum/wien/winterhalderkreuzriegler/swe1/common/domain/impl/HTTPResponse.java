package at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.impl;

import java.io.InputStream;
import java.util.Map;

import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.enums.StatusCode;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.interfaces.Response;

/**
 * 
 * Die HTTPResponse Klasse stellt einen HTTPResponse dar. Es werden alle relevanten Daten fuer den Response als Attribute 
 * gehalten. Insbeondere der statusCode, contentLength, contentType. Die restlichen header werden als Map gespeichert und
 * zur weiteren Verarbeitung als InputStream. Diese Klasse ist eine reine Datenhalter Klasse.
 * 
 *
 */
public class HTTPResponse implements Response {

	private StatusCode statusCode;
	private Long contentLength;
	private String contentType;
	private InputStream body;
	private Map<String, String> headers;

	@Override
	public StatusCode getStatusCode() {
		return statusCode;
	}

	@Override
	public void setStatusCode(StatusCode code) {
		this.statusCode = code;
	}

	@Override
	public Long getContentLength() {
		return contentLength;
	}

	@Override
	public void setContentLength(long length) {
		this.contentLength = length;
	}

	@Override
	public String getContentType() {
		return contentType;
	}

	@Override
	public void setContentType(String type) {
		this.contentType = type;
	}

	@Override
	public InputStream getBody() {
		return body;
	}

	@Override
	public void setBody(InputStream body) {
		this.body = body;
	}

	@Override
	public Map<String, String> getHeaders() {
		return headers;
	}

	@Override
	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public String toString() {
		StringBuilder out = new StringBuilder();

		return out.toString();
	}

}
