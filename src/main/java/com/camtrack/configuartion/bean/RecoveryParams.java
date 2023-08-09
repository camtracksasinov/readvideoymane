//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.configuartion.bean;

import java.io.Serializable;

import com.camtrack.config.Utils;

public class RecoveryParams implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer idfixe;
	private Short nbmonths;
	private Integer pts;
	private Boolean select;
	private String sublbl;

	public Integer getId() {
		return this.id;
	}

	public Integer getIdfixe() {
		return this.idfixe;
	}

	public Short getNbmonths() {
		return this.nbmonths;
	}

	public Integer getPts() {
		return this.pts;
	}

	public Boolean getSelect() {
		return this.select;
	}

	public String getSublbl() {
		return Utils.StringEscape(this.sublbl);
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public void setIdfixe(final Integer idfixe) {
		this.idfixe = idfixe;
	}

	public void setNbmonths(final Short nbmonths) {
		this.nbmonths = nbmonths;
	}

	public void setPts(final Integer pts) {
		this.pts = pts;
	}

	public void setSelect(final Boolean select) {
		this.select = select;
	}

	public void setSublbl(final String sublbl) {
		this.sublbl = sublbl;
	}
}
