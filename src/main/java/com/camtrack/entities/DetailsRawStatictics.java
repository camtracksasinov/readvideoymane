package com.camtrack.entities;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class DetailsRawStatictics implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer initp;
	private Integer remainp;
	private String acc;

	public String getAcc() {
		return acc;
	}

	public void setAcc(String acc) {
		this.acc = acc;
	}

	public Integer getInitp() {
		return initp;
	}

	public void setInitp(Integer initp) {
		this.initp = initp;
	}

	public Integer getRemainp() {
		return remainp;
	}

	public void setRemainp(Integer remainp) {
		this.remainp = remainp;
	}

	private List<SmallRaw> recov;
	private List<SmallRaw> remov;
	private List<SmallRaw> infraction;

	public List<SmallRaw> getInfraction() {
		return infraction;
	}

	public void setInfraction(List<SmallRaw> infraction) {
		this.infraction = infraction;
	}

	public List<SmallRaw> getRecov() {
		return recov;
	}

	public void setRecov(List<SmallRaw> recov) {
		this.recov = recov;
	}

	public List<SmallRaw> getRemov() {
		return remov;
	}

	public void setRemov(List<SmallRaw> remov) {
		this.remov = remov;
	}

}
