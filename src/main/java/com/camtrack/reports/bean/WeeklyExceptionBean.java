//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.reports.bean;

import com.camtrack.config.Utils;

public class WeeklyExceptionBean {
	private String affiliatename;
	private int alarmcount;
	private int alertcount;
	private String clientname;
	private int duration;
	private float nb;
	private int recoringcount;
	private int totaldistance;
	private String transportername;
	private String vehicledesc;
	private int weekly;

	public String getAffiliatename() {
		return Utils.StringEscape(this.affiliatename);
	}

	public int getAlarmcount() {
		return this.alarmcount;
	}

	public int getAlertcount() {
		return this.alertcount;
	}

	public String getClientname() {
		return Utils.StringEscape(this.clientname);
	}

	public int getDuration() {
		return this.duration;
	}

	public float getNb() {
		return this.nb;
	}

	public int getRecoringcount() {
		return this.recoringcount;
	}

	public int getTotaldistance() {
		return this.totaldistance;
	}

	public String getTransportername() {
		return Utils.StringEscape(this.transportername);
	}

	public String getVehicledesc() {
		return Utils.StringEscape(this.vehicledesc);
	}

	public int getWeekly() {
		return this.weekly;
	}

	public void setAffiliatename(final String affiliatename) {
		this.affiliatename = affiliatename;
	}

	public void setAlarmcount(final int alarmcount) {
		this.alarmcount = alarmcount;
	}

	public void setAlertcount(final int alertcount) {
		this.alertcount = alertcount;
	}

	public void setClientname(final String clientname) {
		this.clientname = clientname;
	}

	public void setDuration(final int duration) {
		this.duration = duration;
	}

	public void setNb(final float nb) {
		this.nb = nb;
	}

	public void setRecoringcount(final int recoringcount) {
		this.recoringcount = recoringcount;
	}

	public void setTotaldistance(final int totaldistance) {
		this.totaldistance = totaldistance;
	}

	public void setTransportername(final String transportername) {
		this.transportername = transportername;
	}

	public void setVehicledesc(final String vehicledesc) {
		this.vehicledesc = vehicledesc;
	}

	public void setWeekly(final int weekly) {
		this.weekly = weekly;
	}

	@Override
	public String toString() {
		return "WeeklyExceptionBean [clientname=" + this.clientname + ", affiliatename=" + this.affiliatename
				+ ", transportername=" + this.transportername + ", vehicledesc=" + this.vehicledesc + ", weekly="
				+ this.weekly + ", alarmcount=" + this.alarmcount + ", alertcount=" + this.alertcount
				+ ", recoringcount=" + this.recoringcount + ", duration=" + this.duration + ", totaldistance="
				+ this.totaldistance + ", nb=" + this.nb + ", getClientname()=" + this.getClientname()
				+ ", getAffiliatename()=" + this.getAffiliatename() + ", getTransportername()="
				+ this.getTransportername() + ", getVehicledesc()=" + this.getVehicledesc() + ", getWeekly()="
				+ this.getWeekly() + ", getAlarmcount()=" + this.getAlarmcount() + ", getAlertcount()="
				+ this.getAlertcount() + ", getRecoringcount()=" + this.getRecoringcount() + ", getDuration()="
				+ this.getDuration() + ", getTotaldistance()=" + this.getTotaldistance() + ", getNb()=" + this.getNb()
				+ ", getClass()=" + this.getClass() + ", hashCode()=" + this.hashCode() + ", toString()="
				+ super.toString() + "]";
	}
}
