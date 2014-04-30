package at.technikum.wien.winterhalderkreuzriegler.swe1.plugins.navi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 
 * Der OSMHandler wird vom SAX Parser benutzt. Es wird definiert auf welche start und endtags reagiert wird.
 * Anschliessend wird eine Map mit allen Strassennamen befuellt.
 *
 */
public class OSMHandler extends DefaultHandler {

	private Map<String, List<String>> map;
	private String street = null;
	private String city = null;
	private boolean parse = false;

	public OSMHandler(Map<String, List<String>> map) {
		this.map = map;
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException {

		if ("way".equals(qName) || "node".equals(qName)) {
			parse = true;
			street = null;
			city = null;
		} else if ("tag".equals(qName) && parse) {
			String key = atts.getValue("k");
			if (key != null) {
				if (key.equals("addr:street")) {
					street = atts.getValue("v");
				} else if (key.equals("addr:city")) {
					city = atts.getValue("v");
				}
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		if (parse && ("way".equals(qName) || "node".equals(qName))
				&& street != null && city != null) {
			parse = false;
			List<String> cities = new ArrayList<String>();
			if (map.containsKey(street)) {
				cities = map.get(street);
			}

			if (!cities.contains(city)) {
				cities.add(city);
				map.put(street, cities);
			}

			street = null;
			city = null;
		}

	}

}
