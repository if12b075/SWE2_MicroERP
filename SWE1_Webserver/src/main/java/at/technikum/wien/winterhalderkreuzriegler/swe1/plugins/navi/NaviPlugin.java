package at.technikum.wien.winterhalderkreuzriegler.swe1.plugins.navi;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.technikum.wien.winterhalderkreuzriegler.swe1.common.ResponseBuilder;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.enums.Method;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.enums.StatusCode;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.interfaces.Request;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.interfaces.Response;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.interfaces.Uri;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.helper.UriHelper;
import at.technikum.wien.winterhalderkreuzriegler.swe1.plugins.Cache;
import at.technikum.wien.winterhalderkreuzriegler.swe1.plugins.interfaces.Pluggable;

/**
 * 
 * Dies ist Implementierung des Navigationsplugins. Beim ersten aufruf wird die Map aus der .osm Datei erzeugt.
 * Anschliessend kann nach einem Strassennamen gesucht werden. Die gefunden Orte werden als Liste <li> in der HTML Seite
 * ausgegeben. Wenn keine passenden eintrage gefunden werden, wird eine entsprechende Meldung ausgegeben.
 * Waehrend die Map ausgebaut wird (die Karte neu eingelesen wird), kann keine suche durchgefuehrt werden und der Benutzer
 * erhaelt eine entsprechende Nachricht.
 *
 */
public class NaviPlugin implements Pluggable {

	private static final String TRUE = "true";
	private static final String STREET_KEY = "street";
	private static final String REFRESH_MAP = "refreshMap";
	protected static final String OSM_PATH_KEY = "osmPath";
	protected static final String DEFAULT_OSM_PATH = "D:/austria-latest.osm";
	private Map<String, List<String>> streetMap = new HashMap<String, List<String>>();
	private boolean isBlocked = false;

	@Override
	public Response request(Uri uri, Request request) {
		if (isBlocked) {
			return blockResponse();
		} else {
			String street = null;
			if (Method.GET.equals(request.getMethod())) {
				street = uri.getGETParams().get(STREET_KEY);
				String refreshMap = uri.getGETParams().get(REFRESH_MAP);
				if (refreshMap != null && TRUE.equals(refreshMap)) {
					loadStreetMap();
					return answerReloadStreetMap();
				}
			} else if (Method.POST.equals(request.getMethod())) {
				InputStreamReader reader = new InputStreamReader(
						request.getBody());
				try {
					StringWriter wr = new StringWriter();
					for (long i = 0; i < request.getContentLength(); i++) {
						wr.write(reader.read());
					}
					wr.close();
					String decodedParams = URLDecoder.decode(wr.toString(),
							"UTF-8");

					street = UriHelper.getParamValue(decodedParams).get(
							STREET_KEY);
				} catch (IOException e) {
					throw new IllegalStateException(e);
				}
			} else {
				return ResponseBuilder.buildResponse(StatusCode.STATUS_404);
			}
			return handleRequest(street);
		}
	}

	private Response answerReloadStreetMap() {
		Response r = ResponseBuilder.buildResponse(StatusCode.STATUS_200);
		StringBuilder body = new StringBuilder();
		body.append("<h3>Die Karte wird neu eingelesen. W&auml;hrend dieser Zeit werden keine Anfragen beantwortet!</h3>");
		r.setBody(new ByteArrayInputStream(body.toString().getBytes()));
		r.setContentLength(body.length());
		r.setContentType("text/html");
		return r;
	}

	private Response handleRequest(String street) {
		Response r = ResponseBuilder.buildResponse(StatusCode.STATUS_200);
		StringBuilder body = new StringBuilder();

		body.append("<html><head><meta charset=\"utf-8\"></head>");

		body.append("<h1>Navigationsplugin</h1>");

		body.append("<form method=\"GET\" action=\"navi\">");
		body.append("<input type=\"text\" name=\"" + STREET_KEY + "\"/>");
		body.append("<input type=\"submit\" value=\"Senden\"/>");
		body.append("</form>");

		body.append("<a href=\"navi?" + REFRESH_MAP + "=" + TRUE
				+ "\">Sta&szlig;enkarte neu aufbereiten</a>");

		if (street != null) {
			if (streetMap.containsKey(street)) {
				List<String> cities = streetMap.get(street);
				body.append("<h3>Es gibt "
						+ cities.size()
						+ " Gemeinden/St&auml;dte in den(en) die Stra&szlig;e: "
						+ street + " vorkommt!</h3>");
				body.append("<ul>");
				for (String city : cities) {
					body.append("<li>" + city + "</li>");
				}
				body.append("</ul>");
			} else {
				body.append("<h3>Es wurden keine Orte f&uuml;r " + street
						+ " gefunden!</h3>");
			}
		}
		body.append("</html>");

		r.setBody(new ByteArrayInputStream(body.toString().getBytes()));
		r.setContentLength(body.length());
		r.setContentType("text/html");
		return r;
	}

	private Response blockResponse() {
		Response r = ResponseBuilder.buildResponse(StatusCode.STATUS_200);
		StringBuilder body = new StringBuilder();
		body.append("<h1>Leider kann derzeit keine Anfrage beantwortet werden, da die OpenStreetMap aktualisiert wird!</h1>");
		r.setBody(new ByteArrayInputStream(body.toString().getBytes()));
		r.setContentLength(body.length());
		r.setContentType("text/html");
		return r;
	}

	@Override
	public void start() {
		loadStreetMap();
	}

	private void loadStreetMap() {
		Thread thread = new Thread(new Runnable() {

			public void run() {
				isBlocked = true;
				long startTime = System.currentTimeMillis();
				streetMap = NaviHelper.parseOSM(new File(Cache.properties
						.getProperty(OSM_PATH_KEY, DEFAULT_OSM_PATH)));
				System.out.println("Einlesezeit: "
						+ (System.currentTimeMillis() - startTime) / 1000 + "s");
				isBlocked = false;
				System.out.println("StreetMap fertig eingelesen!");
			}
		});
		thread.start();
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
	}

}
