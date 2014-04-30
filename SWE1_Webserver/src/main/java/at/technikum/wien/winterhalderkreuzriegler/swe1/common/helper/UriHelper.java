package at.technikum.wien.winterhalderkreuzriegler.swe1.common.helper;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Stellt saemtliche Helfermethoden fuer die UriImpl Klasse zur verfuegung.
 *
 */
public class UriHelper {

	/**
	 * Extrahiert aus dem Path den zweiten URI-Teil
	 * 
	 * <pre>
	 * z.B.: /static/index.html -> index.html
	 * </pre>
	 * 
	 * @param path
	 *            zu konvertierender Path
	 * @return extrahierter Zweiter URL-Teil
	 */
	public static String convertPath(String path) {
		path = path.substring(1);
		return path.indexOf('/') == -1 ? "" : path
				.substring(path.indexOf('/') + 1);
	}

	/**
	 * 
	 * @param path der Pfad als String z.B.: /static/index.html
	 * @return Es wird ein String[] zuruegegeben das alle teile des Pfades enthaelt die mit einem "/" geteilt wurden.
	 */
	public static String[] splitPath(String path) {
		return path.replace(" ", "").split("/");
	}

	/**
	 * 
	 * @param params alle Parameter ohne dem "?" z.B.: wert1=value1&wert2=value2....
	 * @return gibt eine Map zurueck dessen Key der Parametername und Value der Werte ist. 
	 * Wurde ein Leerstring uebergeben, wird eine leere Map retuniert.
	 */
	public static Map<String, String> getParamValue(String params) {
		if (params == null || params.length() == 0) {
			return new HashMap<String, String>();
		}
		String[] splittedParams = params.split("&");
		Map<String, String> result = new HashMap<String, String>();
		for (String par : splittedParams) {
			String[] splPar = par.split("=");
			result.put(splPar[0], splPar[1]);
		}

		return result;
	}

}
