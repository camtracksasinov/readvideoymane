//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.bean;

import java.io.Serializable;
import java.util.List;

public class SmallEmailConfigBean implements Serializable {
	private static final long serialVersionUID = 1L;
	Integer elementsid;
	private List<Levelbean> levelbeam;
	private Integer typelementsid;

	public Integer getElementsid() {
		return this.elementsid;
	}

	public List<Levelbean> getLevelbeam() {
		return this.levelbeam;
	}

	public Integer getTypelementsid() {
		return this.typelementsid;
	}

	public void setElementsid(final Integer elementsid) {
		this.elementsid = elementsid;
	}

	public void setLevelbeam(final List<Levelbean> levelbeam) {
		this.levelbeam = levelbeam;
	}

	public void setTypelementsid(final Integer typelementsid) {
		this.typelementsid = typelementsid;
	}
}
