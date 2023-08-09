package com.camtrack.unitsinformations.bean;

import java.io.Serializable;
import java.util.Date;

import com.camtrack.config.Utils;

public class MyPositions implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Date dat;
	private Double lat;
	private Double lgs;
	private String odom;
	private Double sp;
	private Integer vehid;

	public Date getDat() {
		return dat;
	}

	public Double getLat() {
		return lat;
	}

	public Double getLgs() {
		return lgs;
	}

	public String getOdom() {
		return Utils.StringEscape(odom);
	}

	public Double getSp() {
		return sp;
	}

	public Integer getVehid() {
		return vehid;
	}

	public void setDat(Date dat) {
		this.dat = dat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public void setLgs(Double lgs) {
		this.lgs = lgs;
	}

	public void setOdom(String odom) {
		this.odom = odom;
	}

	public void setSp(Double sp) {
		this.sp = sp;
	}

	public void setVehid(Integer vehid) {
		this.vehid = vehid;
	}

}
