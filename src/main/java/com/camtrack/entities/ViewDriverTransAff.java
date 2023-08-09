//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class ViewDriverTransAff implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer affiliateid;
	private Date createdon;
	private Integer driverid;
	private String driverkeycode;
	private Integer id;
	private String name;
	private BigDecimal totaldistance;
	private Integer transporterid;

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || this.getClass() != obj.getClass()) {
			return false;
		}
		final ViewDriverTransAff other = (ViewDriverTransAff) obj;
		return this.id == other.id;
	}

	public Integer getAffiliateid() {
		return this.affiliateid;
	}

	public Date getCreatedon() {
		return this.createdon;
	}

	public Integer getDriverid() {
		return this.driverid;
	}

	public String getDriverkeycode() {
		return this.driverkeycode;
	}

	public Integer getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public BigDecimal getTotaldistance() {
		return this.totaldistance;
	}

	public Integer getTransporterid() {
		return this.transporterid;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id);
	}

	public void setAffiliateid(final Integer affiliateid) {
		this.affiliateid = affiliateid;
	}

	public void setCreatedon(final Date createdon) {
		this.createdon = createdon;
	}

	public void setDriverid(final Integer driverid) {
		this.driverid = driverid;
	}

	public void setDriverkeycode(final String driverkeycode) {
		this.driverkeycode = driverkeycode;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setTotaldistance(final BigDecimal totaldistance) {
		this.totaldistance = totaldistance;
	}

	public void setTransporterid(final Integer transporterid) {
		this.transporterid = transporterid;
	}

	@Override
	public String toString() {
		return "ViewDriverTransAff [id=" + this.id + ", driverid=" + this.driverid + ", name=" + this.name
				+ ", createdon=" + this.createdon + ", transporterid=" + this.transporterid + ", affiliateid="
				+ this.affiliateid + ", driverkeycode=" + this.driverkeycode + ", totaldistance=" + this.totaldistance
				+ "]";
	}
}
