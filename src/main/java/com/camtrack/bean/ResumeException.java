//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.bean;

import java.io.Serializable;
import java.util.List;

import com.camtrack.entities.NbrtypeExceptions;

public class ResumeException implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<NbrtypeExceptions> countinvalid;
	private String resumeexception;

	public List<NbrtypeExceptions> getCountinvalid() {
		return this.countinvalid;
	}

	public String getResumeexception() {
		return this.resumeexception;
	}

	public void setCountinvalid(final List<NbrtypeExceptions> countinvalid) {
		this.countinvalid = countinvalid;
	}

	public void setResumeexception(final String resumeexception) {
		this.resumeexception = resumeexception;
	}
}
