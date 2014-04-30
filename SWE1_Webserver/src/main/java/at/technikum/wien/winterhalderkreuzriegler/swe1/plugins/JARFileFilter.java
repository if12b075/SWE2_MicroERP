package at.technikum.wien.winterhalderkreuzriegler.swe1.plugins;

import java.io.File;
import java.io.FileFilter;

public class JARFileFilter implements FileFilter {

	@Override
	public boolean accept(File f) {
		return f.getName().toLowerCase().endsWith(".jar");
	}

}
