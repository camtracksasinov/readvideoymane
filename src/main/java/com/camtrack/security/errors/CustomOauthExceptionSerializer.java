//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.security.errors;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class CustomOauthExceptionSerializer extends StdSerializer<CustomOauthException> {
	public CustomOauthExceptionSerializer() {
		super(CustomOauthException.class);
	}

	@Override
	public void serialize(final CustomOauthException value, final JsonGenerator jsonGenerator,
			final SerializerProvider serializerProvider) throws IOException {
		jsonGenerator.writeStartObject();
		jsonGenerator.writeNumberField("code", value.getHttpErrorCode());
		jsonGenerator.writeBooleanField("status", false);
		jsonGenerator.writeObjectField("data", (Object) null);
		jsonGenerator.writeObjectField("errors", Arrays.asList(value.getOAuth2ErrorCode(), value.getMessage()));
		if (value.getAdditionalInformation() != null) {
			for (final Map.Entry<String, String> entry : value.getAdditionalInformation().entrySet()) {
				final String key = entry.getKey();
				final String add = entry.getValue();
				jsonGenerator.writeStringField(key, add);
			}
		}
		jsonGenerator.writeEndObject();
	}
}
