//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ResumeResult implements Serializable {
	private static final long serialVersionUID = 1L;
	List<Map<String, Object>> invalidresult;
	List<Map<String, Object>> result;
	private Integer typeInfos;

	public ResumeResult(final List<Map<String, Object>> result, final List<Map<String, Object>> invalidresult,
			final Integer typeInfos) {
		this.result = result;
		this.typeInfos = typeInfos;
		this.invalidresult = invalidresult;
	}

	public List<Map<String, Object>> getResult() {
		return this.result;
	}

	public Integer getTypeInfos() {
		return this.typeInfos;
	}

	public void setResult(final List<Map<String, Object>> result) {
		this.result = result;
	}

	public void setTypeInfos(final Integer typeInfos) {
		this.typeInfos = typeInfos;
	}

	public List<Map<String, Object>> getInvalidresult() {
		return invalidresult;
	}

	public void setInvalidresult(List<Map<String, Object>> invalidresult) {
		this.invalidresult = invalidresult;
	}

}
