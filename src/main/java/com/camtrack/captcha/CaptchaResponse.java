package com.camtrack.captcha;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//Lombok annotations
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CaptchaResponse implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("challenge_ts")
	Date challengeTs;
	@JsonProperty("error-codes")
	List<String> errorCodes;
	String hostname;
	boolean success;

	public Date getChallengeTs() {
		return challengeTs;
	}

	public List<String> getErrorCodes() {
		return errorCodes;
	}

	public String getHostname() {
		return hostname;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setChallengeTs(Date challengeTs) {
		this.challengeTs = challengeTs;
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