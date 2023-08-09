//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.bean;

import java.util.Objects;

public class ListAllIdWithtypeexceptionBean {
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
	private Integer[] listaffiliateids;
	private Integer[] listclientids;
	private Integer[] listdriverids;
	private Integer[] listidtypeexception;
	private Integer[] listtransporterids;
	private Integer[] listvehicleids;

	private Boolean record;

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
