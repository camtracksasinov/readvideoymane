//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.reports.bean;

public class RawStatistic {
	private Integer affiliateid;
	private String affiliatename;
	private Integer clientid;
	private String clientname;
	private Integer colorzone;
	private Integer driverdisqualifiedlimit;
	private Integer drivergreenlimit;
	private Integer driverid;
	private String drivername;
	private Integer driverredlimit;
	private Integer driveryellowlimit;
	private Integer excpnpoints;
	private Integer initialpoint;
	private Integer minsenioritydriver;
	private Integer recoverypoint1;
	private Integer recoverypoint2;
	private Integer recvrypoints;
	private Integer subpoints;
	private Integer totalremainingpoints;
	private Integer totalsubstractedpoints;
	private double totdist;
	private Integer transporterid;
	private String transportername;
	private Integer totacc;
	private String acc;

	public String getAcc() {
		return acc;
	}

	public void setAcc(String acc) {
		this.acc = acc;
	}

	public Integer getTotacc() {
		return totacc;
	}

	public void setTotacc(Integer totacc) {
		this.totacc = totacc;
	}

	public Integer getAffiliateid() {
		return this.affiliateid;
	}

	public String getAffiliatename() {
		return this.affiliatename;
	}

	public Integer getClientid() {
		return this.clientid;
	}

	public String getClientname() {
		return this.clientname;
	}

	public Integer getColorzone() {
		return this.colorzone;
	}

	public Integer getDriverdisqualifiedlimit() {
		return this.driverdisqualifiedlimit;
	}

	public Integer getDrivergreenlimit() {
		return this.drivergreenlimit;
	}

	public Integer getDriverid() {
		return this.driverid;
	}

	public String getDrivername() {
		return this.drivername;
	}

	public Integer getDriverredlimit() {
		return this.driverredlimit;
	}

	public Integer getDriveryellowlimit() {
		return this.driveryellowlimit;
	}

	public Integer getExcpnpoints() {
		return Math.abs(this.excpnpoints);
	}

	public Integer getInitialpoint() {
		return this.initialpoint;
	}

	public Integer getMinsenioritydriver() {
		return this.minsenioritydriver;
	}

	public Integer getRecoverypoint1() {
		return this.recoverypoint1;
	}

	public Integer getRecoverypoint2() {
		return this.recoverypoint2;
	}

	public Integer getRecvrypoints() {
		return this.recvrypoints;
	}

	public Integer getSubpoints() {
		return Math.abs(this.subpoints);
	}

	public Integer getTotalremainingpoints() {
		return this.totalremainingpoints;
	}

	public Integer getTotalsubstractedpoints() {
		return Math.abs(this.totalsubstractedpoints);
	}

	public double getTotdist() {
		return this.totdist;
	}

	public Integer getTransporterid() {
		return this.transporterid;
	}

	public String getTransportername() {
		return this.transportername;
	}

	public void setAffiliateid(final Integer affiliateid) {
		this.affiliateid = affiliateid;
	}

	public void setAffiliatename(final String affiliatename) {
		this.affiliatename = affiliatename;
	}

	public void setClientid(final Integer clientid) {
		this.clientid = clientid;
	}

	public void setClientname(final String clientname) {
		this.clientname = clientname;
	}

	public void setColorzone(final Integer colorzone) {
		this.colorzone = colorzone;
	}

	public void setDriverdisqualifiedlimit(final Integer driverdisqualifiedlimit) {
		this.driverdisqualifiedlimit = driverdisqualifiedlimit;
	}

	public void setDrivergreenlimit(final Integer drivergreenlimit) {
		this.drivergreenlimit = drivergreenlimit;
	}

	public void setDriverid(final Integer driverid) {
		this.driverid = driverid;
	}

	public void setDrivername(final String drivername) {
		this.drivername = drivername;
	}

	public void setDriverredlimit(final Integer driverredlimit) {
		this.driverredlimit = driverredlimit;
	}

	public void setDriveryellowlimit(final Integer driveryellowlimit) {
		this.driveryellowlimit = driveryellowlimit;
	}

	public void setExcpnpoints(final Integer excpnpoints) {
		this.excpnpoints = Math.abs(excpnpoints);
	}

	public void setInitialpoint(final Integer initialpoint) {
		this.initialpoint = initialpoint;
	}

	public void setMinsenioritydriver(final Integer minsenioritydriver) {
		this.minsenioritydriver = minsenioritydriver;
	}

	public void setRecoverypoint1(final Integer recoverypoint1) {
		this.recoverypoint1 = recoverypoint1;
	}

	public void setRecoverypoint2(final Integer recoverypoint2) {
		this.recoverypoint2 = recoverypoint2;
	}

	public void setRecvrypoints(final Integer recoverypoints) {
		this.recvrypoints = recoverypoints;
	}

	public void setSubpoints(final Integer subpoints) {
		this.subpoints = Math.abs(subpoints);
	}

	public void setTotalremainingpoints(final Integer totalremainingpoints) {
		this.totalremainingpoints = totalremainingpoints;
	}

	public void setTotalsubstractedpoints(final Integer totalsubstractedpoints) {
		this.totalsubstractedpoints = Math.abs(totalsubstractedpoints);
	}

	public void setTotdist(final double totdist) {
		this.totdist = totdist;
	}

	public void setTransporterid(final Integer transporterid) {
		this.transporterid = transporterid;
	}

	public void setTransportername(final String transportername) {
		this.transportername = transportername;
	}

	@Override
	public String toString() {
		return "RawStatistic [driverid=" + this.driverid + ", drivername=" + this.drivername + ", initialpoint="
				+ this.initialpoint + ", subpoints=" + this.subpoints + ", recoverypoints=" + this.recvrypoints
				+ ", clientname=" + this.clientname + ", clientid=" + this.clientid + ", affiliatename="
				+ this.affiliatename + ", affiliateid=" + this.affiliateid + ", transportername=" + this.transportername
				+ ", transporterid=" + this.transporterid + ", minsenioritydriver=" + this.minsenioritydriver
				+ ", totalremainingpoints=" + this.totalremainingpoints + ", totdist=" + this.totdist
				+ ", drivergreenlimit=" + this.drivergreenlimit + ", driveryellowlimit=" + this.driveryellowlimit
				+ ", driverredlimit=" + this.driverredlimit + ", driverdisqualifiedlimit="
				+ this.driverdisqualifiedlimit + ", recoverypoint1=" + this.recoverypoint1 + ", recoverypoint2="
				+ this.recoverypoint2 + ", excpnpoints=" + this.excpnpoints + ", totalsubstractedpoints="
				+ this.totalsubstractedpoints + ", colorzone=" + this.colorzone + "]";
	}

}
