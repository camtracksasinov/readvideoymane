//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.bean;

import java.io.Serializable;

public class Levelbean implements Serializable {
	private static final long serialVersionUID = 1L;
	private Boolean alarm;
	private Boolean alert;
	private Integer idtypelement;
	private Boolean record;

	public Boolean getAlarm() {
		return this.alarm;
	}

	public Boolean getAlert() {
		return this.alert;
	}

	public Integer getIdtypelement() {
		return this.idtypelement;
	}

	public Boolean getRecord() {
		return this.record;
	}

	public void setAlarm(final Boolean alarm) {
		this.alarm = alarm;
	}

	public void setAlert(final Boolean alert) {
		this.alert = alert;
	}

	public void setIdtypelement(final Integer idtypelement) {
		this.idtypelement = idtypelement;
	}

	public void setRecord(final Boolean record) {
		this.record = record;
	}
}
