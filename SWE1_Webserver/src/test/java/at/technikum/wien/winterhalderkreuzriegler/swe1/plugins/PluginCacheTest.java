package at.technikum.wien.winterhalderkreuzriegler.swe1.plugins;

import org.junit.Assert;
import org.junit.Test;

import at.technikum.wien.winterhalderkreuzriegler.swe1.common.WebserverConstants;

public class PluginCacheTest extends Assert {

	@Test
	public void testRefreshPlugins() {
		Assert.assertEquals(5, Cache.plugins.size());
		Cache.plugins.get("test").request(null, null);
	}

	@Test
	public void refreshProperties() {
		Cache.refreshCache();
		assertEquals("www",
				Cache.properties.getProperty(WebserverConstants.WWW_HOME_KEY));
	}

}
