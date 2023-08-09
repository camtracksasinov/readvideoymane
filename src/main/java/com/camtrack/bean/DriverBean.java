//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.bean;

import java.io.Serializable;
import java.util.Date;

public class DriverBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private Date createdon;
	private Integer driverid;
	private Integer transporterid;

	public Date getCreatedon() {
		return this.createdon;
	}

	public Integer getDriverid() {
		return this.driverid;
	}

	public Integer getTransporterid() {
		return this.transporterid;
	}

	public void setCreatedon(final Date createdon) {
		this.createdon = createdon;
	}

	public void setDriverid(final Integer driverid) {
		this.driverid = driverid;
	}

	public void setTransporterid(final Integer transporterid) {
		this.transporterid = transporterid;
	}
}
