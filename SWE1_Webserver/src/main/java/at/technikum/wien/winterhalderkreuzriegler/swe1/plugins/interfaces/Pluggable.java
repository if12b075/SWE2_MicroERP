package at.technikum.wien.winterhalderkreuzriegler.swe1.plugins.interfaces;

import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.interfaces.Request;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.interfaces.Response;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.interfaces.Uri;

/**
 * 
 * Interface fuer alle Implementierungen eines Plugins. Alle Plugins muessen dieses Interface implementieren.
 * 
 * 
 */
public interface Pluggable {

	/**
	 * Der Request wird von diesem Plugin bearbeitet und liefert den
	 * dementsprechenden Response
	 * 
	 * @param request
	 *            Der eingehende Request vom Client
	 * @param uri
	 *            Die Uri des Requests
	 * @return Je nach implementierung des Plugins der dementsprechende
	 *         {@link Response}
	 */
	public Response request(Uri uri, Request request);

	/**
	 * Startet das Plugin
	 */
	public void start();

	/**
	 * Beendet das Plugin mit allen Threads
	 */
	public void stop();

}
