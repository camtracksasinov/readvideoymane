//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import com.camtrack.config.Utils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "scp")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Scp.findAll", query = "SELECT s FROM Scp s"),
		@NamedQuery(name = "Scp.findByScpid", query = "SELECT s FROM Scp s WHERE s.scpid = :scpid"),
		@NamedQuery(name = "Scp.findByClientid", query = "SELECT s FROM Scp s WHERE s.clientid = :clientid"),
		@NamedQuery(name = "Scp.findByAffiliateid", query = "SELECT s FROM Scp s WHERE s.affiliateid = :affiliateid"),
		@NamedQuery(name = "Scp.findByInitialpoint", query = "SELECT s FROM Scp s WHERE s.initialpoint = :initialpoint"),
		@NamedQuery(name = "Scp.findByNoofcalendaryear", query = "SELECT s FROM Scp s WHERE s.noofcalendaryear = :noofcalendaryear"),
		@NamedQuery(name = "Scp.findByDrivergreenlimit", query = "SELECT s FROM Scp s WHERE s.drivergreenlimit = :drivergreenlimit"),
		@NamedQuery(name = "Scp.findByDriveryellowlimit", query = "SELECT s FROM Scp s WHERE s.driveryellowlimit = :driveryellowlimit"),
		@NamedQuery(name = "Scp.findByDriverredlimit", query = "SELECT s FROM Scp s WHERE s.driverredlimit = :driverredlimit"),
		@NamedQuery(name = "Scp.findByDriverdisqualifiedlimit", query = "SELECT s FROM Scp s WHERE s.driverdisqualifiedlimit = :driverdisqualifiedlimit"),
		@NamedQuery(name = "Scp.findByTransporterredlimit", query = "SELECT s FROM Scp s WHERE s.transporterredlimit = :transporterredlimit"),
		@NamedQuery(name = "Scp.findByMinsenioritydriver", query = "SELECT s FROM Scp s WHERE s.minsenioritydriver = :minsenioritydriver"),
		@NamedQuery(name = "Scp.findByMinsenioritytransporter", query = "SELECT s FROM Scp s WHERE s.minsenioritytransporter = :minsenioritytransporter"),
		@NamedQuery(name = "Scp.findByCreatedon", query = "SELECT s FROM Scp s WHERE s.createdon = :createdon"),
		@NamedQuery(name = "Scp.findByCreatedby", query = "SELECT s FROM Scp s WHERE s.createdby = :createdby"),
		@NamedQuery(name = "Scp.findByUpdatedon", query = "SELECT s FROM Scp s WHERE s.updatedon = :updatedon"),
		@NamedQuery(name = "Scp.findByUpdatedby", query = "SELECT s FROM Scp s WHERE s.updatedby = :updatedby"),
		@NamedQuery(name = "Scp.findByStatus", query = "SELECT s FROM Scp s WHERE s.status = :status"),
		@NamedQuery(name = "Scp.findByMindistance", query = "SELECT s FROM Scp s WHERE s.mindistance = :mindistance") })
public class Scp implements Serializable {
	public static String log;
	private static final long serialVersionUID = 1L;
	static {
		Scp.log = "";
	}

	public static void init(final boolean createorupdate, final User user) {
		Scp.log = Utils.EntetesLog(createorupdate, user.getUsername(), "SCP Parameter");
	}

	@Column(name = "affiliateid")
	private Integer affiliateid;
	@Column(name = "clientid")
	private Integer clientid;
	@Column(name = "createdby")
	private Integer createdby;
	@Column(name = "createdon")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdon;
	@Column(name = "driverdisqualifiedlimit")
	private Integer driverdisqualifiedlimit;
	@Column(name = "drivergreenlimit")
	private Integer drivergreenlimit;
	@Column(name = "driverredlimit")
	private Integer driverredlimit;
	@Column(name = "driveryellowlimit")
	private Integer driveryellowlimit;
	@Column(name = "initialpoint")
	private Integer initialpoint;
	@Column(name = "mindistance")
	private BigDecimal mindistance;
	@Column(name = "minsenioritydriver")
	private Integer minsenioritydriver;
	@Column(name = "minsenioritytransporter")
	private Integer minsenioritytransporter;
	@Column(name = "noofcalendaryear")
	private Integer noofcalendaryear;
	private Integer accpoints;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "scpid")
	private Integer scpid;
	@Column(name = "status")
	private Integer status;
	@Column(name = "transporterid")
	private Integer transporterid;
	@Column(name = "transporterredlimit")
	private Integer transporterredlimit;

	@Column(name = "updatedby")
	private Integer updatedby;

	@Column(name = "updatedon")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedon;

	public Scp() {
	}

	public Scp(final Integer scpid) {
		this.scpid = scpid;
	}

	public static String getLog() {
		return log;
	}

	public static void setLog(String log) {
		Scp.log = log;
	}

	public Integer getAccpoints() {
		if (Objects.isNull(accpoints)) {
			return 1;
		}
		return accpoints;
	}

	public void setAccpoints(Integer accpoints, String name, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(accpoints)) {
				Scp.log = Utils.createnewlogs(Scp.log, accpoints + "", "Accpoints");
			}
		} else if (!Objects.isNull(accpoints) && !Objects.isNull(this.accpoints)) {
			Scp.log = Utils.updatelogsinfos(Scp.log, this.accpoints + "", accpoints + "", "Accpoints");
		} else if (!Objects.isNull(accpoints)) {
			Scp.log = Utils.updatelogsinfos(Scp.log, "", accpoints + "", "Accpoints");
		}
		this.accpoints = accpoints;

	}

	public void setAffiliateid(Integer affiliateid) {
		this.affiliateid = affiliateid;
	}

	public void setClientid(Integer clientid) {
		this.clientid = clientid;
	}

	public void setDriverdisqualifiedlimit(Integer driverdisqualifiedlimit) {
		this.driverdisqualifiedlimit = driverdisqualifiedlimit;
	}

	public void setDrivergreenlimit(Integer drivergreenlimit) {
		this.drivergreenlimit = drivergreenlimit;
	}

	public void setDriverredlimit(Integer driverredlimit) {
		this.driverredlimit = driverredlimit;
	}

	public void setDriveryellowlimit(Integer driveryellowlimit) {
		this.driveryellowlimit = driveryellowlimit;
	}

	public void setInitialpoint(Integer initialpoint) {
		this.initialpoint = initialpoint;
	}

	public void setMindistance(BigDecimal mindistance) {
		this.mindistance = mindistance;
	}

	public void setMinsenioritydriver(Integer minsenioritydriver) {
		this.minsenioritydriver = minsenioritydriver;
	}

	public void setMinsenioritytransporter(Integer minsenioritytransporter) {
		this.minsenioritytransporter = minsenioritytransporter;
	}

	public void setNoofcalendaryear(Integer noofcalendaryear) {
		this.noofcalendaryear = noofcalendaryear;
	}

	public void setTransporterid(Integer transporterid) {
		this.transporterid = transporterid;
	}

	public void setTransporterredlimit(Integer transporterredlimit) {
		this.transporterredlimit = transporterredlimit;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Scp)) {
			return false;
		}
		final Scp other = (Scp) object;
		return (this.scpid != null || other.scpid == null) && (this.scpid == null || this.scpid.equals(other.scpid));
	}

	public Integer getAffiliateid() {
		return this.affiliateid;
	}

	public Integer getClientid() {
		return this.clientid;
	}

	public Integer getCreatedby() {
		return this.createdby;
	}

	public Date getCreatedon() {
		return this.createdon;
	}

	public Integer getDriverdisqualifiedlimit() {
		return this.driverdisqualifiedlimit;
	}

	public Integer getDrivergreenlimit() {
		return this.drivergreenlimit;
	}

	public Integer getDriverredlimit() {
		return this.driverredlimit;
	}

	public Integer getDriveryellowlimit() {
		return this.driveryellowlimit;
	}

	public Integer getInitialpoint() {
		return this.initialpoint;
	}

	public BigDecimal getMindistance() {
		return this.mindistance;
	}

	public Integer getMinsenioritydriver() {
		return this.minsenioritydriver;
	}

	public Integer getMinsenioritytransporter() {
		return this.minsenioritytransporter;
	}

	public Integer getNoofcalendaryear() {
		return this.noofcalendaryear;
	}

	public Integer getScpid() {
		return this.scpid;
	}

	public Integer getStatus() {
		return this.status;
	}

	public Integer getTransporterid() {
		return this.transporterid;
	}

	public Integer getTransporterredlimit() {
		return this.transporterredlimit;
	}

	public Integer getUpdatedby() {
		return this.updatedby;
	}

	public Date getUpdatedon() {
		return this.updatedon;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.scpid != null) ? this.scpid.hashCode() : 0);
		return hash;
	}

	public void setAffiliateid(final Integer affiliateid, final String name, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(name)) {
				Scp.log = Utils.createnewlogs(Scp.log, name + "", "Affiliate LEVEL");
			}
		} else if (!Objects.isNull(name)) {
			Scp.log = Utils.updatelogsinfos(Scp.log, "", name + "", "Affiliate LEVEL");
		} else if (!Objects.isNull(name)) {
			Scp.log = Utils.updatelogsinfos(Scp.log, "", name + "", "Affiliate LEVEL");
		}
		this.affiliateid = affiliateid;
	}

	public void setClientid(final Integer clientid, final String name, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(name)) {
				Scp.log = Utils.createnewlogs(Scp.log, name + "", "CUSTOMER LEVEL");
			}
		} else if (!Objects.isNull(name)) {
			Scp.log = Utils.updatelogsinfos(Scp.log, "", name + "", "CUSTOMER LEVEL");
		} else if (!Objects.isNull(name)) {
			Scp.log = Utils.updatelogsinfos(Scp.log, "", name + "", "CUSTOMER LEVEL");
		}
		this.clientid = clientid;
	}

	public void setCreatedby(final Integer createdby) {
		this.createdby = createdby;
	}

	public void setCreatedon(final Date createdon) {
		this.createdon = createdon;
	}

	public void setDriverdisqualifiedlimit(final Integer driverdisqualifiedlimit, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(driverdisqualifiedlimit)) {
				Scp.log = Utils.createnewlogs(Scp.log, driverdisqualifiedlimit + "", "Driver Disqualified Limit");
			}
		} else if (!Objects.isNull(driverdisqualifiedlimit) && !Objects.isNull(this.driverdisqualifiedlimit)) {
			Scp.log = Utils.updatelogsinfos(Scp.log, this.driverdisqualifiedlimit + "", driverdisqualifiedlimit + "",
					"Driver Disqualified Limit");
		} else if (!Objects.isNull(driverdisqualifiedlimit)) {
			Scp.log = Utils.updatelogsinfos(Scp.log, "", driverdisqualifiedlimit + "", "Driver Disqualified Limit");
		}
		this.driverdisqualifiedlimit = driverdisqualifiedlimit;
	}

	public void setDrivergreenlimit(final Integer drivergreenlimit, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(drivergreenlimit)) {
				Scp.log = Utils.createnewlogs(Scp.log, drivergreenlimit + "", "Driver Green Limit");
			}
		} else if (!Objects.isNull(drivergreenlimit) && !Objects.isNull(this.drivergreenlimit)) {
			Scp.log = Utils.updatelogsinfos(Scp.log, this.drivergreenlimit + "", drivergreenlimit + "",
					"Driver Green Limit");
		} else if (!Objects.isNull(drivergreenlimit)) {
			Scp.log = Utils.updatelogsinfos(Scp.log, "", drivergreenlimit + "", "Driver Green Limit");
		}
		this.drivergreenlimit = drivergreenlimit;
	}

	public void setDriverredlimit(final Integer driverredlimit, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(driverredlimit)) {
				Scp.log = Utils.createnewlogs(Scp.log, driverredlimit + "", "Driver Red Limit");
			}
		} else if (!Objects.isNull(driverredlimit) && !Objects.isNull(this.driverredlimit)) {
			Scp.log = Utils.updatelogsinfos(Scp.log, this.driverredlimit + "", driverredlimit + "", "Driver Red Limit");
		} else if (!Objects.isNull(driverredlimit)) {
			Scp.log = Utils.updatelogsinfos(Scp.log, "", driverredlimit + "", "Driver Red Limit");
		}
		this.driverredlimit = driverredlimit;
	}

	public void setDriveryellowlimit(final Integer driveryellowlimit, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(driveryellowlimit)) {
				Scp.log = Utils.createnewlogs(Scp.log, driveryellowlimit + "", "Driver Yellow Limit");
			}
		} else if (!Objects.isNull(driveryellowlimit) && !Objects.isNull(this.driveryellowlimit)) {
			Scp.log = Utils.updatelogsinfos(Scp.log, this.driveryellowlimit + "", driveryellowlimit + "",
					"Driver Yellow Limit");
		} else if (!Objects.isNull(driveryellowlimit)) {
			Scp.log = Utils.updatelogsinfos(Scp.log, "", driveryellowlimit + "", "Driver Yellow Limit");
		}
		this.driveryellowlimit = driveryellowlimit;
	}

	public void setInitialpoint(final Integer initialpoint, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(initialpoint)) {
				Scp.log = Utils.createnewlogs(Scp.log, initialpoint + "", "Initial Point");
			}
		} else if (!Objects.isNull(initialpoint) && !Objects.isNull(this.initialpoint)) {
			Scp.log = Utils.updatelogsinfos(Scp.log, this.initialpoint + "", initialpoint + "", "Initial Point");
		} else if (!Objects.isNull(initialpoint)) {
			Scp.log = Utils.updatelogsinfos(Scp.log, "", initialpoint + "", "Initial Point");
		}
		this.initialpoint = initialpoint;
	}

	public void setMindistance(final BigDecimal mindistance, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(mindistance)) {
				Scp.log = Utils.createnewlogs(Scp.log, mindistance + "", "Minimun Distance");
			}
		} else if (!Objects.isNull(mindistance) && !Objects.isNull(this.mindistance)) {
			Scp.log = Utils.updatelogsinfos(Scp.log, this.mindistance + "", mindistance + "", "Minimun Distance");
		} else if (!Objects.isNull(mindistance)) {
			Scp.log = Utils.updatelogsinfos(Scp.log, "", mindistance + "", "Minimun Distance");
		}
		this.mindistance = mindistance;
	}

	public void setMinsenioritydriver(final Integer minsenioritydriver, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(minsenioritydriver)) {
				Scp.log = Utils.createnewlogs(Scp.log, minsenioritydriver + "", "Minimun Seniority Driver");
			}
		} else if (!Objects.isNull(minsenioritydriver) && !Objects.isNull(this.minsenioritydriver)) {
			Scp.log = Utils.updatelogsinfos(Scp.log, this.minsenioritydriver + "", minsenioritydriver + "",
					"Minimun Seniority Driver");
		} else if (!Objects.isNull(minsenioritydriver)) {
			Scp.log = Utils.updatelogsinfos(Scp.log, "", minsenioritydriver + "", "Minimun Seniority Driver");
		}
		this.minsenioritydriver = minsenioritydriver;
	}

	public void setMinsenioritytransporter(final Integer minsenioritytransporter, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(minsenioritytransporter)) {
				Scp.log = Utils.createnewlogs(Scp.log, minsenioritytransporter + "", "Minimun Seniority Transporter");
			}
		} else if (!Objects.isNull(minsenioritytransporter) && !Objects.isNull(this.minsenioritytransporter)) {
			Scp.log = Utils.updatelogsinfos(Scp.log, this.minsenioritytransporter + "", minsenioritytransporter + "",
					"Minimun Seniority Transporter");
		} else if (!Objects.isNull(minsenioritytransporter)) {
			Scp.log = Utils.updatelogsinfos(Scp.log, "", minsenioritytransporter + "", "Minimun Seniority Transporter");
		}
		this.minsenioritytransporter = minsenioritytransporter;
	}

	public void setNoofcalendaryear(final Integer noofcalendaryear, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(noofcalendaryear)) {
				Scp.log = Utils.createnewlogs(Scp.log, noofcalendaryear + "", "No Of Calendar ON Year");
			}
		} else if (!Objects.isNull(noofcalendaryear) && !Objects.isNull(this.noofcalendaryear)) {
			Scp.log = Utils.updatelogsinfos(Scp.log, this.noofcalendaryear + "", noofcalendaryear + "",
					"No Of Calendar ON Year");
		} else if (!Objects.isNull(noofcalendaryear)) {
			Scp.log = Utils.updatelogsinfos(Scp.log, "", noofcalendaryear + "", "No Of Calendar ON Year");
		}
		this.noofcalendaryear = noofcalendaryear;
	}

	public void setScpid(final Integer scpid) {
		this.scpid = scpid;
	}

	public void setStatus(final Integer status) {
		this.status = status;
	}

	public void setTransporterid(final Integer transporterid, final String name, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(name)) {
				Scp.log = Utils.createnewlogs(Scp.log, name + "", "Transporter LEVEL");
			}
		} else if (!Objects.isNull(name)) {
			Scp.log = Utils.updatelogsinfos(Scp.log, "", name + "", "Transporter LEVEL");
		} else if (!Objects.isNull(name)) {
			Scp.log = Utils.updatelogsinfos(Scp.log, "", name + "", "Transporter LEVEL");
		}
		this.transporterid = transporterid;
	}

	public void setTransporterredlimit(final Integer transporterredlimit, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(transporterredlimit)) {
				Scp.log = Utils.createnewlogs(Scp.log, transporterredlimit + "", "Transporter Red Limit");
			}
		} else if (!Objects.isNull(transporterredlimit) && !Objects.isNull(this.transporterredlimit)) {
			Scp.log = Utils.updatelogsinfos(Scp.log, this.transporterredlimit + "", transporterredlimit + "",
					"Transporter Red Limit");
		} else if (!Objects.isNull(transporterredlimit)) {
			Scp.log = Utils.updatelogsinfos(Scp.log, "", transporterredlimit + "", "Transporter Red Limit");
		}
		this.transporterredlimit = transporterredlimit;
	}

	public void setUpdatedby(final Integer updatedby) {
		this.updatedby = updatedby;
	}

	public void setUpdatedon(final Date updatedon) {
		this.updatedon = updatedon;
	}

	@Override
	public String toString() {
		return "com.camtrack.entities.Scp[ scpid=" + this.scpid + " ]";
	}
}
