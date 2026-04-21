// Decompiled by Procyon v0.5.30
//

package com.camtrack.bean;

import java.util.Objects;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Data;

@Data
public class AutomaticreportBean {
	public static String listToString(final Integer[] getListids) {
		String result = "";
		int k = 0;
		if (!Objects.isNull(getListids) && getListids.length > 0) {
			for (final Integer integer : getListids) {
				if (k == 0) {
					result = "" + integer;
				} else {
					result = result + "," + integer;
				}
				++k;
			}
		}
		return result;
	}

	private Boolean alarm;
	private Boolean alert;
	private Long automaticreport;
	private Integer[] listaffiliateids;
	private Integer[] listclientids;
	private Integer[] listdriverids;
	private Integer[] listidtypeexception;
	private Integer[] listtransporterids;
	private Integer[] listvehicleids;
	private Boolean record;
	@NotEmpty(message = "The subject is required and Not Empty.")
	@NotNull(message = "The subject is required and Not Empty")
	private String subject;
	@NotEmpty(message = "The emailist is required and Not Empty.")
	@NotNull(message = "The emailist is required and Not Empty.")
	private String emailist;
	@NotNull(message = "The formatreport is required.")
	@Positive(message = "The formatreport number must be greater than 0")
	private Short formatreport;
	@NotNull(message = "The idreportname is required.")
	@Positive(message = "The idreportname number must be greater than 0")
	private Integer idreportname;
	@NotNull(message = "The idfrequence is required.")
	@Positive(message = "The idfrequence number must be greater than 0")
	private Short idfrequence;
	@NotNull(message = "The timerange is required.")
	@Positive(message = "The timerange number must be greater than 0")
	private Short timerange;
	@NotEmpty(message = "The hourofrequest is required format HH:mm.")
	@NotNull(message = "The hourofrequest is required  format HH:mm.")
	private String hourofrequest;
	@NotNull(message = "The dayofweekordayofmonth is required.")
	@Positive(message = "The dayofweekordayofmonth number must be greater than 0")
	private Short dayofweekordayofmonth;
	@NotEmpty(message = "The firstdate is required format yyyy-MM-dd.")
	@NotNull(message = "The firstdate is required format yyyy-MM-dd.")
	private String firstdate;
	@NotEmpty(message = "The bodymail is required and Not Empty.")
	@NotNull(message = "The bodymail is required and Not Empty.")
	private String bodymail;

	public Short getIdfrequence() {
		return idfrequence;
	}

	public void setIdfrequence(Short idfrequence) {
		this.idfrequence = idfrequence;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getEmailist() {
		return emailist;
	}

	public void setEmailist(String emailist) {
		this.emailist = emailist;
	}

	public Short getFormatreport() {
		return formatreport;
	}

	public void setFormatreport(Short formatreport) {
		this.formatreport = formatreport;
	}

	public Integer getIdreportname() {
		return idreportname;
	}

	public void setIdreportname(Integer idreportname) {
		this.idreportname = idreportname;
	}

	public Short getTimerange() {
		return timerange;
	}

	public void setTimerange(Short timerange) {
		this.timerange = timerange;
	}

	public String getHourofrequest() {
		return hourofrequest;
	}

	public void setHourofrequest(String hourofrequest) {
		this.hourofrequest = hourofrequest;
	}

	public Short getDayofweekordayofmonth() {
		return dayofweekordayofmonth;
	}

	public void setDayofweekordayofmonth(Short dayofweekordayofmonth) {
		this.dayofweekordayofmonth = dayofweekordayofmonth;
	}

	public String getFirstdate() {
		return firstdate;
	}

	public void setFirstdate(String firstdate) {
		this.firstdate = firstdate;
	}

	public String getBodymail() {
		return bodymail;
	}

	public void setBodymail(String bodymail) {
		this.bodymail = bodymail;
	}

	/**
	 * @RequestParam(value = "subject", required = true) final String subject,
	 * @RequestParam(value = "emailist", required = true) final String emailist,
	 * @RequestParam(value = "idreportname", required = true) final Integer
	 *                     idreportname,
	 * @RequestParam(value = "idfrequence", required = true) final Short
	 *                     idfrequence,
	 * @RequestParam(value = "formatreport", required = true) final Short
	 *                     formatreport,
	 * @RequestParam(value = "timerange", required = true) final Short timerange,
	 * @RequestParam(value = "hourofrequest", required = true) final String
	 *                     hourofrequest,
	 * @RequestParam(value = "dayofweekordayofmonth", required = true) final Short
	 *                     dayofweekordayofmonth,
	 * @RequestParam(value = "firstdate", required = true) final String firstdate,
	 * @RequestParam(value = "bodymail", required = true) final String bodymail
	 * @return
	 */
	public Boolean getAlarm() {
		if (Objects.isNull(this.alarm)) {
			return false;
		}
		return this.alarm;
	}

	public Boolean getAlert() {
		if (Objects.isNull(this.alert)) {
			return false;
		}
		return this.alert;
	}

	public Long getAutomaticreport() {
		return this.automaticreport;
	}

	public Integer[] getListaffiliateids() {
		return this.listaffiliateids;
	}

	public Integer[] getListclientids() {
		return this.listclientids;
	}

	public Integer[] getListdriverids() {
		return this.listdriverids;
	}

	public Integer[] getListidtypeexception() {
		return this.listidtypeexception;
	}

	public Integer[] getListtransporterids() {
		return this.listtransporterids;
	}

	public Integer[] getListvehicleids() {
		return this.listvehicleids;
	}

	public Boolean getRecord() {
		if (Objects.isNull(this.record)) {
			return false;
		}
		return this.record;
	}

	public void setAlarm(final Boolean alarm) {
		this.alarm = alarm;
	}

	public void setAlert(final Boolean alert) {
		this.alert = alert;
	}

	public void setAutomaticreport(final Long automaticreport) {
		this.automaticreport = automaticreport;
	}

	public void setListaffiliateids(final Integer[] listaffiliateids) {
		this.listaffiliateids = listaffiliateids;
	}

	public void setListclientids(final Integer[] listclientids) {
		this.listclientids = listclientids;
	}

	public void setListdriverids(final Integer[] listdriverids) {
		this.listdriverids = listdriverids;
	}

	public void setListidtypeexception(final Integer[] listidtypeexception) {
		this.listidtypeexception = listidtypeexception;
	}

	public void setListtransporterids(final Integer[] listtransporterids) {
		this.listtransporterids = listtransporterids;
	}

	public void setListvehicleids(final Integer[] listvehicleids) {
		this.listvehicleids = listvehicleids;
	}

	public void setRecord(final Boolean record) {
		this.record = record;
	}
}
