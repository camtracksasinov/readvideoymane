//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.bean;

import java.io.Serializable;

import com.camtrack.config.Utils;

public class UserInfos implements Serializable {
	private static final long serialVersionUID = 1L;
	private Boolean actif;
	private String alanosqd;
	private String alofaqsdes;
	private Boolean con1;
	private String email;
	private Boolean hummad;
	private Boolean idfirst;
	private String is1;
	private String lang;
	private Integer language;
	private String languagename;
	private Boolean mfa;
	private String name;
	private String phonenumber;
	private String pictures;
	private Character sexe;
	private String typerolename;
	private Integer userrole;
	private Integer usersid;
	private Integer usertyperoleid;
	private String imguser;
	private String imgcus;
	private String cln;
	private String stream;

	public String getStream() {
		return stream;
	}

	public void setStream(String stream) {
		this.stream = stream;
	}

	public String getCln() {
		return cln;
	}

	public void setCln(String cln) {
		this.cln = cln;
	}

	public String getImguser() {
		return imguser;
	}

	public void setImguser(String imguser) {
		this.imguser = imguser;
	}

	public String getImgcus() {
		return imgcus;
	}

	public void setImgcus(String imgcus) {
		this.imgcus = imgcus;
	}

	public Boolean getActif() {
		return actif;
	}

	public String getAlanosqd() {
		return Utils.StringEscape(this.alanosqd);
	}

	public String getAlofaqsdes() {
		return alofaqsdes;
	}

	public Boolean getCon1() {
		return con1;
	}

	public String getEmail() {
		return Utils.StringEscape(this.email);
	}

	public Boolean getHummad() {
		return hummad;
	}

	public Boolean getIdfirst() {
		return idfirst;
	}

	public String getIs1() {
		return is1;
	}

	public String getLang() {
		return Utils.StringEscape(this.lang);
	}

	public Integer getLanguage() {
		return this.language;
	}

	public String getLanguagename() {
		return Utils.StringEscape(this.languagename);
	}

	public Boolean getMfa() {
		return mfa;
	}

	public String getName() {
		return Utils.StringEscape(this.name);
	}

	public String getPhonenumber() {
		return Utils.StringEscape(this.phonenumber);
	}

	public String getPictures() {
		return this.pictures;
	}

	public Character getSexe() {
		return this.sexe;
	}

	public String getTyperolename() {
		return Utils.StringEscape(this.typerolename);
	}

	public Integer getUserrole() {
		return this.userrole;
	}

	public Integer getUsersid() {
		return this.usersid;
	}

	public Integer getUsertyperoleid() {
		return this.usertyperoleid;
	}

	public Boolean isActif() {
		return this.actif;
	}

	public Boolean isIdfirst() {
		return idfirst;
	}

	public void setActif(final Boolean actif) {
		this.actif = actif;
	}

	public void setAlanosqd(final String alanosqd) {
		this.alanosqd = alanosqd;
	}

	public void setAlofaqsdes(String alofaqsdes) {
		this.alofaqsdes = alofaqsdes;
	}

	public void setCon1(Boolean con1) {
		this.con1 = con1;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setHummad(Boolean hummad) {
		this.hummad = hummad;
	}

	public void setIdfirst(Boolean idfirst) {
		this.idfirst = idfirst;
	}

	public void setIs1(String is1) {
		this.is1 = is1;
	}

	public void setLang(final String lang) {
		this.lang = lang;
	}

	public void setLanguage(final Integer language) {
		this.language = language;
	}

	public void setLanguagename(final String languagename) {
		this.languagename = languagename;
	}

	public void setMfa(Boolean mfa) {
		this.mfa = mfa;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setPhonenumber(final String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public void setPictures(final String pictures) {
		this.pictures = pictures;
	}

	public void setSexe(final Character sexe) {
		this.sexe = sexe;
	}

	public void setTyperolename(final String typerolename) {
		this.typerolename = typerolename;
	}

	public void setUserrole(final Integer userrole) {
		this.userrole = userrole;
	}

	public void setUsersid(final Integer usersid) {
		this.usersid = usersid;
	}

	public void setUsertyperoleid(final Integer usertyperoleid) {
		this.usertyperoleid = usertyperoleid;
	}
}
