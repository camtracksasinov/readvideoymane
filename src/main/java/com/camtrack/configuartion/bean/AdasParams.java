//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.configuartion.bean;

import java.io.Serializable;

public class AdasParams implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer adasparamid;
	private Integer alarm;
	private Integer alert;
	private Integer record;
	private Boolean select;

	public Integer getAdasparamid() {
		return this.adasparamid;
	}

	public Integer getAlarm() {
		return this.alarm;
	}

	public Integer getAlert() {
		return this.alert;
	}

	public Integer getRecord() {
		return this.record;
	}

	public Boolean getSelect() {
		return this.select;
	}

	public void setAdasparamid(final Integer adasparamid) {
		this.adasparamid = adasparamid;
	}

	public void setAlarm(final Integer alarm) {
		this.alarm = alarm;
	}

	public void setAlert(final Integer alert) {
		this.alert = alert;
	}

	public void setRecord(final Integer record) {
		this.record = record;
	}

	public void setSelect(final Boolean select) {
		this.select = select;
	}
}
