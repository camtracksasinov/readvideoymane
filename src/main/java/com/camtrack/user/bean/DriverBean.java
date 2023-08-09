//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.bean;

import com.camtrack.config.Utils;

public class DriverBean {
	private int driverid;
	private String drivername;
	private int vehicleid;

	public DriverBean() {
	}

	public DriverBean(final int vehicleid, final int driverid, final String drivername) {
		this.vehicleid = vehicleid;
		this.driverid = driverid;
		this.drivername = drivername;
	}

	public int getDriverid() {
		return this.driverid;
	}

	public String getDrivername() {
		return Utils.StringEscape(this.drivername);
	}

	public int getVehicleid() {
		return this.vehicleid;
	}

	public void setDriverid(final int driverid) {
		this.driverid = driverid;
	}

	public void setDrivername(final String drivername) {
		this.drivername = drivername;
	}

	public void setVehicleid(final int vehicleid) {
		this.vehicleid = vehicleid;
	}

	@Override
	public String toString() {
		return "DriverBean [vehicleid=" + this.vehicleid + ", driverid=" + this.driverid + ", drivername="
				+ this.drivername + "]";
	}
}
