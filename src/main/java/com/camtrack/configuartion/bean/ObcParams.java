//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.configuartion.bean;

import java.io.Serializable;

public class ObcParams implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer alarm;
	private Integer alert;
	private Integer obcparamid;
	private Integer record;
	private Boolean select;

	public Integer getAlarm() {
		return this.alarm;
	}

	public Integer getAlert() {
		return this.alert;
	}

	public Integer getObcparamid() {
		return this.obcparamid;
	}

	public Integer getRecord() {
		return this.record;
	}

	public Boolean getSelect() {
		return this.select;
	}

	public void setAlarm(final Integer alarm) {
		this.alarm = alarm;
	}

	public void setAlert(final Integer alert) {
		this.alert = alert;
	}

	public void setObcparamid(final Integer obcparamid) {
		this.obcparamid = obcparamid;
	}

	public void setRecord(final Integer record) {
		this.record = record;
	}

	public void setSelect(final Boolean select) {
		this.select = select;
	}
}
