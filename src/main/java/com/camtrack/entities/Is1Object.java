package com.camtrack.entities;

import java.io.Serializable;

import org.springframework.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Is1Object implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@NonNull
	private String is1;
	// @ValidPassword(message = "rule.password")
	private String is2;

	public String getIs1() {
		return is1;
	}

	public String getIs2() {
		return is2;
	}

	public void setIs1(String is1) {
		this.is1 = is1;
	}

	public void setIs2(String is2) {
		this.is2 = is2;
	}

}
