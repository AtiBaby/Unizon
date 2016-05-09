package hu.unideb.inf.Unizon.util;

import java.io.IOException;
import java.util.Properties;

public class PropertyHelper {

	public static final String IMAGES_LOCATION;

	static {
		Properties props = new Properties();

		try {
			props.load(PropertyHelper.class.getResourceAsStream("/unizon.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		IMAGES_LOCATION = props.getProperty("unizon.images.location");
	}

}
