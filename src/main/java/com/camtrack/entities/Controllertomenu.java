/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camtrack.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author alanic
 */
@Entity
@Table(name = "controllertomenu")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Controllertomenu.findAll", query = "SELECT c FROM Controllertomenu c"),
		@NamedQuery(name = "Controllertomenu.findByIds", query = "SELECT c FROM Controllertomenu c WHERE c.ids = :ids"),
		@NamedQuery(name = "Controllertomenu.findByControllerstring", query = "SELECT c FROM Controllertomenu c WHERE c.controllerstring = :controllerstring"),
		@NamedQuery(name = "Controllertomenu.findByCreate", query = "SELECT c FROM Controllertomenu c WHERE c.create = :create"),
		@NamedQuery(name = "Controllertomenu.findByOrder", query = "SELECT c FROM Controllertomenu c WHERE c.order = :order") })
public class Controllertomenu implements Serializable {

	private static final long serialVersionUID = 1L;
	@Column(name = "controllerstring")
	private String controllerstring;
	@Column(name = "create")
	@Temporal(TemporalType.TIMESTAMP)
	private Date create;
	@Id
	@Basic(optional = false)
	@Column(name = "ids")
	private Integer ids;
	@JoinColumn(name = "menuid", referencedColumnName = "menuid")
	@ManyToOne(fetch = FetchType.LAZY)
	private Menu menuid;
	@Column(name = "order")
	private Short order;

	public Controllertomenu() {
	}

	public Controllertomenu(Integer ids) {
		this.ids = ids;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Controllertomenu)) {
			return false;
		}
		Controllertomenu other = (Controllertomenu) object;
		if ((this.ids == null && other.ids != null) || (this.ids != null && !this.ids.equals(other.ids))) {
			return false;
		}
		return true;
	}

	public String getControllerstring() {
		return controllerstring;
	}

	public Date getCreate() {
		return create;
	}

	public Integer getIds() {
		return ids;
	}

	public Menu getMenuid() {
		return menuid;
	}

	public Short getOrder() {
		return order;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (ids != null ? ids.hashCode() : 0);
		return hash;
	}

	public void setControllerstring(String controllerstring) {
		this.controllerstring = controllerstring;
	}

	public void setCreate(Date create) {
		this.create = create;
	}

	public void setIds(Integer ids) {
		this.ids = ids;
	}

	public void setMenuid(Menu menuid) {
		this.menuid = menuid;
	}

	public void setOrder(Short order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "com.ymane.entities.Controllertomenu[ ids=" + ids + " ]";
	}

}
