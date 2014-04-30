package at.technikum.wien.winterhalderkreuzriegler.swe1.plugins.interfaces;

import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.enums.StatusCode;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.interfaces.Request;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.interfaces.Response;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.interfaces.Uri;

public interface PluginManager {

	/**
	 * Anhand der {@link Uri} wird entschieden, welches Plugin (
	 * {@link Pluggable}) geladen wird und eben dieses wird ausgefuehrt
	 * 
	 * @param uri
	 *            {@link Uri} der Webanfrage
	 * @param request
	 *            {@link Request} der Webanfrage
	 * @return Der jeweilige {@link Response} des Plugins, wenn kein passendes
	 *         Plugin gefunden/geladen, {@link Response} mit
	 *         {@link StatusCode#STATUS_404}
	 */
	public Response excecuteRequest(Uri uri, Request request);

}
