package at.technikum.wien.winterhalderkreuzriegler.swe1.webserver.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import at.technikum.wien.winterhalderkreuzriegler.swe1.common.ResponseBuilder;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.WebserverConstants;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.enums.Method;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.enums.Protocol;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.enums.StatusCode;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.enums.Version;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.impl.HTTPRequest;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.impl.UriImpl;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.interfaces.Request;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.interfaces.Response;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.interfaces.Uri;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.helper.UriHelper;
import at.technikum.wien.winterhalderkreuzriegler.swe1.plugins.Cache;
import at.technikum.wien.winterhalderkreuzriegler.swe1.plugins.impl.PluginManagerImpl;
import at.technikum.wien.winterhalderkreuzriegler.swe1.plugins.interfaces.PluginManager;

/**
 * 
 * Dies ist die Implementierung des Webservers. Erst wird ein Socket geoeffnet
 * der auf den vordefinierten Port hoert. Anschliessend wird auf eine Verbindung
 * von einem client gewartet. Fragt ein Client an wird ein neuer Thread
 * gestartet und diesen der neue Socket uebergeben. Anschliessend wird ein
 * HTTPRequest und Uri Objekt erzeugt und geparse. Diese beiden Objekte werden
 * dem PluginManager uebergeben, der diese anschliessend weiterverarbeitet und
 * entscheidet welches Plugin geladen werden muss.
 * 
 * 
 */
public class Server {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {

		// Plugins das erste mal laden und Starten
		Cache.refreshCache();

		final ExecutorService pool;
		final ServerSocket serverSocket;
		String var = "C";
		String zusatz;
		if (args.length > 0)
			var = args[0].toUpperCase();
		if (var == "C") {
			// Liefert einen Thread-Pool, dem bei Bedarf neue Threads
			// hinzugefuegt
			// werden. Vorrangig werden jedoch vorhandene freie Threads benutzt.
			pool = Executors.newCachedThreadPool();
			zusatz = "CachedThreadPool";
		} else {
			int poolSize = 10;
			// Liefert einen Thread-Pool fuer maximal poolSize Threads
			pool = Executors.newFixedThreadPool(poolSize);
			zusatz = "poolsize=" + poolSize;
		}
		serverSocket = new ServerSocket(WebserverConstants.PORT);
		// Thread zur Behandlung der Client-Server-Kommunikation, der Thread-
		// Parameter liefert das Runnable-Interface (also die run-Methode fuer
		// t1).
		Thread t1 = new Thread(new NetworkService(pool, serverSocket));
		System.out.println("Start NetworkService(Multiplikation), " + zusatz
				+ ", Thread: " + Thread.currentThread());
		// Start der run-Methode von NetworkService: warten auf Client-request
		t1.start();
		//
		// reagiert auf Strg+C, der Thread(Parameter) darf nicht gestartet sein
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				System.out.println("Strg+C, pool.shutdown");
				pool.shutdown(); // keine Annahme von neuen Anforderungen
				try {
					// warte maximal 4 Sekunden auf Beendigung aller
					// Anforderungen
					pool.awaitTermination(4L, TimeUnit.SECONDS);
					if (!serverSocket.isClosed()) {
						System.out.println("ServerSocket close");
						serverSocket.close();
					}
				} catch (IOException e) {
				} catch (InterruptedException ei) {
				}
			}
		});
	}
}

// Thread bzw. Runnable zur Entgegennahme der Client-Anforderungen
class NetworkService implements Runnable { // oder extends Thread
	private final ServerSocket serverSocket;
	private final ExecutorService pool;

	public NetworkService(ExecutorService pool, ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
		this.pool = pool;
	}

	public void run() { // run the service
		try {
			// Endlos-Schleife: warte auf Client-Anforderungen
			// Abbruch durch Strg+C oder Client-Anforderung 'Exit',
			// dadurch wird der ServerSocket beendet, was hier zu einer
			// IOException
			// fuehrt und damit zum Ende der run-Methode mit vorheriger
			// Abarbeitung der
			// finally-Klausel.
			while (true) {
				/*
				 * Zunaechst wird eine Client-Anforderung
				 * entgegengenommen(accept-Methode). Der ExecutorService pool
				 * liefert einen Thread, dessen run-Methode durch die
				 * run-Methode der Handler-Instanz realisiert wird. Dem Handler
				 * werden als Parameter uebergeben: der ServerSocket und der
				 * Socket des anfordernden Clients.
				 */
				Socket cs = serverSocket.accept(); // warten auf
													// Client-Anforderung
				// starte den Handler-Thread zur Realisierung der
				// Client-Anforderung
				pool.execute(new Handler(cs));
			}
		} catch (IOException ex) {
			System.out.println("--- Interrupt NetworkService-run");
		} finally {
			System.out.println("--- Ende NetworkService(pool.shutdown)");
			pool.shutdown(); // keine Annahme von neuen Anforderungen
			try {
				// warte maximal 1 Sekunden auf Beendigung aller Anforderungen
				pool.awaitTermination(1L, TimeUnit.SECONDS);
				if (!serverSocket.isClosed()) {
					System.out
							.println("--- Ende NetworkService:ServerSocket close");
					serverSocket.close();
				}
			} catch (IOException e) {
			} catch (InterruptedException ei) {
			}
		}
	}
}

// Thread bzw. Runnable zur Realisierung der Client-Anforderungen
class Handler implements Runnable { // oder 'extends Thread'
	private final Socket client;

	Handler(Socket client) { // Server/Client-Socket
		this.client = client;
	}

	public void run() {
		try {

			System.out.println("running service, " + Thread.currentThread());
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(client.getInputStream()));

			Request request = new HTTPRequest();
			int port = 0;
			Version version = null;
			Protocol protocol = null;
			String host = null;
			String path = null;
			String getParams = null;

			int lineNumber = 1;
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				System.out.println(line);

				if (line.length() == 0) {

					StringBuilder buf = new StringBuilder();
					System.out.println("start reading");
					while (bufferedReader.ready()) {
						line = bufferedReader.readLine();
						buf.append(line);
					}

					InputStream is = new DataInputStream(
							new ByteArrayInputStream(buf.toString().getBytes(
									"UTF-8")));
					request.setBody(is);

					break;
				} else {

					if (lineNumber == 1) {
						// parse first Line
						String[] requestLine = line.split(" ");
						// GET
						request.setMethod(Method.valueOf(requestLine[0].trim()));
						// /index.html
						String tempPath = requestLine[1];

						int idx = tempPath.indexOf('?');
						if (idx != -1) {
							path = tempPath.substring(0, idx);
							getParams = URLDecoder.decode(
									tempPath.substring(idx + 1), "UTF-8");
							// tempPath.substring(idx + 1), "ASCII");
						} else {
							path = tempPath;
						}

						String[] splittetProtocol = requestLine[2].trim()
								.split("\\/");
						// HTTP
						protocol = Protocol
								.getByProtocolString(splittetProtocol[0]);
						// 1.1
						version = Version
								.getByVersionString(splittetProtocol[1]);

					} else {
						String[] headerArgs = line.split(": ", 2);
						// headers Map befuellen
						request.addToHeader(headerArgs[0], headerArgs[1]);

						if (headerArgs[0].equals("Content-Length")) {
							request.setContentLength(Long
									.parseLong(headerArgs[1]));
						}

						if (headerArgs[0].equals("Content-Type")) {
							request.setContentType(headerArgs[1]);
						}

						if (headerArgs[0].equals("Host")) {
							String[] splittedHost = headerArgs[1].split(":");
							host = splittedHost[0];
							port = Integer.parseInt(splittedHost[1]);
						}
					}
				}
				lineNumber++;
			}

			// System.out.println(request);
			// System.out.println(uri);
			Uri uri = new UriImpl(port, protocol, version, host);
			uri.setPath(path);
			uri.setGETParams(UriHelper.getParamValue(getParams));

			PluginManager manager = new PluginManagerImpl();
			Response response = manager.excecuteRequest(uri, request);

			writeResponse(client.getOutputStream(), uri, response);
		} catch (IOException e) {
			System.out.println("IOException, Handler-run");
		} catch (Exception e) {
			e.printStackTrace();
			Response r = ResponseBuilder.buildResponse(StatusCode.STATUS_500);
			Uri uriEmpty = new UriImpl(0, Protocol.HTTP, Version.V1_1, "NOHOST");
			try {
				writeResponse(client.getOutputStream(), uriEmpty, r);
			} catch (IOException ioe) {
			}

		} finally {
			// out.println(response); // Rueckgabe Ergebnis an den Client
			if (!client.isClosed()) {
				System.out.println("****** Handler:Client close");
				try {
					client.close();
				} catch (IOException e) {
				}
			}
		}
	} // Ende run

	private void writeResponse(OutputStream outStream, Uri uri,
			Response response) throws IOException {
		PrintWriter out = new PrintWriter(outStream, true);

		out.println(uri.getProtocol().name() + "/"
				+ uri.getVersion().getVersion() + " "
				+ response.getStatusCode().getCode() + " "
				+ response.getStatusCode().getText());

		out.println("Content-Type: " + response.getContentType());
		out.println("Content-Length: " + response.getContentLength());
		for (Entry<String, String> entry : response.getHeaders().entrySet()) {
			out.println(entry.getKey() + ": " + entry.getValue());
		}

		out.println();

		if (response.getBody() != null) {

			InputStream in = response.getBody();
			byte[] buffer = new byte[1024];
			int len;
			while ((len = in.read(buffer)) != -1) {
				outStream.write(buffer, 0, len);
			}
		}
	}
}
