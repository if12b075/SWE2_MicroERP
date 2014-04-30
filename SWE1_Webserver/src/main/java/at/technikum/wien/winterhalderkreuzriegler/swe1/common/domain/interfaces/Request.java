package at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.interfaces;


import java.io.InputStream;
import java.util.Map;

import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.enums.Method;

/**
 * 
 * Interface fuer die HTTPRequest Klasse
 *
 */
public interface Request {

	public Method getMethod();

	public void setMethod(Method method);

	public Map<String, String> getHeaders();

	public void setHeaders(Map<String, String> headers);

	public String getContentType();

	public void setContentType(String type);

	public Long getContentLength();

	public void setContentLength(Long length);

	public InputStream getBody();

	public void setBody(InputStream body);

	public void addToHeader(String key, String value);
}
