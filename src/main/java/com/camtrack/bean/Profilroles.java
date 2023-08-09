//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.bean;

import java.io.Serializable;

public class Profilroles implements Serializable {
	private static final long serialVersionUID = 1L;
	private String contacts;
	private Integer languageid;
	private String names;
	private String newemail;
	private String newpassword;
	private String sexe;

	public String getContacts() {
		return contacts;
	}

	public Integer getLanguageid() {
		return languageid;
	}

	public String getNames() {
		return names;
	}

	public String getNewemail() {
		return newemail;
	}

	public String getNewpassword() {
		return newpassword;
	}

	public String getSexe() {
		return sexe;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public void setLanguageid(Integer languageid) {
		this.languageid = languageid;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public void setNewemail(String newemail) {
		this.newemail = newemail;
	}

	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

}
