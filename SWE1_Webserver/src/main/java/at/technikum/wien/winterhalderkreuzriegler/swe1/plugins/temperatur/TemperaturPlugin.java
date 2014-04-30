package at.technikum.wien.winterhalderkreuzriegler.swe1.plugins.temperatur;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import at.technikum.wien.winterhalderkreuzriegler.swe1.common.ResponseBuilder;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.enums.Method;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.enums.StatusCode;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.interfaces.Request;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.interfaces.Response;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.interfaces.Uri;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.helper.UriHelper;
import at.technikum.wien.winterhalderkreuzriegler.swe1.plugins.interfaces.Pluggable;

/**
 * 
 * Dies ist die Implementierung des TemperaturPlugins. Das angezeigte Datum muss
 * als Parameter uebergeben werden. Dies geht sowohl als POST sowie als GET.
 * Wenn kein Datum uebergeben wird, wird das heutige Datum ausgewaehlt. Wenn an
 * die URI /YYYY/MM/DD anfehaengt wird, wird von einer REST abfrage ausgegangen
 * und die Daten werden als XML ausgegeben.
 * 
 */
public class TemperaturPlugin implements Pluggable {

	private static final String TEXT_XML = "text/xml";
	private static final String SHOW_DATA = "showData";
	private static final String YEAR = "year";
	private static final String MONTH = "month";
	private static final String DAY = "day";
	private static final String DATE = "date";
	private SensorReader reader;
	private SensorDao sensorDao;

	@Override
	public Response request(Uri uri, Request request) {
		String path = UriHelper.convertPath(uri.getPath());
		String[] splittedPath = UriHelper.splitPath(path);
		if (splittedPath.length == 0 || splittedPath[0].isEmpty()
				|| splittedPath[0].equals(SHOW_DATA)) {
			return handleRequest(uri, request);
		} else if (splittedPath.length == 3) {
			return handleRestRequest(Integer.valueOf(splittedPath[0]),
					Integer.valueOf(splittedPath[1]),
					Integer.valueOf(splittedPath[2]));
		} else {
			return ResponseBuilder.buildResponse(StatusCode.STATUS_404);
		}
	}

	private Response handleRequest(Uri uri, Request request) {
		int year = TemperaturHelper.getYear();
		int month = TemperaturHelper.getMonth();
		int day = TemperaturHelper.getDay();
		if (Method.GET.equals(request.getMethod())) {
			Map<String, String> params = uri.getGETParams();
			if (params.containsKey(YEAR)) {
				year = Integer.valueOf(params.get(YEAR));
			}

			if (params.containsKey(MONTH)) {
				month = Integer.valueOf(params.get(MONTH));
			}

			if (params.containsKey(DAY)) {
				day = Integer.valueOf(params.get(DAY));
			}

		} else if (Method.POST.equals(request.getMethod())) {
			InputStreamReader reader = new InputStreamReader(request.getBody());
			try {
				StringWriter wr = new StringWriter();
				for (long i = 0; i < request.getContentLength(); i++) {
					wr.write(reader.read());
				}
				wr.close();
				String decodedParams = URLDecoder
						.decode(wr.toString(), "UTF-8");

				Map<String, String> params = UriHelper
						.getParamValue(decodedParams);
				if (params.containsKey(DATE)) {
					Calendar cal = Calendar.getInstance();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					try {
						cal.setTime(sdf.parse(params.get(DATE)));
					} catch (ParseException e) {
						return ResponseBuilder
								.buildResponse(StatusCode.STATUS_500);
					}
					year = cal.get(Calendar.YEAR);
					month = cal.get(Calendar.MONTH) + 1;
					day = cal.get(Calendar.DAY_OF_MONTH);
				}

			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
		} else {
			return ResponseBuilder.buildResponse(StatusCode.STATUS_404);
		}
		return wrapResult(year, month, day,
				sensorDao.loadData(year, month, day));
	}

	private Response wrapResult(int year, int month, int day,
			List<SensorData> loadSensorData) {
		Response r = ResponseBuilder.buildResponse(StatusCode.STATUS_200);
		StringBuilder body = new StringBuilder();
		body.append("<html><head><meta charset=\"utf-8\"></head>");
		body.append("<h1>Messwerte vom " + day + "." + month + "." + year
				+ "</h1>");

		body.append("<form method=\"POST\" action=\"showData\">");
		body.append("<input type=\"date\" name=\"date\" value=\"" + year + "-"
				+ month + "-" + day + "\" />");
		body.append("<input type=\"submit\" value=\"Senden\"/>");
		body.append("</form><br>");

		body.append("<table border=\"1\">");
		body.append("<tr><th>ID</th><th>Uhrzeit</th><th>Wert</th></tr>");
		for (SensorData data : loadSensorData) {
			body.append("<tr>");
			body.append("<td>");
			body.append(data.getId());
			body.append("</td>");
			body.append("<td>");
			SimpleDateFormat format = new SimpleDateFormat("HH:mm::ss");
			body.append(format.format(data.getTime()));
			body.append("</td>");
			body.append("<td>");
			body.append(data.getValue());
			body.append("</td>");
			body.append("</tr>");
		}
		body.append("</table>");

		body.append("</html>");

		r.setBody(new ByteArrayInputStream(body.toString().getBytes()));
		r.setContentLength(body.length());
		r.setContentType("text/html");
		return r;
	}

	private Response handleRestRequest(int year, int month, int day) {
		List<SensorData> dataList = sensorDao.loadData(year, month, day);

		String result = marshalSensorData(new SensorWrapper(dataList));

		Response response = ResponseBuilder
				.buildResponse(StatusCode.STATUS_200);
		response.setContentLength(result.length());
		response.setContentType(TEXT_XML);
		response.setBody(new ByteArrayInputStream(result.getBytes()));
		return response;
	}

	private String marshalSensorData(SensorWrapper wrapper) {
		try {
			JAXBContext jaxbContext = JAXBContext
					.newInstance(SensorWrapper.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// fuer eine schoene Formatierung ;-)
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			StringWriter writer = new StringWriter();

			jaxbMarshaller.marshal(wrapper, writer);
			return writer.toString();
		} catch (JAXBException e) {
			throw new IllegalStateException(e);
		}
	}

	@XmlRootElement
	private static class SensorWrapper {

		private List<SensorData> list = new ArrayList<SensorData>();

		@SuppressWarnings("unused")
		public SensorWrapper() {

		}

		public SensorWrapper(List<SensorData> list) {
			this.list = list;
		}

		@XmlElement(name = "data")
		public List<SensorData> getList() {
			return list;
		}

	}

	@Override
	public void start() {
		sensorDao = new SensorDao();
		reader = new SensorReader();
		reader.run();
	}

	@Override
	public void stop() {
		// Eventuell sollte man hier den Thread stoppen
	}

	private class SensorReader implements Runnable {

		@Override
		public void run() {
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					SensorData data = new SensorData();
					data.setValue(readValue());
					data.setTime(System.currentTimeMillis());
					sensorDao.createSensorData(data);
				}

				private double readValue() {
					return Math.random();
				}

			}, 0, 50000);
			// Alle 50 Sekunden ausfuehren
		}

	}

}
