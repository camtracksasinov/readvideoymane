//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.bean;

import java.io.Serializable;

import com.camtrack.config.Utils;

public class VehicleBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String regno;
	private Integer status;
	private String tpv;
	private Integer transporterid;
	private String transportername;
	private String unitid;
	private String vehicledesc;
	private Integer vehicleid;
	private String vehicleuniqueid;

	public String getRegno() {
		return Utils.StringEscape(this.regno);
	}

	public Integer getStatus() {
		return this.status;
	}

	public String getTpv() {
		return this.tpv;
	}

	public Integer getTransporterid() {
		return this.transporterid;
	}

	public String getTransportername() {
		return Utils.StringEscape(this.transportername);
	}

	public String getUnitid() {
		return this.unitid;
	}

	public String getVehicledesc() {
		return Utils.StringEscape(this.vehicledesc);
	}

	public Integer getVehicleid() {
		return this.vehicleid;
	}

	public String getVehicleuniqueid() {
		return this.vehicleuniqueid;
	}

	public void setRegno(final String regno) {
		this.regno = regno;
	}

	public void setStatus(final Integer status) {
		this.status = status;
	}

	public void setTpv(final String tpv) {
		this.tpv = tpv;
	}

	public void setTransporterid(final Integer transporterid) {
		this.transporterid = transporterid;
	}

	public void setTransportername(final String transportername) {
		this.transportername = transportername;
	}

	public void setUnitid(final String unitid) {
		this.unitid = unitid;
	}

	public void setVehicledesc(final String vehicledesc) {
		this.vehicledesc = vehicledesc;
	}

	public void setVehicleid(final Integer vehicleid) {
		this.vehicleid = vehicleid;
	}

	public void setVehicleuniqueid(final String vehicleuniqueid) {
		this.vehicleuniqueid = vehicleuniqueid;
	}
}
