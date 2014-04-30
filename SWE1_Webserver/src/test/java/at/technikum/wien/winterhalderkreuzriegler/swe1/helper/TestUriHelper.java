package at.technikum.wien.winterhalderkreuzriegler.swe1.helper;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import at.technikum.wien.winterhalderkreuzriegler.swe1.common.helper.UriHelper;

public class TestUriHelper extends Assert {
	
	@Test
	public void testConvert(){
		
		String converted = UriHelper.convertPath("/static/index.html");
		assertEquals("index.html", converted);
	}
	
/*	@Test 
	public void testSplit(){
		String[] splited = UriHelper.splitPath("/static/index.html");
		String[] control = {"","static","index.html"};
		assertEquals(control, splited);
	}*/
	
	@Test
	public void testGetValueParams(){
		Map<String, String> params = UriHelper.getParamValue("");
		Map<String, String> control = new HashMap<>();
		
		assertEquals(control, params);
	}
	
	@Test
	public void testGetValueParams2(){
		Map<String, String> params = UriHelper.getParamValue("attr1=value1&attr2=value2&attr3=value3&attr4=value4");
		Map<String, String> control = new HashMap<>();
		control.put("attr1", "value1");
		control.put("attr2", "value2");
		control.put("attr3", "value3");
		control.put("attr4", "value4");
		
		assertEquals(control, params);
	}
}
