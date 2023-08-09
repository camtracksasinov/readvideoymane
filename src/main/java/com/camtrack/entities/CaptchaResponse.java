package com.camtrack.entities;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

// Lombok annotations
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CaptchaResponse {

	LocalDateTime challenge_ts;
	@JsonProperty("error-codes")
	List<String> errorCodes;
	String hostname;
	boolean success;

	public List<String> getErrorCodes() {
		return errorCodes;
	}

	public String getHostname() {
		return hostname;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setErrorCodes(List<String> errorCodes) {
		this.errorCodes = errorCodes;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
