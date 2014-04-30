package at.technikum.wien.winterhalderkreuzriegler.swe1.plugins.temperatur;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import at.technikum.wien.winterhalderkreuzriegler.swe1.common.WebserverConstants;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.enums.Method;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.enums.Protocol;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.enums.StatusCode;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.interfaces.Request;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.interfaces.Response;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.interfaces.Uri;
import at.technikum.wien.winterhalderkreuzriegler.swe1.helper.TestHelper;

public class TestTemperaturePlugin extends Assert {

	private TemperaturPlugin plugin;
	private TestHelper helper;

	@Before
	public void setUp() {
		plugin = new TemperaturPlugin();
		plugin.start();
		helper = new TestHelper();
	}

	@Test
	public void testREST() {
		Uri uri = helper.createUri("localhost", "/GetTemperature/2018/01/01",
				WebserverConstants.PORT, Protocol.HTTP,
				new HashMap<String, String>());
		Request request = helper.createRequest(null, null, null, null,
				Method.GET);
		Response response = plugin.request(uri, request);

		assertNotNull(response);
		assertEquals(StatusCode.STATUS_200, response.getStatusCode());
		assertEquals("text/xml", response.getContentType());
	}

	@Test
	public void testHTTP() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("year", "2018");
		map.put("month", "01");
		map.put("day", "01");
		Uri uri = helper.createUri("localhost", "GetTemperature",
				WebserverConstants.PORT, Protocol.HTTP, map);
		Request request = helper.createRequest(null, null, null, null,
				Method.GET);
		Response response = plugin.request(uri, request);

		assertNotNull(response);
		assertEquals(StatusCode.STATUS_200, response.getStatusCode());
		assertEquals("text/html", response.getContentType());
	}

	@Test
	@Ignore
	public void insertData() {

		Calendar toDay = Calendar.getInstance();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 10);

		SensorDao dao = new SensorDao();

		while (cal.getTimeInMillis() < toDay.getTimeInMillis()) {
			for (int i = 1; i <= 5; i++) {
				SensorData data = new SensorData();
				data.setTime(cal.getTimeInMillis() + (8640000 * i));
				data.setValue(Math.random());
				dao.createSensorData(data);

			}
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + 1);
		}
	}

}
