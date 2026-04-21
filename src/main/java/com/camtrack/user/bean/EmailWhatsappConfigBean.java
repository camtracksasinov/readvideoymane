//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.bean;

import java.io.Serializable;
import java.util.List;

public class EmailWhatsappConfigBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<SmallWhattsappEmail> emailwhatsap;
	private List<Levelbean> levelbeam;
	private List<Integer> listelementsid;
	private Integer typelementsid;
	private Integer userid;

	public List<Levelbean> getLevelbeam() {
		return this.levelbeam;
	}

	public List<Integer> getListelementsid() {
		return this.listelementsid;
	}

	public Integer getTypelementsid() {
		return this.typelementsid;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public List<SmallWhattsappEmail> getEmailwhatsap() {
		return emailwhatsap;
	}

	public void setEmailwhatsap(List<SmallWhattsappEmail> emailwhatsap) {
		this.emailwhatsap = emailwhatsap;
	}

	public void setLevelbeam(final List<Levelbean> levelbeam) {
		this.levelbeam = levelbeam;
	}

	public void setListelementsid(final List<Integer> listelementsid) {
		this.listelementsid = listelementsid;
	}

	public void setTypelementsid(final Integer typelementsid) {
		this.typelementsid = typelementsid;
	}

	public void setUserid(final Integer userid) {
		this.userid = userid;
	}
}
