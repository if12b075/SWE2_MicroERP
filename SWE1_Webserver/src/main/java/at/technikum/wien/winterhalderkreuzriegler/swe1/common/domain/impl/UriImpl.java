package at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.impl;

import java.util.Map;

import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.enums.Protocol;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.enums.Version;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.interfaces.Uri;

/**
 * 
 * UriImple stellt die Implementierung einer URI dar. Gespeichert werden der Port, das Protokoll, die Version, der Host,
 * der Pfad und die uebergebenen Parameter. Diese Klasse ist eine reine Datenhalter Klasse.
 *
 */
public class UriImpl implements Uri {

	private int port;
	private Protocol protocol;
	private Version version;
	private String host;
	private String path;
	private Map<String, String> getParams;

	public UriImpl(int port, Protocol protocol, Version version, String host) {
		this.port = port;
		this.protocol = protocol;
		this.version = version;
		this.host = host;
	}

	@Override
	public int getPort() {
		return port;
	}

	@Override
	public void setPort(int port) {
		this.port = port;
	}

	@Override
	public Protocol getProtocol() {
		return protocol;
	}

	@Override
	public void setProtocol(Protocol protocol) {
		this.protocol = protocol;
	}

	@Override
	public Version getVersion() {
		return version;
	}

	@Override
	public void setVersion(Version version) {
		this.version = version;
	}

	@Override
	public String getHost() {
		return host;
	}

	@Override
	public void setHost(String host) {
		this.host = host;
	}

	@Override
	public String getPath() {
		return path;
	}

	@Override
	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public Map<String, String> getGETParams() {
		return getParams;
	}

	@Override
	public void setGETParams(Map<String, String> getParams) {
		this.getParams = getParams;
	}

	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();
		out.append("URI: \n");
		out.append("port: " + port);
		out.append("\n");
		out.append("protocol: " + protocol);
		out.append("\n");
		out.append("version: " + version);
		out.append("\n");
		out.append("host: " + host);
		out.append("\n");
		out.append("path: " + path);
		out.append("\n");

		return out.toString();
	}

}