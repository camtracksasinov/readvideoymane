package com.camtrack.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DetailsExceptions implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Map<String, Object>> data = new ArrayList<>();
	private Integer size = 0;
	private Boolean hasmoreElements = false;

	public List<Map<String, Object>> getData() {
		return data;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public void setData(List<Map<String, Object>> data) {
		this.data = data;
	}

	public Boolean getHasmoreElements() {
		return hasmoreElements;
	}

	public void setHasmoreElements(Boolean hasmoreElements) {
		this.hasmoreElements = hasmoreElements;
	}

}
