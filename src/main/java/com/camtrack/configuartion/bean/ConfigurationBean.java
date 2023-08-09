//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.configuartion.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.camtrack.config.Utils;

public class ConfigurationBean implements Serializable {
	private static final long serialVersionUID = 1L;
	List<AdasParams> adasparams;
	Float alarmdelayinput;
	Float alarmthreshold;
	Float alarmthresholdperc;
	Float alertdelayinput;
	Float alertthreshold;
	Float alertthresholdperc;
	Integer clientaffiliateid;
	Integer customerid;
	int driverdisqualifiedlimit;
	int drivergreenlimit;
	int driverredlimit;
	int driveryellowlimit;
	List<FatigueParams> fatigparams;
	Float fatiguefrequency;
	int fatiguepoints;
	int frommonth;
	String fromtime;
	int hapoints;
	int hbpoints;
	int initialpoint;
	BigDecimal mindistance;
	int minsenioritydriver;
	int minsenioritytransporter;
	int noofcalendaryear;
	List<ObcParams> obcparams;
	Float osfrequency;
	int ospoints;
	Integer parametertypeid;
	Float recordingdelayinput;
	Float recordingthreshold;
	Float recordingthresholdperc;
	List<RecoveryParams> recovery;
	Float requiredresttime;
	int rollingdays;
	Integer scpid;
	Float thresholdlimit;
	int tomonth;
	String totime;
	Integer transporterid;
	int transporterredlimit;
	List<VisualParams> visualparams;

	public List<AdasParams> getAdasparams() {
		return this.adasparams;
	}

	public Float getAlarmdelayinput() {
		return this.alarmdelayinput;
	}

	public Float getAlarmthreshold() {
		return this.alarmthreshold;
	}

	public Float getAlarmthresholdperc() {
		return this.alarmthresholdperc;
	}

	public Float getAlertdelayinput() {
		return this.alertdelayinput;
	}

	public Float getAlertthreshold() {
		return this.alertthreshold;
	}

	public Float getAlertthresholdperc() {
		return this.alertthresholdperc;
	}

	public Integer getClientaffiliateid() {
		return this.clientaffiliateid;
	}

	public Integer getCustomerid() {
		return this.customerid;
	}

	public Integer getDriverdisqualifiedlimit() {
		return this.driverdisqualifiedlimit;
	}

	public Integer getDrivergreenlimit() {
		return this.drivergreenlimit;
	}

	public Integer getDriverredlimit() {
		return this.driverredlimit;
	}

	public Integer getDriveryellowlimit() {
		return this.driveryellowlimit;
	}

	public List<FatigueParams> getFatigparams() {
		return this.fatigparams;
	}

	public Float getFatiguefrequency() {
		return this.fatiguefrequency;
	}

	public Integer getFatiguepoints() {
		return this.fatiguepoints;
	}

	public Integer getFrommonth() {
		return this.frommonth;
	}

	public String getFromtime() {
		return Utils.StringEscape(this.fromtime);
	}

	public Integer getHapoints() {
		return this.hapoints;
	}

	public Integer getHbpoints() {
		return this.hbpoints;
	}

	public Integer getInitialpoint() {
		return this.initialpoint;
	}

	public BigDecimal getMindistance() {
		return this.mindistance;
	}

	public Integer getMinsenioritydriver() {
		return this.minsenioritydriver;
	}

	public Integer getMinsenioritytransporter() {
		return this.minsenioritytransporter;
	}

	public Integer getNoofcalendaryear() {
		return this.noofcalendaryear;
	}

	public List<ObcParams> getObcparams() {
		return this.obcparams;
	}

	public Float getOsfrequency() {
		return this.osfrequency;
	}

	public Integer getOspoints() {
		return this.ospoints;
	}

	public Integer getParametertypeid() {
		return this.parametertypeid;
	}

	public Float getRecordingdelayinput() {
		return this.recordingdelayinput;
	}

	public Float getRecordingthreshold() {
		return this.recordingthreshold;
	}

	public Float getRecordingthresholdperc() {
		return this.recordingthresholdperc;
	}

	public List<RecoveryParams> getRecovery() {
		return this.recovery;
	}

	public Float getRequiredresttime() {
		return this.requiredresttime;
	}

	public Integer getRollingdays() {
		return this.rollingdays;
	}

	public Integer getScpid() {
		return this.scpid;
	}

	public Float getThresholdlimit() {
		return this.thresholdlimit;
	}

	public Integer getTomonth() {
		return this.tomonth;
	}

	public String getTotime() {
		return Utils.StringEscape(this.totime);
	}

	public Integer getTransporterid() {
		return this.transporterid;
	}

	public Integer getTransporterredlimit() {
		return this.transporterredlimit;
	}

	public List<VisualParams> getVisualparams() {
		return this.visualparams;
	}

	public void setAdasparams(final List<AdasParams> adasparams) {
		this.adasparams = adasparams;
	}

	public void setAlarmdelayinput(final Float alarmdelayinput) {
		this.alarmdelayinput = alarmdelayinput;
	}

	public void setAlarmthreshold(final Float alarmthreshold) {
		this.alarmthreshold = alarmthreshold;
	}

	public void setAlarmthresholdperc(final Float alarmthresholdperc) {
		this.alarmthresholdperc = alarmthresholdperc;
	}

	public void setAlertdelayinput(final Float alertdelayinput) {
		this.alertdelayinput = alertdelayinput;
	}

	public void setAlertthreshold(final Float alertthreshold) {
		this.alertthreshold = alertthreshold;
	}

	public void setAlertthresholdperc(final Float alertthresholdperc) {
		this.alertthresholdperc = alertthresholdperc;
	}

	public void setClientaffiliateid(final Integer clientaffiliateid) {
		this.clientaffiliateid = clientaffiliateid;
	}

	public void setCustomerid(final Integer customerid) {
		this.customerid = customerid;
	}

	public void setDriverdisqualifiedlimit(final int driverdisqualifiedlimit) {
		this.driverdisqualifiedlimit = driverdisqualifiedlimit;
	}

	public void setDriverdisqualifiedlimit(final Integer driverdisqualifiedlimit) {
		this.driverdisqualifiedlimit = driverdisqualifiedlimit;
	}

	public void setDrivergreenlimit(final int drivergreenlimit) {
		this.drivergreenlimit = drivergreenlimit;
	}

	public void setDrivergreenlimit(final Integer drivergreenlimit) {
		this.drivergreenlimit = drivergreenlimit;
	}

	public void setDriverredlimit(final int driverredlimit) {
		this.driverredlimit = driverredlimit;
	}

	public void setDriverredlimit(final Integer driverredlimit) {
		this.driverredlimit = driverredlimit;
	}

	public void setDriveryellowlimit(final int driveryellowlimit) {
		this.driveryellowlimit = driveryellowlimit;
	}

	public void setDriveryellowlimit(final Integer driveryellowlimit) {
		this.driveryellowlimit = driveryellowlimit;
	}

	public void setFatigparams(final List<FatigueParams> fatigparams) {
		this.fatigparams = fatigparams;
	}

	public void setFatiguefrequency(final Float fatiguefrequency) {
		this.fatiguefrequency = fatiguefrequency;
	}

	public void setFatiguepoints(final int fatiguepoints) {
		this.fatiguepoints = fatiguepoints;
	}

	public void setFatiguepoints(final Integer fatiguepoints) {
		this.fatiguepoints = fatiguepoints;
	}

	public void setFrommonth(final int frommonth) {
		this.frommonth = frommonth;
	}

	public void setFrommonth(final Integer frommonth) {
		this.frommonth = frommonth;
	}

	public void setFromtime(final String fromtime) {
		this.fromtime = fromtime;
	}

	public void setHapoints(final int hapoints) {
		this.hapoints = hapoints;
	}

	public void setHapoints(final Integer hapoints) {
		this.hapoints = hapoints;
	}

	public void setHbpoints(final int hbpoints) {
		this.hbpoints = hbpoints;
	}

	public void setHbpoints(final Integer hbpoints) {
		this.hbpoints = hbpoints;
	}

	public void setInitialpoint(final int initialpoint) {
		this.initialpoint = initialpoint;
	}

	public void setInitialpoint(final Integer initialpoint) {
		this.initialpoint = initialpoint;
	}

	public void setMindistance(final BigDecimal mindistance) {
		this.mindistance = mindistance;
	}

	public void setMinsenioritydriver(final int minsenioritydriver) {
		this.minsenioritydriver = minsenioritydriver;
	}

	public void setMinsenioritydriver(final Integer minsenioritydriver) {
		this.minsenioritydriver = minsenioritydriver;
	}

	public void setMinsenioritytransporter(final int minsenioritytransporter) {
		this.minsenioritytransporter = minsenioritytransporter;
	}

	public void setMinsenioritytransporter(final Integer minsenioritytransporter) {
		this.minsenioritytransporter = minsenioritytransporter;
	}

	public void setNoofcalendaryear(final int noofcalendaryear) {
		this.noofcalendaryear = noofcalendaryear;
	}

	public void setNoofcalendaryear(final Integer noofcalendaryear) {
		this.noofcalendaryear = noofcalendaryear;
	}

	public void setObcparams(final List<ObcParams> obcparams) {
		this.obcparams = obcparams;
	}

	public void setOsfrequency(final Float osfrequency) {
		this.osfrequency = osfrequency;
	}

	public void setOspoints(final int ospoints) {
		this.ospoints = ospoints;
	}

	public void setOspoints(final Integer ospoints) {
		this.ospoints = ospoints;
	}

	public void setParametertypeid(final Integer parametertypeid) {
		this.parametertypeid = parametertypeid;
	}

	public void setRecordingdelayinput(final Float recordingdelayinput) {
		this.recordingdelayinput = recordingdelayinput;
	}

	public void setRecordingthreshold(final Float recordingthreshold) {
		this.recordingthreshold = recordingthreshold;
	}

	public void setRecordingthresholdperc(final Float recordingthresholdperc) {
		this.recordingthresholdperc = recordingthresholdperc;
	}

	public void setRecovery(final List<RecoveryParams> recovery) {
		this.recovery = recovery;
	}

	public void setRequiredresttime(final Float requiredresttime) {
		this.requiredresttime = requiredresttime;
	}

	public void setRollingdays(final int rollingdays) {
		this.rollingdays = rollingdays;
	}

	public void setRollingdays(final Integer rollingdays) {
		this.rollingdays = rollingdays;
	}

	public void setScpid(final Integer scpid) {
		this.scpid = scpid;
	}

	public void setThresholdlimit(final Float thresholdlimit) {
		this.thresholdlimit = thresholdlimit;
	}

	public void setTomonth(final int tomonth) {
		this.tomonth = tomonth;
	}

	public void setTomonth(final Integer tomonth) {
		this.tomonth = tomonth;
	}

	public void setTotime(final String totime) {
		this.totime = totime;
	}

	public void setTransporterid(final Integer transporterid) {
		this.transporterid = transporterid;
	}

	public void setTransporterredlimit(final int transporterredlimit) {
		this.transporterredlimit = transporterredlimit;
	}

	public void setTransporterredlimit(final Integer transporterredlimit) {
		this.transporterredlimit = transporterredlimit;
	}

	public void setVisualparams(final List<VisualParams> visualparams) {
		this.visualparams = visualparams;
	}
}
