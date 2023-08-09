//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.transporter.bean;

import java.io.Serializable;

import com.camtrack.config.Utils;

public class TransporterBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer affiliateid;
	private String name;
	private Integer status;
	private Integer transporterid;
	private String transportuniqueid;

	public int getAffiliateid() {
		return this.affiliateid;
	}

	public String getName() {
		return Utils.StringEscape(this.name);
	}

	public Integer getStatus() {
		return this.status;
	}

	public int getTransporterid() {
		return this.transporterid;
	}

	public String getTransportuniqueid() {
		return this.transportuniqueid;
	}

	public void setAffiliateid(final int affiliateid) {
		this.affiliateid = affiliateid;
	}

	public void setAffiliateid(final Integer affiliateid) {
		this.affiliateid = affiliateid;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setStatus(final Integer status) {
		this.status = status;
	}

	public void setTransporterid(final int transporterid) {
		this.transporterid = transporterid;
	}

	public void setTransporterid(final Integer transporterid) {
		this.transporterid = transporterid;
	}

	public void setTransportuniqueid(final String transportuniqueid) {
		this.transportuniqueid = transportuniqueid;
	}
}
