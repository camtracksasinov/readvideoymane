package com.camtrack.mfauthentification.bean;

import java.io.Serializable;

import com.camtrack.config.Utils;

public class MfaTokenData implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Boolean con1;
	private Boolean mfa;
	private String mfaCode;
	private String msg;
	private String qrCode;

	public MfaTokenData(String qrCode, String mfaCode, String msg, Boolean mfa, Boolean con1) {
		super();
		this.qrCode = qrCode;
		this.mfaCode = mfaCode;
		this.msg = msg;
		this.mfa = mfa;
		this.con1 = con1;
	}

	public String getMfaCode() {
		return Utils.StringEscape(mfaCode);
	}

	public String getMsg() {
		return Utils.StringEscape(msg);
	}

	public String getQrCode() {
		return Utils.StringEscape(qrCode);
	}

	public Boolean isCon1() {
		return con1;
	}

	public Boolean isMfa() {
		return mfa;
	}

	public void setCon1(Boolean con1) {
		this.con1 = con1;
	}

	public void setMfa(Boolean mfa) {
		this.mfa = mfa;
	}

	public void setMfaCode(String mfaCode) {
		this.mfaCode = mfaCode;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	// getter and setter
}
