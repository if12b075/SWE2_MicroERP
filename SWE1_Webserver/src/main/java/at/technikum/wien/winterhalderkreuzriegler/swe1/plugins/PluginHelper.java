package at.technikum.wien.winterhalderkreuzriegler.swe1.plugins;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import at.technikum.wien.winterhalderkreuzriegler.swe1.common.ResponseBuilder;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.enums.StatusCode;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.interfaces.Response;

/**
 * 
 * Helferklasse fuer die Plugins. Es wird derzeit nur von dem Static Plugin genutzt.
 * Es wird der Pfad zu der statischen HTML datei uebergeben. Diese wird geoeffnet und 
 * ein Response Objekt wird erzeugt. Dieses Response Objekt wird anschliessend richtig befuellt
 * und der Statuscode wird erzeugt und gesetzt.
 *
 */
public class PluginHelper {

	public static Response createFileResponse(String filePath) {
		File file = new File(filePath);
		InputStream repsponseBody;
		String contentType;
		long contentLength = 0;
		try {
			repsponseBody = new FileInputStream(file);
			contentType = Files.probeContentType(Paths.get(filePath));
			contentLength = Files.size(file.toPath());
		} catch (IOException e) {
			Response fault = ResponseBuilder
					.buildResponse(StatusCode.STATUS_404);
			return fault;
		}
		Response response = ResponseBuilder
				.buildResponse(StatusCode.STATUS_200);
		response.setBody(repsponseBody);
		response.setContentType(contentType);
		response.setContentLength(contentLength);
		response.setHeaders(new HashMap<String, String>());
		return response;
	}
}
