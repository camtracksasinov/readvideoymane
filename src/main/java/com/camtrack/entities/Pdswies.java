package com.camtrack.entities;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.springframework.lang.NonNull;

import com.camtrack.config.passwordmanagement.ValidPassword;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//LostPassword

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Pdswies implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@NonNull
	@NotBlank(message = "Username is mandatory")
	private String is1;
	@ValidPassword(message = "rule.password")
	@NonNull
	@NotBlank(message = "Password is mandatory")
	private String newoldis;
	@NonNull
	@NotBlank(message = "Password is mandatory")
	private String oldis2;

	public String getIs1() {
		return is1;
	}

	public String getNewoldis() {
		return newoldis;
	}

	public String getOldis2() {
		return oldis2;
	}

	public void setIs1(String is1) {
		this.is1 = is1;
	}

	public void setNewoldis(String newoldis) {
		this.newoldis = newoldis;
	}

	public void setOldis2(String oldis2) {
		this.oldis2 = oldis2;
	}

}
