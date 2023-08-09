package com.camtrack.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ObjectDrivers implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer transporterid;
	private String driverids;

	public Integer getTransporterid() {
		return transporterid;
	}

	public void setTransporterid(Integer transporterid) {
		this.transporterid = transporterid;
	}

	public String getDriverids() {
		return driverids;
	}

	public void setDriverids(String driverids) {
		this.driverids = driverids;
	}

	public static List<Integer> getListFomString(String data) {
		List<Integer> result = new ArrayList<>();
		String[] results = data.split(",");
		for (String string : results) {
			result.add(Integer.valueOf(string));
		}

		return result;
	}
}
