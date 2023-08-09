//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.bean;

import java.io.Serializable;

public class Rightonmenu implements Serializable {
	private static final long serialVersionUID = 1L;
	boolean add;
	boolean delete;
	private Integer menuid;
	boolean update;
	boolean view;

	public Integer getMenuid() {
		return this.menuid;
	}

	public boolean isAdd() {
		return this.add;
	}

	public boolean isDelete() {
		return this.delete;
	}

	public boolean isUpdate() {
		return this.update;
	}

	public boolean isView() {
		return this.view;
	}

	public void setAdd(final boolean add) {
		this.add = add;
	}

	public void setDelete(final boolean delete) {
		this.delete = delete;
	}

	public void setMenuid(final Integer menuid) {
		this.menuid = menuid;
	}

	public void setUpdate(final boolean update) {
		this.update = update;
	}

	public void setView(final boolean view) {
		this.view = view;
	}
}
