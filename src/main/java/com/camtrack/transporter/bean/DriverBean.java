//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.transporter.bean;

import java.io.Serializable;

import com.camtrack.config.Utils;

public class DriverBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer driverid;
	private String driverkeycode;
	private String drivername;
	private Integer status;
	private Integer transporterid;
	private Integer vehicleId;

	public Integer getDriverid() {
		return this.driverid;
	}

	public String getDriverkeycode() {
		return Utils.StringEscape(this.driverkeycode);
	}

	public String getDrivername() {
		return Utils.StringEscape(this.drivername);
	}

	public Integer getStatus() {
		return this.status;
	}

	public Integer getTransporterid() {
		return this.transporterid;
	}

	public Integer getVehicleId() {
		return this.vehicleId;
	}

	public void setDriverid(final Integer driverid) {
		this.driverid = driverid;
	}

	public void setDriverkeycode(final String driverkeycode) {
		this.driverkeycode = driverkeycode;
	}

	public void setDrivername(final String drivername) {
		this.drivername = drivername;
	}

	public void setStatus(final Integer status) {
		this.status = status;
	}

	public void setTransporterid(final Integer transporterid) {
		this.transporterid = transporterid;
	}

	public void setVehicleId(final Integer vehicleId) {
		this.vehicleId = vehicleId;
	}
}
