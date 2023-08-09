package com.camtrack.user.bean;

import java.io.Serializable;

import com.camtrack.config.Utils;

public class Hierarchies implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Integer affid;
	private String affnm;
	private Integer clid;
	private String clnm;
	private Integer drivid;
	private String drivnm;
	private Integer trspid;
	private String trspnm;
	private Integer vehid;
	private String vehnm;

	public Integer getAffid() {
		return affid;
	}

	public String getAffnm() {
		return Utils.StringEscape(affnm);
	}

	public Integer getClid() {
		return clid;
	}

	public String getClnm() {
		return Utils.StringEscape(clnm);
	}

	public Integer getDrivid() {
		return drivid;
	}

	public String getDrivnm() {
		return Utils.StringEscape(drivnm);
	}

	public Integer getTrspid() {
		return trspid;
	}

	public String getTrspnm() {
		return Utils.StringEscape(trspnm);
	}

	public Integer getVehid() {
		return vehid;
	}

	public String getVehnm() {
		return Utils.StringEscape(vehnm);
	}

	public void setAffid(Integer affid) {
		this.affid = affid;
	}

	public void setAffnm(String affnm) {
		this.affnm = affnm;
	}

	public void setClid(Integer clid) {
		this.clid = clid;
	}

	public void setClnm(String clnm) {
		this.clnm = clnm;
	}

	public void setDrivid(Integer drivid) {
		this.drivid = drivid;
	}

	public void setDrivnm(String drivnm) {
		this.drivnm = drivnm;
	}

	public void setTrspid(Integer trspid) {
		this.trspid = trspid;
	}

	public void setTrspnm(String trspnm) {
		this.trspnm = trspnm;
	}

	public void setVehid(Integer vehid) {
		this.vehid = vehid;
	}

	public void setVehnm(String vehnm) {
		this.vehnm = vehnm;
	}

}
