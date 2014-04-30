package at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.enums;

/**
 * 
 * Aufzahlung der Unterstuetzten Versionsnummern
 *
 */
public enum Version {
	V1_1("1.1");

	/**
	 * 
	 * @param version setzt die Versionsnummer
	 */
	private Version(String version) {
		this.version = version;
	}

	private String version;

	/**
	 * 
	 * @param stringVersion Version als String
	 * @return Gibt die Version die als String uebergeben wurde als Versions Objekt zurueck
	 */
	public static Version getByVersionString(String stringVersion) {
		for (Version v : Version.values()) {
			if (stringVersion.equals(v.version)) {
				return v;
			}
		}
		return null;
	}

	/**
	 * 
	 * @return gibt der Version als String zurueck
	 */
	public String getVersion() {
		return version;
	}

}
