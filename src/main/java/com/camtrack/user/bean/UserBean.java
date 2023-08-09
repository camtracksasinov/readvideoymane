//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.bean;

import java.util.List;
import java.util.Objects;

import com.camtrack.config.Utils;
import com.camtrack.entities.Entities;

public class UserBean {
	private String contacts;
	private String email;
	private Boolean enabled;
	private List<Entities> entitiesnames;
	private String entitiestype;
	private Boolean firstconn;
	private Boolean mfa;
	private String name;
	private Integer roleid;
	private String rolename;
	private Character sexe;
	private Integer typeroleid;
	private Boolean unblock;
	private String urlimages;
	private Integer userid;
	// private String userName;

	public String getContacts() {
		return Utils.StringEscape(this.contacts);
	}

	public String getEmail() {
		return Utils.StringEscape(this.email);
	}

	public Boolean getEnabled() {
		if (Objects.isNull(enabled)) {
			return false;
		}
		return this.enabled;
	}

	public List<Entities> getEntitiesnames() {
		return this.entitiesnames;
	}

	public String getEntitiestype() {
		return this.entitiestype;
	}

	public Boolean getFirstconn() {
		if (Objects.isNull(firstconn)) {
			return false;
		}
		return firstconn;
	}

	public Boolean getMfa() {
		if (Objects.isNull(mfa)) {
			return false;
		}
		return mfa;
	}

	public String getName() {
		return Utils.StringEscape(this.name);
	}

	public Integer getRoleid() {
		return this.roleid;
	}

	public String getRolename() {
		return Utils.StringEscape(this.rolename);
	}

	public Character getSexe() {
		return sexe;
	}

	public Integer getTyperoleid() {
		return this.typeroleid;
	}

	public Boolean getUnblock() {
		if (Objects.isNull(unblock)) {
			return true;
		}
		return unblock;
	}

	public String getUrlimages() {
		return Utils.StringEscape(this.urlimages);
	}

	public Integer getUserid() {
		return this.userid;
	}

	/**
	 * public String getUserName() { return Utils.StringEscape(this.userName); }
	 */

	public void setContacts(final String contacts) {
		this.contacts = contacts;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setEnabled(final Boolean enabled) {
		this.enabled = enabled;
	}

	public void setEntitiesnames(final List<Entities> entitiesnames) {
		this.entitiesnames = entitiesnames;
	}

	public void setEntitiestype(final String entitiestype) {
		this.entitiestype = entitiestype;
	}

	public void setFirstconn(Boolean firstconn) {
		this.firstconn = firstconn;
	}

	public void setMfa(Boolean mfa) {
		this.mfa = mfa;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setRoleid(final Integer roleid) {
		this.roleid = roleid;
	}

	public void setRolename(final String rolename) {
		this.rolename = rolename;
	}

	public void setSexe(Character sexe) {
		this.sexe = sexe;
	}

	public void setTyperoleid(final Integer typeroleid) {
		this.typeroleid = typeroleid;
	}

	public void setUnblock(Boolean unblock) {
		this.unblock = unblock;
	}

	public void setUrlimages(final String urlimages) {
		this.urlimages = urlimages;
	}

	public void setUserid(final Integer userid) {
		this.userid = userid;
	}
	/**
	 * public void setUserName(final String userName) { this.userName = userName; }
	 */
}
