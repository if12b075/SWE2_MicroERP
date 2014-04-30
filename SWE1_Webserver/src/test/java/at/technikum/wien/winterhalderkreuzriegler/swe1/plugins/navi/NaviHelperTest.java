package at.technikum.wien.winterhalderkreuzriegler.swe1.plugins.navi;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class NaviHelperTest extends Assert {

	@Test
	public void testReadMap() {
		Map<String, List<String>> map = NaviHelper.parseOSM(new File(
				"src/test/resources/naviTest.xml"));
		assertNotNull(map);
		assertTrue(map.containsKey("TestStrasse1"));
		assertTrue(map.containsKey("TestStrasse2"));
		assertFalse(map.containsKey("TestStrasse3"));
		
		assertEquals(2, map.get("TestStrasse1").size());
		assertEquals(1, map.get("TestStrasse2").size());

	}

}
