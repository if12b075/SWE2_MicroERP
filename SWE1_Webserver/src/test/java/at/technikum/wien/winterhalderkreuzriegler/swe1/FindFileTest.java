package at.technikum.wien.winterhalderkreuzriegler.swe1;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

public class FindFileTest {

	@Test
	public void testFindFile() {

		File f = new File("src/test/resources/plugins/");
		Assert.assertTrue("Kein Directory", f.isDirectory());
		
		f = new File("www");
		Assert.assertTrue("Kein Directory", f.isDirectory());
	}

}
