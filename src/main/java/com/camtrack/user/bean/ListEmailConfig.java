//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.bean;

import java.io.Serializable;
import java.util.List;

import com.camtrack.config.Utils;

public class ListEmailConfig implements Serializable {
	private static final long serialVersionUID = 1L;
	List<SmallWhattsappEmail> whattsapemail;
	List<SmallEmailConfigBean> listconfig;
	String nameuser;
	Integer userid;

	public List<SmallWhattsappEmail> getWhattsapemail() {
		return whattsapemail;
	}

	public void setWhattsapemail(List<SmallWhattsappEmail> whattsapemail) {
		this.whattsapemail = whattsapemail;
	}

	public List<SmallEmailConfigBean> getListconfig() {
		return this.listconfig;
	}

	public String getNameuser() {
		return Utils.StringEscape(this.nameuser);
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setListconfig(final List<SmallEmailConfigBean> listconfig) {
		this.listconfig = listconfig;
	}

	public void setNameuser(final String nameuser) {
		this.nameuser = nameuser;
	}

	public void setUserid(final Integer userid) {
		this.userid = userid;
	}
}
