package com.camtrack.config.passwordmanagement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * <h2>BaseResponse</h2>
 *
 * @author aek
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ApiResponse {

	private Object data;
	private boolean error = true;
	private String message;

	public ApiResponse(Object data, String message) {
		this.data = data;
		this.message = message;
	}
}
