package at.technikum.wien.winterhalderkreuzriegler.swe1.common;

import org.junit.Assert;
import org.junit.Test;

import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.enums.StatusCode;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.interfaces.Response;

public class ResponseBuilderTest extends Assert {

	@Test
	public void testCreate() {
		Response response = ResponseBuilder
				.buildResponse(StatusCode.STATUS_200);
		assertNotNull(response);
		assertEquals(StatusCode.STATUS_200, response.getStatusCode());
		assertNotNull(response.getHeaders());
		assertNull(response.getContentType());
		assertNull(response.getBody());
		assertNull(response.getContentLength());
	}

}
