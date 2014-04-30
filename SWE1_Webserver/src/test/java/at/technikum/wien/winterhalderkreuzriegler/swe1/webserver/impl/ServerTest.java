package at.technikum.wien.winterhalderkreuzriegler.swe1.webserver.impl;
/*
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import at.technikum.wien.winterhalderkreuzriegler.swe1.common.WebserverConstants;

public class ServerTest extends Assert {
/*
	@BeforeClass
	public static void setUp() throws Exception {
		Server srv = new Server();
		srv.main(new String[] {});
	}

	@Test
	public void runServer() throws IOException, URISyntaxException {
		// Try to Call Server
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(new URI("http", null, "localhost",
				WebserverConstants.PORT, "/static/index.html", null, "anchor"));
		HttpResponse response = client.execute(request);

		// Get the response
		BufferedReader rd = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent()));

		assertEquals("<!DOCTYPE html>", rd.readLine());
		assertEquals("<html>", rd.readLine());
		assertEquals("<head>", rd.readLine());
		// etc.
	}

	@Test
	public void mutliUserTest() throws IOException, URISyntaxException,
			InterruptedException {

		List<Thread> threads = new ArrayList<Thread>();

		for (int i = 0; i < 100; i++) {
			TestHandler h = new TestHandler();
			Thread th = new Thread(h);
			threads.add(th);
			th.start();
		}

		for (Thread th : threads) {
			th.join();
		}

	}

	private class TestHandler implements Runnable {
		public void run() {
			// Try to Call Server
			HttpClient client = new DefaultHttpClient();
			HttpGet request;
			try {
				request = new HttpGet(new URI("http", null, "localhost",
						WebserverConstants.PORT, "/static/index.html", null,
						"anchor"));

				HttpResponse response = client.execute(request);

				// Get the response
				BufferedReader rd = new BufferedReader(new InputStreamReader(
						response.getEntity().getContent()));

				assertEquals("<!DOCTYPE html>", rd.readLine());
				assertEquals("<html>", rd.readLine());
				assertEquals("<head>", rd.readLine());
				// etc.

			} catch (Exception e) {
				assertTrue(false);
			}

		}
	}
}*/

