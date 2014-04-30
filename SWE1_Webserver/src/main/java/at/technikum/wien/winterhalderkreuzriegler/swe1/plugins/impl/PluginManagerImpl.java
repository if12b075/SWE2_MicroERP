/**
 * 
 */
package at.technikum.wien.winterhalderkreuzriegler.swe1.plugins.impl;

import at.technikum.wien.winterhalderkreuzriegler.swe1.common.ResponseBuilder;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.enums.StatusCode;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.interfaces.Request;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.interfaces.Response;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.interfaces.Uri;
import at.technikum.wien.winterhalderkreuzriegler.swe1.plugins.Cache;
import at.technikum.wien.winterhalderkreuzriegler.swe1.plugins.interfaces.PluginManager;

/**
 * Implementierung des Interface {@link PluginManager}
 * 
 * 
 */
public class PluginManagerImpl implements PluginManager {

	@Override
	public Response excecuteRequest(Uri uri, Request request) {
		if (uri != null && uri.getPath() != null) {
			String pluginKey = uri.getPath().split("\\/")[1];
			if (pluginKey != null && Cache.plugins.containsKey(pluginKey)) {
				return Cache.plugins.get(pluginKey).request(uri, request);
			}
		}
		return ResponseBuilder.buildResponse(StatusCode.STATUS_404);
	}
}
