//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.io.Serializable;

public class Worktime implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long drivetime;
	private Long idleTime;
	private Long pausetime;
	private Long totaldistance;
	private Long worktime;

	public Worktime(final Long worktime, final Long idleTime, final Long pausetime, final Long drivetime,
			final Long totaldistance) {
		this.worktime = worktime;
		this.idleTime = idleTime;
		this.pausetime = pausetime;
		if (drivetime.intValue() > worktime.intValue()) {
			this.drivetime = worktime;
		} else {
			this.drivetime = drivetime;
		}
		this.totaldistance = totaldistance;
	}

	public Long getDrivetime() {
		return this.drivetime;
	}

	public Long getIdleTime() {
		return this.idleTime;
	}

	public Long getPausetime() {
		return this.pausetime;
	}

	public Long getTotaldistance() {
		return this.totaldistance;
	}

	public Long getWorktime() {
		return this.worktime;
	}

	public void setDrivetime(final Long drivetime) {
		this.drivetime = drivetime;
	}

	public void setIdleTime(final Long idleTime) {
		this.idleTime = idleTime;
	}

	public void setPausetime(final Long pausetime) {
		this.pausetime = pausetime;
	}

	public void setTotaldistance(final Long totaldistance) {
		this.totaldistance = totaldistance;
	}

	public void setWorktime(final Long worktime) {
		this.worktime = worktime;
	}
}
