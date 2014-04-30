package at.technikum.wien.winterhalderkreuzriegler.swe1.helper;

import org.junit.Assert;
import org.junit.Test;

import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.enums.StatusCode;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.interfaces.Response;
import at.technikum.wien.winterhalderkreuzriegler.swe1.plugins.PluginHelper;

public class TestPluginHelper extends Assert {

	@Test
	public void testCreateResponse() {
		Response r = PluginHelper.createFileResponse("www/index.html");
		assertNotNull(r);
		assertEquals("text/html", r.getContentType());
		assertEquals(StatusCode.STATUS_200, r.getStatusCode());
	}

	@Test
	public void testCreateResponseImage() {
		Response r = PluginHelper.createFileResponse("www/Logo_FHTW.jpg");
		assertNotNull(r);
		assertEquals("image/jpeg", r.getContentType());
		assertEquals(StatusCode.STATUS_200, r.getStatusCode());
	}
	
	@Test
	public void testCreateResponsePDF() {
		Response r = PluginHelper.createFileResponse("www/SWE1_Uebungsbeispiel.pdf");
		assertNotNull(r);
		assertEquals("application/pdf", r.getContentType());
		assertEquals(StatusCode.STATUS_200, r.getStatusCode());
	}

}
