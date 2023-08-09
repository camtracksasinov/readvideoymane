//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.decarbonisation.dao;

import java.util.List;
import java.util.Map;

import com.camtrack.bean.ResumeResults;
import com.camtrack.entities.User;

public interface DecarboInterface {
	List<Map<String, Object>> detaildecarbonisation(final User user, final String listToString,
			final String listToString2, final String listToString3, final String listToString4,
			final String listToString5, final String datedebut, final String datefin);

	List<Map<String, Object>> fuelconsum(User user, String listToString, String listToString2, String listToString3,
			String listToString4, String listToString5, String datedebut, String datefin, Integer period);

	List<Map<String, Object>> periodecarbo(final User user, final String listToString, final String listToString2,
			final String listToString3, final String listToString4, final String listToString5, final String datedebut,
			final String datefin, final Integer periodicite);

	ResumeResults summaryDecarbo(final User user, final String listToString, final String listToString2,
			final String listToString3, final String listToString4, final String listToString5, final String datedebut,
			final String datefin);
}
