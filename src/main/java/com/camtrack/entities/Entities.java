//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.io.Serializable;
import java.util.List;

public class Entities implements Serializable {
	private static final long serialVersionUID = 1L;
	private Boolean alarm;
	private Boolean alert;
	private Integer ids;
	private List<Integer> idtypealert;
	private Boolean record;

	public Boolean getAlarm() {
		return this.alarm;
	}

	public Boolean getAlert() {
		return this.alert;
	}

	public Integer getIds() {
		return this.ids;
	}

	public List<Integer> getIdtypealert() {
		return this.idtypealert;
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

	public void setIds(final Integer ids) {
		this.ids = ids;
	}

	public void setIdtypealert(final List<Integer> idtypealert) {
		this.idtypealert = idtypealert;
	}

	public void setRecord(final Boolean record) {
		this.record = record;
	}
}
