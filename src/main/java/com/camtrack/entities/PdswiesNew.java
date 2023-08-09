package com.camtrack.entities;

import java.io.Serializable;

import org.springframework.lang.NonNull;

import com.camtrack.config.passwordmanagement.ValidPassword;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//LostPassword

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PdswiesNew implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String is1;
	@NonNull
	@ValidPassword
	private String newoldis;

	public String getIs1() {
		return is1;
	}

	public String getNewoldis() {
		return newoldis;
	}

	public void setIs1(String is1) {
		this.is1 = is1;
	}

	public void setNewoldis(String newoldis) {
		this.newoldis = newoldis;
	}

}
