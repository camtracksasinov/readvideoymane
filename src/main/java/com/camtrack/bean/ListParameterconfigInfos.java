//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.bean;

import java.io.Serializable;
import java.util.List;

public class ListParameterconfigInfos implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<ParameterconfigInfos> listparams;

	public List<ParameterconfigInfos> getListparams() {
		return listparams;
	}

	public void setListparams(List<ParameterconfigInfos> listparams) {
		this.listparams = listparams;
	}

}
