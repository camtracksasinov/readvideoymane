//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.bean;

import java.io.Serializable;
import java.util.List;

import com.camtrack.config.Utils;
import com.camtrack.config.passwordmanagement.ValidPassword;

public class Userallidroles implements Serializable {
	private static final long serialVersionUID = 1L;
	private Boolean activateuser;
	private List<CreateEntityRole> allentitiesright;
	private String contacts;
	private Boolean enabled;
	private Boolean firstconn;
	private Boolean isadmin;
	private Integer languageid;
	private Boolean mfa;
	private String names;
	private String newemail;
	@ValidPassword
	private String newpassword;
	private Integer roleid;
	private String sexe;
	private Boolean unblock;
	private Integer userid;
	private String username;

	public Boolean getActivateuser() {
		return this.activateuser;
	}

	public List<CreateEntityRole> getAllentitiesright() {
		return this.allentitiesright;
	}

	public String getContacts() {
		return Utils.StringEscape(this.contacts);
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public Boolean getFirstconn() {
		return firstconn;
	}

	public Boolean getIsadmin() {
		return isadmin;
	}

	public Integer getLanguageid() {
		return this.languageid;
	}

	public Boolean getMfa() {
		return mfa;
	}

	public String getNames() {
		return Utils.StringEscape(this.names);
	}

	public String getNewemail() {
		return Utils.StringEscape(this.newemail);
	}

	public String getNewpassword() {
		return Utils.StringEscape(this.newpassword);
	}

	public Integer getRoleid() {
		return this.roleid;
	}

	public String getSexe() {
		return Utils.StringEscape(this.sexe);
	}

	public Boolean getUnblock() {
		return unblock;
	}

	public Integer getUserid() {
		return this.userid;
	}

	// Utils.StringEscape(datedebut), Utils.StringEscape(datefin)
	public String getUsername() {
		return Utils.StringEscape(this.username);
	}

	public void setActivateuser(final Boolean activateuser) {
		this.activateuser = activateuser;
	}

	public void setAllentitiesright(final List<CreateEntityRole> allentitiesright) {
		this.allentitiesright = allentitiesright;
	}

	public void setContacts(final String contacts) {
		this.contacts = contacts;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public void setFirstconn(Boolean firstconn) {
		this.firstconn = firstconn;
	}

	public void setIsadmin(Boolean isadmin) {
		this.isadmin = isadmin;
	}

	public void setLanguageid(final Integer languageid) {
		this.languageid = languageid;
	}

	public void setMfa(Boolean mfa) {
		this.mfa = mfa;
	}

	public void setNames(final String names) {
		this.names = names;
	}

	public void setNewemail(final String newemail) {
		this.newemail = newemail;
	}

	public void setNewpassword(final String newpassword) {
		this.newpassword = newpassword;
	}

	public void setRoleid(final Integer roleid) {
		this.roleid = roleid;
	}

	public void setSexe(final String sexe) {
		this.sexe = sexe;
	}

	public void setUnblock(Boolean unblock) {
		this.unblock = unblock;
	}

	public void setUserid(final Integer userid) {
		this.userid = userid;
	}

	public void setUsername(final String username) {
		this.username = username;
	}
}
