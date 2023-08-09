//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.bean;

import java.io.Serializable;

public class CreateEntityRole implements Serializable {
	private static final long serialVersionUID = 1L;
	private Boolean alarmlevel;
	private Boolean alertlevel;
	private Integer entityid;
	private Integer[] listidtypeexception;
	private Boolean recordlevel;

	public Boolean getAlarmlevel() {
		return this.alarmlevel;
	}

	public Boolean getAlertlevel() {
		return this.alertlevel;
	}

	public Integer getEntityid() {
		return this.entityid;
	}

	public Integer[] getListidtypeexception() {
		return this.listidtypeexception;
	}

	public Boolean getRecordlevel() {
		return this.recordlevel;
	}

	public void setAlarmlevel(final Boolean alarmlevel) {
		this.alarmlevel = alarmlevel;
	}

	public void setAlertlevel(final Boolean alertlevel) {
		this.alertlevel = alertlevel;
	}

	public void setEntityid(final Integer entityid) {
		this.entityid = entityid;
	}

	public void setListidtypeexception(final Integer[] listidtypeexception) {
		this.listidtypeexception = listidtypeexception;
	}

	public void setRecordlevel(final Boolean recordlevel) {
		this.recordlevel = recordlevel;
	}
}
