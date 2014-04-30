/**
 * 
 */
package at.technikum.wien.winterhalderkreuzriegler.swe1.plugins.impl;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import at.technikum.wien.winterhalderkreuzriegler.swe1.common.WebserverConstants;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.enums.Method;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.enums.Protocol;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.enums.StatusCode;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.interfaces.Request;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.interfaces.Response;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.interfaces.Uri;
import at.technikum.wien.winterhalderkreuzriegler.swe1.helper.TestHelper;
import at.technikum.wien.winterhalderkreuzriegler.swe1.plugins.interfaces.PluginManager;

/**
 * 
 * Testklasse fuer {@link PluginManager}
 * 
 * @author Matthias
 * 
 */
public class PluginManagerTest extends Assert {

	private PluginManager pluginManager;

	private TestHelper helper;

	@Before
	public void setUp() {
		pluginManager = new PluginManagerImpl();
		helper = new TestHelper();
	}

	@Test
	public void testExecuteRequest() {
		Uri uri = helper.createUri("localhost", "/test/index.html",
				WebserverConstants.PORT, Protocol.HTTP,
				new HashMap<String, String>());
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Language", "de");
		Request request = helper.createRequest(null, 0l, "text/html", headers,
				Method.GET);
		Response response = pluginManager.excecuteRequest(uri, request);
		assertNotNull(response);
		assertEquals(StatusCode.STATUS_200, response.getStatusCode());
	}

	@Test
	public void testExecuteRequestStatic() {
		Uri uri = helper.createUri("localhost", "/static/index.html",
				WebserverConstants.PORT, Protocol.HTTP,
				new HashMap<String, String>());
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Language", "de");
		Request request = helper.createRequest(null, 0l, "text/html", headers,
				Method.GET);
		Response response = pluginManager.excecuteRequest(uri, request);
		assertNotNull(response);
		assertEquals(StatusCode.STATUS_200, response.getStatusCode());
		assertEquals("text/html", response.getContentType());
	}

	@Test
	public void testExecuteRequestTemp() {
		Uri uri = helper.createUri("localhost", "/GetTemperature/2012/10/1",
				WebserverConstants.PORT, Protocol.HTTP,
				new HashMap<String, String>());
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Language", "de");
		Request request = helper.createRequest(null, 0l, "text/html", headers,
				Method.GET);
		Response response = pluginManager.excecuteRequest(uri, request);
		assertNotNull(response);
		assertEquals(StatusCode.STATUS_200, response.getStatusCode());
		assertEquals("text/xml", response.getContentType());
	}
}
