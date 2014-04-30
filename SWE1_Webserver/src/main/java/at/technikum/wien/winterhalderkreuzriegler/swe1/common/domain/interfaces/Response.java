package at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.interfaces;

import java.io.InputStream;
import java.util.Map;

import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.enums.StatusCode;

/**
 * 
 * Interface fuer die HTTPResponse Klasse
 *
 */
public interface Response {

	public StatusCode getStatusCode();

	public void setStatusCode(StatusCode code);

	public Long getContentLength();

	public void setContentLength(long length);

	public String getContentType();

	public void setContentType(String type);

	public InputStream getBody();

	public void setBody(InputStream o);

	public Map<String, String> getHeaders();

	public void setHeaders(Map<String, String> headers);

}
