package at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.enums;

/**
 * 
 * Aufaehlung aller Fehlercodes
 * 
 */

public enum StatusCode {
	STATUS_404(404, "Not Found"), STATUS_200(200, "OK"), STATUS_500(500,
			"Internal Server Error");

	/**
	 * 
	 * @param code
	 *            nimmt den Fehlercode als INT entgegen und waehlt den richtigen
	 *            emum aus.
	 * @param ok
	 *            status des aufrufes
	 */
	private StatusCode(int code, String text) {
		this.code = code;
		this.text = text;
	}

	private int code;
	private String text;

	/**
	 * 
	 * @return Gibt den Fehlercode zurueck
	 */
	public int getCode() {
		return code;
	}

	/**
	 * 
	 * @return Gibt den Text des Statuscodes zurueck
	 */
	public String getText() {
		return text;
	}

}
