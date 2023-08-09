//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.bean;

import java.io.Serializable;
import java.util.Objects;

public class ParameterconfigBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String alarmdelayinput;
	private String alarmthreshold;
	private String alarmthresholdperc;
	private String alertdelayinput;
	private String alertthreshold;
	private String alertthresholdperc;
	private Integer clientaffiliateid;
	private Integer customerid;
	private Integer defaultlevelId;
	private Integer frommonth;
	private String fromtime;
	private String minimumdistance;
	private Long paramerterconfigid;
	private Integer parametertypeid;
	private String recordingdelayinput;
	private String recordingthreshold;
	private String recordingthresholdperc;
	private String requiredresttime;
	private Integer rollingdays;
	private String thresholdlimit;
	private Integer tomonth;
	private String totime;

	public String getAlarmdelayinput() {
		return this.alarmdelayinput;
	}

	public String getAlarmthreshold() {
		return this.alarmthreshold;
	}

	public String getAlarmthresholdperc() {
		return this.alarmthresholdperc;
	}

	public String getAlertdelayinput() {
		return this.alertdelayinput;
	}

	public String getAlertthreshold() {
		return this.alertthreshold;
	}

	public String getAlertthresholdperc() {
		return this.alertthresholdperc;
	}

	public Integer getClientaffiliateid() {
		return this.clientaffiliateid;
	}

	public Integer getCustomerid() {
		return this.customerid;
	}

	public Integer getDefaultlevelId() {
		return this.defaultlevelId;
	}

	public Integer getFrommonth() {
		return this.frommonth;
	}

	public String getFromtime() {
		return this.fromtime;
	}

	public String getMinimumdistance() {
		return this.minimumdistance;
	}

	public Long getParamerterconfigid() {
		return this.paramerterconfigid;
	}

	public Integer getParametertypeid() {
		return this.parametertypeid;
	}

	public String getRecordingdelayinput() {
		return this.recordingdelayinput;
	}

	public String getRecordingthreshold() {
		return this.recordingthreshold;
	}

	public String getRecordingthresholdperc() {
		return this.recordingthresholdperc;
	}

	public String getRequiredresttime() {
		return this.requiredresttime;
	}

	public Integer getRollingdays() {
		return this.rollingdays;
	}

	public String getThresholdlimit() {
		return this.thresholdlimit;
	}

	public Integer getTomonth() {
		if (Objects.isNull(this.tomonth)) {
			return 0;
		}
		return this.tomonth;
	}

	public String getTotime() {
		return this.totime;
	}

	public void setAlarmdelayinput(final String alarmdelayinput) {
		this.alarmdelayinput = alarmdelayinput;
	}

	public void setAlarmthreshold(final String alarmthreshold) {
		this.alarmthreshold = alarmthreshold;
	}

	public void setAlarmthresholdperc(final String alarmthresholdperc) {
		this.alarmthresholdperc = alarmthresholdperc;
	}

	public void setAlertdelayinput(final String alertdelayinput) {
		this.alertdelayinput = alertdelayinput;
	}

	public void setAlertthreshold(final String alertthreshold) {
		this.alertthreshold = alertthreshold;
	}

	public void setAlertthresholdperc(final String alertthresholdperc) {
		this.alertthresholdperc = alertthresholdperc;
	}

	public void setClientaffiliateid(final Integer clientaffiliateid) {
		this.clientaffiliateid = clientaffiliateid;
	}

	public void setCustomerid(final Integer customerid) {
		this.customerid = customerid;
	}

	public void setDefaultlevelId(final Integer defaultlevelId) {
		this.defaultlevelId = defaultlevelId;
	}

	public void setFrommonth(final Integer frommonth) {
		this.frommonth = frommonth;
	}

	public void setFromtime(final String fromtime) {
		this.fromtime = fromtime;
	}

	public void setMinimumdistance(final String minimumdistance) {
		this.minimumdistance = minimumdistance;
	}

	public void setParamerterconfigid(final Long paramerterconfigid) {
		this.paramerterconfigid = paramerterconfigid;
	}

	public void setParametertypeid(final Integer parametertypeid) {
		this.parametertypeid = parametertypeid;
	}

	public void setRecordingdelayinput(final String recordingdelayinput) {
		this.recordingdelayinput = recordingdelayinput;
	}

	public void setRecordingthreshold(final String recordingthreshold) {
		this.recordingthreshold = recordingthreshold;
	}

	public void setRecordingthresholdperc(final String recordingthresholdperc) {
		this.recordingthresholdperc = recordingthresholdperc;
	}

	public void setRequiredresttime(final String requiredresttime) {
		this.requiredresttime = requiredresttime;
	}

	public void setRollingdays(final Integer rollingdays) {
		this.rollingdays = rollingdays;
	}

	public void setThresholdlimit(final String thresholdlimit) {
		this.thresholdlimit = thresholdlimit;
	}

	public void setTomonth(final Integer tomonth) {
		if (Objects.isNull(tomonth) || tomonth == 0) {
			this.tomonth = 12;
		}
		this.tomonth = tomonth;
	}

	public void setTotime(final String totime) {
		this.totime = totime;
	}
}
