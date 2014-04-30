package at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.impl;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.enums.Method;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.interfaces.Request;

/**
 * 
 * Die HTTPRequest Klasse stellt einen HTTP aufruf da. Es werden alle wichtigen Information eines Requests gespeichert.
 * Es wird die Methode, der ContentType, die ContentLenth als Attribut gespeichert. Alles weiteren Headerinformation werden
 * in der Headers Map gespeichert. Der Body wird direkt als InputStream gespeichert, somit kann spaeter noch, je nach Plugin
 * entschieden werden wie die Daten ausgelesen werden. Diese Klasse ist eine reine Datenhalterklasse.
 *
 */
public class HTTPRequest implements Request {

	private Method method;
	private Map<String, String> headers;
	private String contentType;
	private Long contentLength;
	private InputStream body;

	public HTTPRequest() {
		headers = new HashMap<String, String>();
	}

	@Override
	public Method getMethod() {
		return method;
	}

	@Override
	public void setMethod(Method method) {
		this.method = method;
	}

	@Override
	public Map<String, String> getHeaders() {
		return headers;
	}

	@Override
	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
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
	public Long getContentLength() {
		return contentLength;
	}

	@Override
	public void setContentLength(Long length) {
		this.contentLength = length;
	}

	@Override
	public InputStream getBody() {
		return body;
	}

	@Override
	public void setBody(InputStream body) {
		this.body = body;
	}

	public void addToHeader(String key, String value) {
		headers.put(key, value);
	}

	public String toString() {
		StringBuilder out = new StringBuilder();
		out.append("REQUEST: \n");
		out.append("method: " + method);
		out.append("\n");
		out.append("contentType: " + contentType);
		out.append("\n");
		out.append("contentLength: " + contentLength);
		out.append("\n");
		for (Map.Entry<String, String> e : headers.entrySet()) {
			out.append(e.getKey() + ": " + e.getValue());
			out.append("\n");
		}

		return out.toString();
	}

}
