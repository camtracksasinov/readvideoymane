//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.rolemenu.bean;

import java.io.Serializable;
import java.util.Objects;

import com.camtrack.config.Utils;

public class RightsRole implements Serializable {
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return 1L;
	}

	private Boolean add;
	private Boolean delete;
	private String description;
	private Boolean edit;
	private String menuclass;
	private Integer menucodeid;
	private int menuhome;
	private int menulevel;
	private int menuorder;
	private String menuurl;
	private int parentmenucodeid;

	private Boolean view;

	public Boolean getAdd() {
		return this.add;
	}

	public Boolean getDelete() {
		return this.delete;
	}

	public String getDescription() {
		return this.description;
	}

	public Boolean getEdit() {
		return this.edit;
	}

	public String getMenuclass() {
		return Utils.StringEscape(this.menuclass);
	}

	public Integer getMenucodeid() {
		return this.menucodeid;
	}

	public int getMenuhome() {
		return this.menuhome;
	}

	public int getMenulevel() {
		return this.menulevel;
	}

	public int getMenuorder() {
		return this.menuorder;
	}

	public String getMenuurl() {
		return Utils.StringEscape(this.menuurl);
	}

	public int getParentmenucodeid() {
		return this.parentmenucodeid;
	}

	public Boolean getView() {
		return this.view;
	}

	public void setAdd(final Boolean add) {
		if (!Objects.isNull(add) && add) {
			this.add = add;
		} else {
			this.add = false;
		}
	}

	public void setDelete(final Boolean delete) {
		if (!Objects.isNull(delete) && delete) {
			this.delete = delete;
		} else {
			this.delete = false;
		}
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setEdit(final Boolean edit) {
		if (!Objects.isNull(edit) && edit) {
			this.edit = edit;
		} else {
			this.edit = false;
		}
	}

	public void setMenuclass(final String menuclass) {
		this.menuclass = menuclass;
	}

	public void setMenucodeid(final Integer menucodeid) {
		this.menucodeid = menucodeid;
	}

	public void setMenuhome(final int menuhome) {
		this.menuhome = menuhome;
	}

	public void setMenulevel(final int menulevel) {
		this.menulevel = menulevel;
	}

	public void setMenuorder(final int menuorder) {
		this.menuorder = menuorder;
	}

	public void setMenuurl(final String menuurl) {
		this.menuurl = menuurl;
	}

	public void setParentmenucodeid(final int parentmenucodeid) {
		this.parentmenucodeid = parentmenucodeid;
	}

	public void setView(final Boolean view) {
		if (!Objects.isNull(view) && view) {
			this.view = view;
		} else {
			this.view = false;
		}
	}
}
