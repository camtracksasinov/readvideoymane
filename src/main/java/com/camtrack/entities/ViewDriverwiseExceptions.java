//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "view_driverwise_exceptions")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({
		@NamedQuery(name = "ViewDriverwiseExceptions.findAll", query = "SELECT v FROM ViewDriverwiseExceptions v"),
		@NamedQuery(name = "ViewDriverwiseExceptions.findByAffiliateid", query = "SELECT v FROM ViewDriverwiseExceptions v WHERE v.affiliateid = :affiliateid"),
		@NamedQuery(name = "ViewDriverwiseExceptions.findByTransporterid", query = "SELECT v FROM ViewDriverwiseExceptions v WHERE v.transporterid = :transporterid"),
		@NamedQuery(name = "ViewDriverwiseExceptions.findByDriverid", query = "SELECT v FROM ViewDriverwiseExceptions v WHERE v.driverid = :driverid"),
		@NamedQuery(name = "ViewDriverwiseExceptions.findByExceptiontype", query = "SELECT v FROM ViewDriverwiseExceptions v WHERE v.exceptiontype = :exceptiontype"),
		@NamedQuery(name = "ViewDriverwiseExceptions.findByLevel1Cnt", query = "SELECT v FROM ViewDriverwiseExceptions v WHERE v.level1Cnt = :level1Cnt"),
		@NamedQuery(name = "ViewDriverwiseExceptions.findByLevel2Cnt", query = "SELECT v FROM ViewDriverwiseExceptions v WHERE v.level2Cnt = :level2Cnt"),
		@NamedQuery(name = "ViewDriverwiseExceptions.findByLevel3Cnt", query = "SELECT v FROM ViewDriverwiseExceptions v WHERE v.level3Cnt = :level3Cnt"),
		@NamedQuery(name = "ViewDriverwiseExceptions.findByRecordscore", query = "SELECT v FROM ViewDriverwiseExceptions v WHERE v.recordscore = :recordscore"),
		@NamedQuery(name = "ViewDriverwiseExceptions.findByAlertscore", query = "SELECT v FROM ViewDriverwiseExceptions v WHERE v.alertscore = :alertscore"),
		@NamedQuery(name = "ViewDriverwiseExceptions.findByAlarmscore", query = "SELECT v FROM ViewDriverwiseExceptions v WHERE v.alarmscore = :alarmscore"),
		@NamedQuery(name = "ViewDriverwiseExceptions.findByObcparamid", query = "SELECT v FROM ViewDriverwiseExceptions v WHERE v.obcparamid = :obcparamid"),
		@NamedQuery(name = "ViewDriverwiseExceptions.findByObclabel", query = "SELECT v FROM ViewDriverwiseExceptions v WHERE v.obclabel = :obclabel"),
		@NamedQuery(name = "ViewDriverwiseExceptions.findById", query = "SELECT v FROM ViewDriverwiseExceptions v WHERE v.id = :id") })
public class ViewDriverwiseExceptions implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name = "affiliateid")
	private Integer affiliateid;
	@Column(name = "alarmscore")
	private Integer alarmscore;
	@Column(name = "alertscore")
	private Integer alertscore;
	@Column(name = "driverid")
	private Integer driverid;
	@Column(name = "exceptiontype")
	private Integer exceptiontype;
	@Id
	@Column(name = "id")
	private Long id;
	@Column(name = "level1_cnt")
	private Integer level1Cnt;
	@Column(name = "level2_cnt")
	private Integer level2Cnt;
	@Column(name = "level3_cnt")
	private Integer level3Cnt;
	@Column(name = "obclabel")
	private String obclabel;
	@Column(name = "obcparamid")
	private Integer obcparamid;
	@Column(name = "recordscore")
	private Integer recordscore;
	@Column(name = "transporterid")
	private Integer transporterid;

	public Integer getAffiliateid() {
		return this.affiliateid;
	}

	public Integer getRecordscore() {
		if (Objects.isNull(this.recordscore)) {
			return 0;
		}
		return this.recordscore;
	}

	public Integer getAlarmscore() {
		if (Objects.isNull(this.alarmscore)) {
			return 0;
		}
		return this.alarmscore;
	}

	public Integer getAlertscore() {
		if (Objects.isNull(this.alertscore)) {
			return 0;
		}
		return this.alertscore;
	}

	public Integer getDriverid() {
		return this.driverid;
	}

	public Integer getExceptiontype() {
		return this.exceptiontype;
	}

	public Long getId() {
		return this.id;
	}

	public Integer getLevel1Cnt() {
		return this.level1Cnt;
	}

	public Integer getLevel2Cnt() {
		return this.level2Cnt;
	}

	public Integer getLevel3Cnt() {
		return this.level3Cnt;
	}

	public String getObclabel() {
		return this.obclabel;
	}

	public Integer getObcparamid() {
		return this.obcparamid;
	}

	public Integer getTransporterid() {
		return this.transporterid;
	}

	public void setAffiliateid(final Integer affiliateid) {
		this.affiliateid = affiliateid;
	}

	public void setAlarmscore(final Integer alarmscore) {
		this.alarmscore = alarmscore;
	}

	public void setAlertscore(final Integer alertscore) {
		this.alertscore = alertscore;
	}

	public void setDriverid(final Integer driverid) {
		this.driverid = driverid;
	}

	public void setExceptiontype(final Integer exceptiontype) {
		this.exceptiontype = exceptiontype;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public void setLevel1Cnt(final Integer level1Cnt) {
		this.level1Cnt = level1Cnt;
	}

	public void setLevel2Cnt(final Integer level2Cnt) {
		this.level2Cnt = level2Cnt;
	}

	public void setLevel3Cnt(final Integer level3Cnt) {
		this.level3Cnt = level3Cnt;
	}

	public void setObclabel(final String obclabel) {
		this.obclabel = obclabel;
	}

	public void setObcparamid(final Integer obcparamid) {
		this.obcparamid = obcparamid;
	}

	public void setRecordscore(final Integer recordscore) {
		this.recordscore = recordscore;
	}

	public void setTransporterid(final Integer transporterid) {
		this.transporterid = transporterid;
	}
}
