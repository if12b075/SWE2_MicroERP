/**
 * 
 */
package at.technikum.wien.winterhalderkreuzriegler.swe1.plugins.adminplugin;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URLDecoder;

import at.technikum.wien.winterhalderkreuzriegler.swe1.common.ResponseBuilder;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.WebserverConstants;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.enums.StatusCode;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.interfaces.Request;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.interfaces.Response;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.interfaces.Uri;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.helper.UriHelper;
import at.technikum.wien.winterhalderkreuzriegler.swe1.plugins.Cache;
import at.technikum.wien.winterhalderkreuzriegler.swe1.plugins.interfaces.Pluggable;

/**
 * Das AdminPlugin ist das 'Eigene' Plugin. Es wird fuer die Verwaltung der
 * anderen Plugins benutzt. Hier koennen die Plugins ein- und ausgeschaltet
 * werden und die Konfiguration der Plugins kann veraendert werden.
 * 
 */
public class AdminPlugin implements Pluggable {

	@Override
	public Response request(Uri uri, Request request) {
		String path = UriHelper.convertPath(uri.getPath());
		if ("".equals(path) || "index.html".equals(path)) {
			return createIndexResponse();
		} else if ("change".equals(path)) {
			return change(request);
		}
		return ResponseBuilder.buildResponse(StatusCode.STATUS_404);
	}

	private Response change(Request request) {
		Response r = ResponseBuilder.buildResponse(StatusCode.STATUS_200);
		InputStreamReader reader = new InputStreamReader(request.getBody());
		try {
			StringWriter wr = new StringWriter();
			for (long i = 0; i < request.getContentLength(); i++) {
				wr.write(reader.read());
			}
			wr.close();
			String decodedString = URLDecoder.decode(wr.toString(), "UTF-8");
			FileOutputStream fop = new FileOutputStream(
					WebserverConstants.RELATIVE_CONFIG_FILE_PATH);

			byte[] contentInBytes = decodedString.substring(
					decodedString.indexOf('=') + 1).getBytes();

			fop.write(contentInBytes);
			fop.flush();
			fop.close();

			Cache.refreshCache();

		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
		String body = "<h1>Well done ;-)</h1>";
		r.setBody(new ByteArrayInputStream(body.getBytes()));
		r.setContentLength(body.length());
		r.setContentType("text/html");
		return r;
	}

	private Response createIndexResponse() {
		Response r = ResponseBuilder.buildResponse(StatusCode.STATUS_200);
		StringBuilder body = new StringBuilder();
		body.append("<h1>Welcome to the AdminPlugin</h1>");
		body.append("<form method=\"POST\" action=\"admin/change\">");
		body.append("<textarea rows=\"10\" cols=\"50\" name=\"config\">");
		try {
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new FileReader(
					WebserverConstants.RELATIVE_CONFIG_FILE_PATH));
			String line;
			while ((line = reader.readLine()) != null) {
				body.append(line + "\n");
			}
		} catch (FileNotFoundException e) {
			throw new IllegalStateException(e);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
		body.append("</textarea><br>");
		body.append("<input type=\"submit\" value=\"Senden\" />");
		body.append("</form>");
		// body.append("<a href=\"admin/pluginForm\">Upload Plugin</a>");
		r.setBody(new ByteArrayInputStream(body.toString().getBytes()));
		r.setContentLength(body.length());
		r.setContentType("text/html");
		return r;
	}

	@Override
	public void start() {
		// Nothing to do
		System.out.println("AdminPlugin startet");
	}

	@Override
	public void stop() {
		// Nothing to do
		System.out.println("AdminPlugin beendet");
	}

	// public Response uploadPluginForm() {
	// Response r = ResponseBuilder.buildResponse(StatusCode.STATUS_200);
	// StringBuilder body = new StringBuilder();
	// body.append("<h1>Upload your Plugin</h1>");
	// body.append("<form method=\"POST\" action=\"uploadPlugin\" enctype=\"multipart/form-data\">");
	// body.append("<input type=\"file\" name=\"file\" /><br>");
	// body.append("<input type=\"submit\" value=\"Senden\" />");
	// body.append("</form>");
	// r.setBody(new ByteArrayInputStream(body.toString().getBytes()));
	// r.setContentLength(body.length());
	// r.setContentType("text/html");
	// return r;
	// }
	//
	// public Response uploadPlugin(Request request) {
	// InputStreamReader reader = new InputStreamReader(request.getBody());
	// StringWriter wr = new StringWriter();
	// try {
	// for (long i = 0; i < request.getContentLength(); i++) {
	// wr.write(reader.read());
	// }
	// wr.close();
	// System.out.println(wr.toString());
	// } catch (IOException e) {
	// throw new IllegalStateException(e);
	// }
	//
	// Response r = ResponseBuilder.buildResponse(StatusCode.STATUS_200);
	// StringBuilder body = new StringBuilder();
	// body.append("<h1>Plugin uploaded...</h1>");
	// r.setBody(new ByteArrayInputStream(body.toString().getBytes()));
	// r.setContentLength(body.length());
	// r.setContentType("text/html");
	// return r;
	// }

}
