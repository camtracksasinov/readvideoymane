//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.reports.bean;

public class WeekDaysBean {
	String endDate;
	String startDate;

	public WeekDaysBean(final String startDate, final String endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getEndDate() {
		return this.endDate;
	}

	public String getStartDate() {
		return this.startDate;
	}

	public void setEndDate(final String endDate) {
		this.endDate = endDate;
	}

	public void setStartDate(final String startDate) {
		this.startDate = startDate;
	}
}
