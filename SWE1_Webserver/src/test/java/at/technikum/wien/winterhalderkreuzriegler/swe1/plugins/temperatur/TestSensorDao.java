package at.technikum.wien.winterhalderkreuzriegler.swe1.plugins.temperatur;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestSensorDao extends Assert {

	private static int DAY_IN_MS = 86400000;

	private static SensorDao dao;

	@BeforeClass
	public static void setUp() {
		dao = new SensorDao();
	}

	@Test
	public void testLoad() {
		List<SensorData> list = dao.loadData(2014, 1, 8);
		assertNotNull(list);
		assertFalse(list.isEmpty());
	}

	@Test
	public void testInsert() {
		double value = 50d;
		SensorData data = new SensorData();
		data.setTime(System.currentTimeMillis() + (DAY_IN_MS * 100));
		data.setValue(value);

		dao.createSensorData(data);

		List<SensorData> list = dao.loadData(TemperaturHelper.getYear(),
				TemperaturHelper.getMonth(), TemperaturHelper.getDay() + 100);

		assertNotNull(list);
		for (SensorData d : list) {
			assertEquals(Double.valueOf(value), Double.valueOf(d.getValue()));
		}

	}

}
