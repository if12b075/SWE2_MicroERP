package at.technikum.wien.winterhalderkreuzriegler.swe1.plugins.navi;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

/**
 * 
 * Der NaviHepler, diehnt als Hilfsklasse fuer das Navigationsplugin. Es wird ein SAX Parser erstellt und mittels des 
 * OSMHandlers wird die Map mit den Strassennamen aufgebaut.
 *
 */
public class NaviHelper {

	public static Map<String, List<String>> parseOSM(File file) {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser saxParser = factory.newSAXParser();

			Map<String, List<String>> result = new HashMap<String, List<String>>();

			saxParser.parse(file, new OSMHandler(result));

			return result;

		} catch (ParserConfigurationException e) {
			throw new IllegalStateException(e);
		} catch (SAXException e) {
			throw new IllegalStateException(e);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}

	}

}
