//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
	private static final String BUNDLE_NAME;
	private static final ResourceBundle RESOURCE_BUNDLE;

	static {
		BUNDLE_NAME = Messages.class.getName() + ".messages";
		RESOURCE_BUNDLE = ResourceBundle.getBundle(Messages.BUNDLE_NAME);
	}

	public static String getString(final String key) {
		try {
			return Messages.RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
