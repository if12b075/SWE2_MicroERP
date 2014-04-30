/**
 * 
 */
package at.technikum.wien.winterhalderkreuzriegler.swe1.plugins.staticplugin;

import at.technikum.wien.winterhalderkreuzriegler.swe1.common.WebserverConstants;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.interfaces.Request;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.interfaces.Response;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.interfaces.Uri;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.helper.UriHelper;
import at.technikum.wien.winterhalderkreuzriegler.swe1.plugins.Cache;
import at.technikum.wien.winterhalderkreuzriegler.swe1.plugins.PluginHelper;
import at.technikum.wien.winterhalderkreuzriegler.swe1.plugins.interfaces.Pluggable;

/**
 * 
 * Implementierung zum Interface {@link Pluggable}
 * 
 * Diese Plugin liefert statisch Files aus dem Filesystem des Servers an den
 * Aufrufer zurueck. Die HTML Datein muessen sich in einem, in der Konfiguration bestimmten Ordner
 * befinden.
 * 
 * 
 */
public class StaticPlugin implements Pluggable {

	@Override
	public Response request(Uri uri, Request request) {
		String filePath = UriHelper.convertPath(uri.getPath());
		return PluginHelper.createFileResponse(Cache.properties
				.getProperty(WebserverConstants.WWW_HOME_KEY) + "/" + filePath);
	}

	@Override
	public void start() {
		// Nothing to do
	}

	@Override
	public void stop() {
		// Nothing to do
	}
}
