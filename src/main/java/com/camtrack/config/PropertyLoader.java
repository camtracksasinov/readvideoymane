//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.config;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Properties;

public class PropertyLoader {
	public static String getLabel(final String language, final String property) {
		String message = property;
		try {
			final Properties prop = new Properties();
			final InputStream stream = PropertyLoader.class.getClassLoader()
					.getResourceAsStream("ui_labels_" + language + ".properties");
			InputStreamReader isr = new InputStreamReader(stream, "UTF-8");
			if (language.equals("Spanish") || language.equals("French")) {
				isr = new InputStreamReader(stream, "ISO-8859-1");
			}
			prop.load(isr);
			if (prop.getProperty(property) != null) {
				message = prop.getProperty(property);
			} else {
				message = property;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return message;
	}

	public static String getMessage(final String language, final String property) {
		String message = property;
		try {
			final Properties prop = new Properties();
			final InputStream stream = PropertyLoader.class.getClassLoader()
					.getResourceAsStream("message_" + language + ".properties");
			InputStreamReader isr = new InputStreamReader(stream, "UTF-8");
			if (language.equals("Spanish") || language.equals("French")) {
				isr = new InputStreamReader(stream, "ISO-8859-1");
			}
			prop.load(isr);
			message = prop.getProperty(property);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return message;
	}

	public static Hashtable<Object, Object> loadLabelsList(final String language) {
		final Properties prop = new Properties();
		try {
			final InputStream stream = PropertyLoader.class.getClassLoader()
					.getResourceAsStream("ui_labels_" + language + ".properties");
			InputStreamReader isr = new InputStreamReader(stream, "UTF-8");
			if (language.equals("Spanish") || language.equals("French")) {
				isr = new InputStreamReader(stream, "ISO-8859-1");
			}
			prop.load(isr);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return prop;
	}

	public static void main(final String[] args) {
		new PropertyLoader();
		System.out.println(loadLabelsList("en"));
	}
}
