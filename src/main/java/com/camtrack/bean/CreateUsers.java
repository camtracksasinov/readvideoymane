// Decompiled by Procyon v0.5.30
//

package com.camtrack.bean;

import java.io.Serializable;

public class CreateUsers implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String contacts;
	private String emailid;
	private String languageid;
	private Boolean mfa;
	private String name;
	private Integer userid;
	private String username;

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public String getLanguageid() {
		return languageid;
	}

	public void setLanguageid(String languageid) {
		this.languageid = languageid;
	}

	public Boolean getMfa() {
		return mfa;
	}

	public void setMfa(Boolean mfa) {
		this.mfa = mfa;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
