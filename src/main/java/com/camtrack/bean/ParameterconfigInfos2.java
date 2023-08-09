//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class ParameterconfigInfos2 implements Serializable {
	private static final long serialVersionUID = 1L;
	private Boolean activesalert;
	private BigDecimal alarmdelayinput;
	private BigDecimal alarmtheshold;
	private BigDecimal alarmthresholdperc;
	private BigDecimal alertdelayinput;
	private Integer alertlevel;
	private BigDecimal alertthreshold;
	private BigDecimal alertthresholdperc;
	private Integer clientaffiliateid;
	private Integer customerid;
	private Integer driverid;
	private Integer frommonth;
	private BigDecimal fromtime;
	private BigDecimal minimumdistance;
	private Long parameterconfigid;
	private Integer parametertypeid;
	private BigDecimal prevthreshold;
	private BigDecimal recordingdelayinput;
	private BigDecimal recordingthreshold;
	private BigDecimal recordingthresholdperc;
	private BigDecimal requiredresttime;
	private Integer rollingdays;
	private BigDecimal thresholdlimit;
	private Integer tomonth;
	private BigDecimal totime;
	private Integer transporterid;
	private Integer vehicleid;

	public Boolean getActivesalert() {
		return this.activesalert;
	}

	public BigDecimal getAlarmdelayinput() {
		return this.alarmdelayinput;
	}

	public BigDecimal getAlarmthreshold() {
		return this.alarmtheshold;
	}

	public BigDecimal getAlarmthresholdperc() {
		return this.alarmthresholdperc;
	}

	public BigDecimal getAlertdelayinput() {
		return this.alertdelayinput;
	}

	public Integer getAlertlevel() {
		return this.alertlevel;
	}

	public BigDecimal getAlertthreshold() {
		return this.alertthreshold;
	}

	public BigDecimal getAlertthresholdperc() {
		return this.alertthresholdperc;
	}

	public Integer getClientaffiliateid() {
		return this.clientaffiliateid;
	}

	public Integer getCustomerid() {
		return this.customerid;
	}

	public Integer getDriverid() {
		return this.driverid;
	}

	public Integer getFrommonth() {
		return this.frommonth;
	}

	public BigDecimal getFromtime() {
		return this.fromtime;
	}

	public BigDecimal getMinimumdistance() {
		return this.minimumdistance;
	}

	public Long getParameterconfigid() {
		return this.parameterconfigid;
	}

	public Integer getParametertypeid() {
		return this.parametertypeid;
	}

	public BigDecimal getPrevthreshold() {
		return this.prevthreshold;
	}

	public BigDecimal getRecordingdelayinput() {
		return this.recordingdelayinput;
	}

	public BigDecimal getRecordingthreshold() {
		return this.recordingthreshold;
	}

	public BigDecimal getRecordingthresholdperc() {
		return this.recordingthresholdperc;
	}

	public BigDecimal getRequiredresttime() {
		return this.requiredresttime;
	}

	public Integer getRollingdays() {
		return this.rollingdays;
	}

	public BigDecimal getThresholdlimit() {
		return this.thresholdlimit;
	}

	public Integer getTomonth() {
		return this.tomonth;
	}

	public BigDecimal getTotime() {
		return this.totime;
	}

	public Integer getTransporterid() {
		return this.transporterid;
	}

	public Integer getVehicleid() {
		return this.vehicleid;
	}

	public void setActivesalert(final Boolean activesalert) {
		this.activesalert = activesalert;
	}

	public void setAlarmdelayinput(final BigDecimal alarmdelayinput) {
		this.alarmdelayinput = alarmdelayinput;
	}

	public void setAlarmthreshold(final BigDecimal alarmthreshold) {
		this.alarmtheshold = alarmthreshold;
	}

	public void setAlarmthresholdperc(final BigDecimal alarmthresholdperc) {
		this.alarmthresholdperc = alarmthresholdperc;
	}

	public void setAlertdelayinput(final BigDecimal alertdelayinput) {
		this.alertdelayinput = alertdelayinput;
	}

	public void setAlertlevel(final Integer alertlevel) {
		this.alertlevel = alertlevel;
	}

	public void setAlertthreshold(final BigDecimal alertthreshold) {
		this.alertthreshold = alertthreshold;
	}

	public void setAlertthresholdperc(final BigDecimal alertthresholdperc) {
		this.alertthresholdperc = alertthresholdperc;
	}

	public void setClientaffiliateid(final Integer clientaffiliateid) {
		this.clientaffiliateid = clientaffiliateid;
	}

	public void setCustomerid(final Integer customerid) {
		this.customerid = customerid;
	}

	public void setDriverid(final Integer driverid) {
		this.driverid = driverid;
	}

	public void setFrommonth(final Integer frommonth) {
		this.frommonth = frommonth;
	}

	public void setFromtime(final BigDecimal fromtime) {
		this.fromtime = fromtime;
	}

	public void setMinimumdistance(final BigDecimal minimumdistance) {
		this.minimumdistance = minimumdistance;
	}

	public void setParameterconfigid(final Long parameterconfigid) {
		this.parameterconfigid = parameterconfigid;
	}

	public void setParametertypeid(final Integer parametertypeid) {
		this.parametertypeid = parametertypeid;
	}

	public void setPrevthreshold(final BigDecimal prevthreshold) {
		this.prevthreshold = prevthreshold;
	}

	public void setRecordingdelayinput(final BigDecimal recordingdelayinput) {
		this.recordingdelayinput = recordingdelayinput;
	}

	public void setRecordingthreshold(final BigDecimal recordingthreshold) {
		this.recordingthreshold = recordingthreshold;
	}

	public void setRecordingthresholdperc(final BigDecimal recordingthresholdperc) {
		this.recordingthresholdperc = recordingthresholdperc;
	}

	public void setRequiredresttime(final BigDecimal requiredresttime) {
		this.requiredresttime = requiredresttime;
	}

	public void setRollingdays(final Integer rollingdays) {
		this.rollingdays = rollingdays;
	}

	public void setThresholdlimit(final BigDecimal thresholdlimit) {
		this.thresholdlimit = thresholdlimit;
	}

	public void setTomonth(final Integer tomonth) {
		this.tomonth = tomonth;
	}

	public void setTotime(final BigDecimal totime) {
		this.totime = totime;
	}

	public void setTransporterid(final Integer transporterid) {
		this.transporterid = transporterid;
	}

	public void setVehicleid(final Integer vehicleid) {
		this.vehicleid = vehicleid;
	}
}
