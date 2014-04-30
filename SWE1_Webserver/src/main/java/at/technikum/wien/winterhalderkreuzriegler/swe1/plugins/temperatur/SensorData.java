package at.technikum.wien.winterhalderkreuzriegler.swe1.plugins.temperatur;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "data")
public class SensorData {

	private double value;

	private long timestamp;

	private long id;

	public double getValue() {
		return value;
	}

	@XmlElement
	public void setValue(double value) {
		this.value = value;
	}

	@XmlElement
	public long getTime() {
		return timestamp;
	}

	public void setTime(long timestamp) {
		this.timestamp = timestamp;
	}

	public long getId() {
		return id;
	}

	@XmlAttribute
	public void setId(long id) {
		this.id = id;
	}

}
