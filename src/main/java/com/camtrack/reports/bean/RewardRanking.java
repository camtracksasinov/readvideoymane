//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.reports.bean;

import java.io.Serializable;

public class RewardRanking implements Serializable {
	private static final long serialVersionUID = 1L;
	private String acci;
	private Integer affid;
	private Double avgdrvp;
	private Integer color;
	private Integer cusid;
	private Integer drivdist;
	private Integer drivgl;
	private Integer drivid;
	private Double drivr;
	private Integer drivrl;
	private Integer drivyl;
	private Integer excp;
	private Integer initp;
	private Integer minpach;
	private Integer minsdrv;
	private Integer recovp1;
	private Integer recovp2;
	private Integer recvp;
	private Integer subp;
	private double totdist;
	private Integer totrp;
	private Integer totsubp;
	private Integer trpid;
	private Double trpr;
	private Integer level;
	private Integer nbrchaff;
	private Integer nbracc;
	private Integer totacc;

	public Integer getNbrchaff() {
		return nbrchaff;
	}

	public void setNbrchaff(Integer nbrchaff) {
		this.nbrchaff = nbrchaff;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Double getTrpr() {
		return trpr;
	}

	public void setTrpr(Double trpr) {
		this.trpr = trpr;
	}

	public String getAcci() {
		return this.acci;
	}

	public Integer getAffid() {
		return this.affid;
	}

	public Double getAvgdrvp() {
		return this.avgdrvp;
	}

	public Integer getColor() {
		return this.color;
	}

	public Integer getCusid() {
		return this.cusid;
	}

	public Integer getDrivdist() {
		return this.drivdist;
	}

	public Integer getDrivgl() {
		return this.drivgl;
	}

	public Integer getDrivid() {
		return this.drivid;
	}

	public Double getDrivr() {
		return this.drivr;
	}

	public Integer getDrivrl() {
		return this.drivrl;
	}

	public Integer getDrivyl() {
		return this.drivyl;
	}

	public Integer getExcp() {
		return Math.abs(this.excp);
	}

	public Integer getInitp() {
		return this.initp;
	}

	public Integer getMinpach() {
		return this.minpach;
	}

	public Integer getMinsdrv() {
		return this.minsdrv;
	}

	public Integer getRecovp1() {
		return this.recovp1;
	}

	public Integer getRecovp2() {
		return this.recovp2;
	}

	public Integer getRecvp() {
		return this.recvp;
	}

	public Integer getSubp() {
		return Math.abs(this.subp);
	}

	public double getTotdist() {
		return this.totdist;
	}

	public Integer getTotrp() {
		return this.totrp;
	}

	public Integer getTotsubp() {
		return Math.abs(this.totsubp);
	}

	public Integer getTrpid() {
		return this.trpid;
	}

	public void setAcci(final String acci) {
		this.acci = acci;
	}

	public void setAffid(final Integer affid) {
		this.affid = affid;
	}

	public void setAvgdrvp(final Double avgdrvp) {
		this.avgdrvp = avgdrvp;
	}

	public void setColor(final Integer color) {
		this.color = color;
	}

	public void setCusid(final Integer cusid) {
		this.cusid = cusid;
	}

	public void setDrivdist(final Integer drivdist) {
		this.drivdist = drivdist;
	}

	public void setDrivgl(final Integer drivgl) {
		this.drivgl = drivgl;
	}

	public void setDrivid(final Integer drivid) {
		this.drivid = drivid;
	}

	public void setDrivr(final Double drivr) {
		this.drivr = drivr;
	}

	public void setDrivrl(final Integer drivrl) {
		this.drivrl = drivrl;
	}

	public void setDrivyl(final Integer drivyl) {
		this.drivyl = drivyl;
	}

	public void setExcp(final Integer excp) {
		this.excp = Math.abs(excp);
	}

	public void setInitp(final Integer initp) {
		this.initp = initp;
	}

	public void setMinpach(final Integer minpach) {
		this.minpach = minpach;
	}

	public void setMinsdrv(final Integer minsdrv) {
		this.minsdrv = minsdrv;
	}

	public void setRecovp1(final Integer recovp1) {
		this.recovp1 = recovp1;
	}

	public void setRecovp2(final Integer recovp2) {
		this.recovp2 = recovp2;
	}

	public void setRecvp(final Integer recvp) {
		this.recvp = recvp;
	}

	public void setSubp(final Integer subp) {
		this.subp = Math.abs(subp);
	}

	public void setTotdist(final double totdist) {
		this.totdist = totdist;
	}

	public void setTotrp(final Integer totrp) {
		this.totrp = totrp;
	}

	public void setTotsubp(final Integer totsubp) {
		this.totsubp = Math.abs(totsubp);
	}

	public void setTrpid(final Integer trpid) {
		this.trpid = trpid;
	}

	public Integer getNbracc() {
		return nbracc;
	}

	public void setNbracc(Integer nbracc) {
		this.nbracc = nbracc;
	}

	public Integer getTotacc() {
		return totacc;
	}

	public void setTotacc(Integer totacc) {
		this.totacc = totacc;
	}

}
