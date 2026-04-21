//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import com.camtrack.config.Utils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "accessrights")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Accessrights.findAll", query = "SELECT a FROM Accessrights a"),
		@NamedQuery(name = "Accessrights.findByAccessrightsid", query = "SELECT a FROM Accessrights a WHERE a.accessrightsid = :accessrightsid"),
		@NamedQuery(name = "Accessrights.findByCreatedon", query = "SELECT a FROM Accessrights a WHERE a.createdon = :createdon"),
		@NamedQuery(name = "Accessrights.findByUpdatedon", query = "SELECT a FROM Accessrights a WHERE a.updatedon = :updatedon"),
		@NamedQuery(name = "Accessrights.findByView", query = "SELECT a FROM Accessrights a WHERE a.view = :view"),
		@NamedQuery(name = "Accessrights.findByAdd", query = "SELECT a FROM Accessrights a WHERE a.add = :add"),
		@NamedQuery(name = "Accessrights.findByEdit", query = "SELECT a FROM Accessrights a WHERE a.edit = :edit"),
		@NamedQuery(name = "Accessrights.findByDelete", query = "SELECT a FROM Accessrights a WHERE a.delete = :delete") })
public class Accessrights implements Serializable {
	public static String log;
	private static final long serialVersionUID = 1L;
	static {
		Accessrights.log = "";
	}

	public static void init(final boolean createorupdate, final User user) {
		Accessrights.log = Utils.EntetesLog(createorupdate, user.getUsername(), "Access Right");
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("rightid")
	@Basic(optional = false)
	@Column(name = "accessrightsid")
	private Integer accessrightsid;
	@Column(name = "add")
	private Boolean add = false;
	@JsonIgnore
	@JoinColumn(name = "createdby", referencedColumnName = "userid")
	@ManyToOne(fetch = FetchType.LAZY)
	private User createdby;
	@JsonIgnore
	@Column(name = "createdon")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdon;
	@Column(name = "delete")
	private Boolean delete = false;
	@Column(name = "edit")
	private Boolean edit = false;
	@JoinColumn(name = "menuid", referencedColumnName = "menuid")
	@ManyToOne(fetch = FetchType.LAZY)
	private Menu menuid;
	@JsonIgnore
	@JoinColumn(name = "reelroleusers", referencedColumnName = "ids")
	@ManyToOne(fetch = FetchType.LAZY)
	private Reelroleusers reelroleusers;
	@JsonIgnore
	@JoinColumn(name = "updatedby", referencedColumnName = "userid")
	@ManyToOne(fetch = FetchType.LAZY)
	private User updatedby;
	@JsonIgnore
	@Column(name = "updatedon")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedon;

	@JsonIgnore
	@JoinColumn(name = "userroleid", referencedColumnName = "userroleid")
	@ManyToOne(fetch = FetchType.LAZY)
	private Userrole userroleid;

	@Column(name = "view")
	private Boolean view = false;

	public Accessrights() {
	}

	public Accessrights(final Integer accessrightsid) {
		this.accessrightsid = accessrightsid;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Accessrights)) {
			return false;
		}
		final Accessrights other = (Accessrights) object;
		return (this.accessrightsid != null || other.accessrightsid == null)
				&& (this.accessrightsid == null || this.accessrightsid.equals(other.accessrightsid));
	}

	public Integer getAccessrightsid() {
		return this.accessrightsid;
	}

	public Boolean getAdd() {
		return this.add;
	}

	public User getCreatedby() {
		return this.createdby;
	}

	public Date getCreatedon() {
		return this.createdon;
	}

	public Boolean getDelete() {
		return this.delete;
	}

	public Boolean getEdit() {
		return this.edit;
	}

	public Menu getMenuid() {
		return this.menuid;
	}

	public Reelroleusers getReelroleusers() {
		return this.reelroleusers;
	}

	public User getUpdatedby() {
		return this.updatedby;
	}

	public Date getUpdatedon() {
		return this.updatedon;
	}

	public Userrole getUserroleid() {
		return this.userroleid;
	}

	public Boolean getView() {
		return this.view;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.accessrightsid != null) ? this.accessrightsid.hashCode() : 0);
		return hash;
	}

	public void setAccessrightsid(final Integer accessrightsid) {
		this.accessrightsid = accessrightsid;
	}

	public void setAdd(final Boolean add, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(add)) {
				Accessrights.log = Utils.createnewlogs(Accessrights.log, this.view + "",
						"Access Level Create on Menu:");
			}
		} else if (!Objects.isNull(add) && !Objects.isNull(this.add)) {
			Accessrights.log = Utils.updatelogsinfos(Accessrights.log, this.add + "", add + "",
					"Access Level Create on Menu:");
		} else if (!Objects.isNull(add)) {
			Accessrights.log = Utils.updatelogsinfos(Accessrights.log, "", add + "", "Access Level Create on Menu:");
		}
		if (!Objects.isNull(add)) {
			this.add = add;
		}
	}

	public void setCreatedby(final User createdby) {
		this.createdby = createdby;
	}

	public void setCreatedon(final Date createdon) {
		this.createdon = createdon;
	}

	public void setDelete(final Boolean delete, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(delete)) {
				Accessrights.log = Utils.createnewlogs(Accessrights.log, delete + "", "Access Level Delete on Menu:");
			}
		} else if (!Objects.isNull(delete) && !Objects.isNull(this.delete)) {
			Accessrights.log = Utils.updatelogsinfos(Accessrights.log, this.delete + "", delete + "",
					"Access Level Delete on Menu:");
		} else if (!Objects.isNull(delete)) {
			Accessrights.log = Utils.updatelogsinfos(Accessrights.log, "", delete + "", "Access Level Delete on Menu:");
		}
		if (!Objects.isNull(delete)) {
			this.delete = delete;
		}
	}

	public void setEdit(final Boolean edit, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(edit)) {
				Accessrights.log = Utils.createnewlogs(Accessrights.log, edit + "", "Access Edit Delete on Menu:");
			}
		} else if (!Objects.isNull(edit) && !Objects.isNull(this.edit)) {
			Accessrights.log = Utils.updatelogsinfos(Accessrights.log, this.edit + "", edit + "",
					"Access Level Edit on Menu:");
		} else if (!Objects.isNull(edit)) {
			Accessrights.log = Utils.updatelogsinfos(Accessrights.log, "", edit + "", "Access Level Edit on Menu:");
		}
		if (!Objects.isNull(edit)) {
			this.edit = edit;
		}
	}

	public void setMenuid(final Menu menuid, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(menuid)) {
				Accessrights.log = Utils.createnewlogs(Accessrights.log, menuid.getDescription() + "", "Menu :");
			}
		} else if (!Objects.isNull(menuid) && !Objects.isNull(this.menuid)) {
			Accessrights.log = Utils.updatelogsinfos(Accessrights.log, this.menuid.getDescription() + "",
					menuid.getDescription() + "", "Menu :");
		} else if (!Objects.isNull(menuid)) {
			Accessrights.log = Utils.updatelogsinfos(Accessrights.log, "", menuid.getDescription() + "", "Menu :");
		}
		this.menuid = menuid;
	}

	public void setReelroleusers(final Reelroleusers reelroleusers, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(reelroleusers)) {
				Accessrights.log = Utils.createnewlogs(Accessrights.log, reelroleusers.getRolenames() + "", "Role :");
			}
		} else if (!Objects.isNull(reelroleusers) && !Objects.isNull(this.reelroleusers)) {
			Accessrights.log = Utils.updatelogsinfos(Accessrights.log, this.reelroleusers.getRolenames() + "",
					reelroleusers.getRolenames() + "", "Role :");
		} else if (!Objects.isNull(reelroleusers)) {
			Accessrights.log = Utils.updatelogsinfos(Accessrights.log, "", reelroleusers.getRolenames() + "", "Role :");
		}
		this.reelroleusers = reelroleusers;
	}

	public void setUpdatedby(final User updatedby) {
		this.updatedby = updatedby;
	}

	public void setUpdatedon(final Date updatedon) {
		this.updatedon = updatedon;
	}

	public void setUserroleid(final Userrole userroleid) {
		this.userroleid = userroleid;
	}

	public void setView(final Boolean view, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(view)) {
				Accessrights.log = Utils.createnewlogs(Accessrights.log, view + "", "Access Level View on Menu:");
			}
		} else if (!Objects.isNull(view) && !Objects.isNull(this.view)) {
			Accessrights.log = Utils.updatelogsinfos(Accessrights.log, this.view + "", view + "",
					"Access Level View on Menu:");
		} else if (!Objects.isNull(view)) {
			Accessrights.log = Utils.updatelogsinfos(Accessrights.log, "", view + "", "Access Level View on Menu:");
		}
		if (!Objects.isNull(view)) {
			this.view = view;
		}
	}

	@Override
	public String toString() {
		return "com.camtrack.entities.Accessrights[ accessrightsid=" + this.accessrightsid + " ]";
	}
}
