package com.camtrack.unitsinformations.bean;

import java.io.Serializable;
import java.util.Date;

import com.camtrack.config.Utils;

public class MyTrucks implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Date dat;
	private String odom;
	private Integer trpid;
	private Integer vehid;
	private String vehn;

	public Date getDat() {
		return dat;
	}

	public String getOdom() {
		return Utils.StringEscape(odom);
	}

	public Integer getTrpid() {
		return trpid;
	}

	public Integer getVehid() {
		return vehid;
	}

	public String getVehn() {
		return Utils.StringEscape(vehn);
	}

	public void setDat(Date dat) {
		this.dat = dat;
	}

	public void setOdom(String odom) {
		this.odom = odom;
	}

	public void setTrpid(Integer trpid) {
		this.trpid = trpid;
	}

	public void setVehid(Integer vehid) {
		this.vehid = vehid;
	}

	public void setVehn(String vehn) {
		this.vehn = vehn;
	}

}
