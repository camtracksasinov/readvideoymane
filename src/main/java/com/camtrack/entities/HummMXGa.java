package com.camtrack.entities;

import java.io.Serializable;

import org.springframework.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HummMXGa implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@NonNull
	private String is1;
	@NonNull
	private String ishdfts;

	public String getIs1() {
		return is1;
	}

	public String getIshdfts() {
		return ishdfts;
	}

	public void setIs1(String is1) {
		this.is1 = is1;
	}

	public void setIshdfts(String ishdfts) {
		this.ishdfts = ishdfts;
	}
}
