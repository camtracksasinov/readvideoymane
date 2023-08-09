//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.io.Serializable;

public class Leveldetails implements Serializable {
	private static final long serialVersionUID = 1L;
	private InfosLevel alarm;
	private InfosLevel alert;
	private InfosLevel record;

	public Leveldetails() {
	}

	public Leveldetails(final InfosLevel record, final InfosLevel alarm, final InfosLevel alert) {
		this.record = record;
		this.alarm = alarm;
		this.alert = alert;
	}

	public InfosLevel getAlarm() {
		return this.alarm;
	}

	public InfosLevel getAlert() {
		return this.alert;
	}

	public InfosLevel getRecord() {
		return this.record;
	}

	public void setAlarm(final InfosLevel alarm) {
		this.alarm = alarm;
	}

	public void setAlert(final InfosLevel alert) {
		this.alert = alert;
	}

	public void setRecord(final InfosLevel record) {
		this.record = record;
	}
}
