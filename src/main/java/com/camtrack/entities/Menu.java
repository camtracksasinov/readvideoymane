//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.entities;

import java.io.Serializable;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.camtrack.config.Utils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "menu")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Menu.findAll", query = "SELECT m FROM Menu m"),
		@NamedQuery(name = "Menu.findByMenuid", query = "SELECT m FROM Menu m WHERE m.menuid = :menuid"),
		@NamedQuery(name = "Menu.findByMenulevel", query = "SELECT m FROM Menu m WHERE m.menulevel = :menulevel"),
		@NamedQuery(name = "Menu.findByDescription", query = "SELECT m FROM Menu m WHERE m.description = :description"),
		@NamedQuery(name = "Menu.findByParentmenuid", query = "SELECT m FROM Menu m WHERE m.parentmenuid = :parentmenuid"),
		@NamedQuery(name = "Menu.findByMenuurl", query = "SELECT m FROM Menu m WHERE m.menuurl = :menuurl"),
		@NamedQuery(name = "Menu.findByMenuclass", query = "SELECT m FROM Menu m WHERE m.menuclass = :menuclass"),
		@NamedQuery(name = "Menu.findByMenuorder", query = "SELECT m FROM Menu m WHERE m.menuorder = :menuorder"),
		@NamedQuery(name = "Menu.findByMenuhome", query = "SELECT m FROM Menu m WHERE m.menuhome = :menuhome") })
public class Menu implements Serializable {
	public static String log;
	private static final long serialVersionUID = 1L;
	static {
		Menu.log = "";
	}

	public static String getLog() {
		return log;
	}

	public static void init(final boolean createorupdate, final User user) {
		Menu.log = Utils.EntetesLog(createorupdate, user.getUsername(), "Right On Menu");
	}

	public static void setLog(String log) {
		Menu.log = log;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "menuid", fetch = FetchType.LAZY)
	private List<Accessrights> accessrightsList;
	@JsonIgnore
	@OneToMany(mappedBy = "menuid", fetch = FetchType.LAZY)
	private List<Controllertomenu> controllertomenuList;
	@Basic(optional = false)
	@Column(name = "description")
	private String description;
	@Basic(optional = false)
	@Column(name = "menuclass")
	private String menuclass;
	@Basic(optional = false)
	@Column(name = "menuhome")
	private Integer menuhome;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "menuid")
	private Integer menuid;
	@Basic(optional = false)
	@Column(name = "menulevel")
	private Integer menulevel;

	@Basic(optional = false)
	@Column(name = "menuorder")
	private Integer menuorder;

	@JsonIgnore
	@JoinColumn(name = "menustatusid", referencedColumnName = "statusid")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Status menustatusid;

	@Basic(optional = false)
	@Column(name = "menuurl")
	private String menuurl;

	@Basic(optional = false)
	@Column(name = "parentmenuid")
	private Integer parentmenuid;

	public Menu() {
	}

	public Menu(final Integer menuid) {
		this.menuid = menuid;
	}

	public Menu(final Integer menuid, final Integer menulevel, final String description, final Integer parentmenuid,
			final String menuurl, final String menuclass, final Integer menuorder, final Integer menuhome) {
		this.menuid = menuid;
		this.menulevel = menulevel;
		this.description = description;
		this.parentmenuid = parentmenuid;
		this.menuurl = menuurl;
		this.menuclass = menuclass;
		this.menuorder = menuorder;
		this.menuhome = menuhome;
	}

	public Menu(final Integer menulevel, final String description, final Integer parentmenuid,
			final Status menustatusid, final Boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(description)) {
				Menu.log = Utils.createnewlogs(Menu.log, description + "", "Menu :");
			}
		} else if (!Objects.isNull(description) && !Objects.isNull(this.description)) {
			Menu.log = Utils.updatelogsinfos(Menu.log, this.description + "", description + "", "Affiliate name");
		} else if (!Objects.isNull(description)) {
			Menu.log = Utils.updatelogsinfos(Menu.log, "", description + "", "Affiliate name");
		}
		this.menulevel = menulevel;
		this.description = description;
		this.parentmenuid = parentmenuid;
		this.menustatusid = menustatusid;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Menu)) {
			return false;
		}
		final Menu other = (Menu) object;
		return (this.menuid != null || other.menuid == null)
				&& (this.menuid == null || this.menuid.equals(other.menuid));
	}

	@XmlTransient
	public List<Accessrights> getAccessrightsList() {
		return this.accessrightsList;
	}

	public List<Controllertomenu> getControllertomenuList() {
		return controllertomenuList;
	}

	public String getDescription() {
		return this.description;
	}

	public String getMenuclass() {
		return this.menuclass;
	}

	public Integer getMenuhome() {
		return this.menuhome;
	}

	public Integer getMenuid() {
		return this.menuid;
	}

	public Integer getMenulevel() {
		return this.menulevel;
	}

	public Integer getMenuorder() {
		return this.menuorder;
	}

	public Status getMenustatusid() {
		return this.menustatusid;
	}

	public String getMenuurl() {
		return this.menuurl;
	}

	public Integer getParentmenuid() {
		return this.parentmenuid;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += ((this.menuid != null) ? this.menuid.hashCode() : 0);
		return hash;
	}

	public void setAccessrightsList(final List<Accessrights> accessrightsList) {
		this.accessrightsList = accessrightsList;
	}

	public void setControllertomenuList(List<Controllertomenu> controllertomenuList) {
		this.controllertomenuList = controllertomenuList;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDescription(final String description, final boolean createorupdate) {
		if (createorupdate) {
			if (!Objects.isNull(description)) {
				Menu.log = Utils.createnewlogs(Menu.log, description + "", "Menu :");
			}
		} else if (!Objects.isNull(description) && !Objects.isNull(this.description)) {
			Menu.log = Utils.updatelogsinfos(Menu.log, this.description + "", description + "", "Affiliate name");
		} else if (!Objects.isNull(description)) {
			Menu.log = Utils.updatelogsinfos(Menu.log, "", description + "", "Affiliate name");
		}
		this.description = description;
	}

	public void setMenuclass(final String menuclass) {
		this.menuclass = menuclass;
	}

	public void setMenuhome(final Integer menuhome) {
		this.menuhome = menuhome;
	}

	public void setMenuid(final Integer menuid) {
		this.menuid = menuid;
	}

	public void setMenulevel(final Integer menulevel) {
		this.menulevel = menulevel;
	}

	public void setMenuorder(final Integer menuorder) {
		this.menuorder = menuorder;
	}

	public void setMenustatusid(final Status menustatusid) {
		this.menustatusid = menustatusid;
	}

	public void setMenuurl(final String menuurl) {
		this.menuurl = menuurl;
	}

	public void setParentmenuid(final Integer parentmenuid) {
		this.parentmenuid = parentmenuid;
	}

	@Override
	public String toString() {
		return "com.services.mintrans.entities.Menu[ menuid=" + this.menuid + " ]";
	}
}
