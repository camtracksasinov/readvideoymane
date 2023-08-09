//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.reports.service;

import java.util.List;

import com.camtrack.entities.User;
import com.camtrack.reports.bean.SafetyRanking;

public interface TopRankingServiceInterface {
	SafetyRanking safetyranking(final User user, final String listToString, final String listToString2,
			final String listToString3, final String listToString4, final String listToString5, final String datedebut,
			final String datefin, final String listToString6, final Boolean record, final Boolean alert,
			final Boolean alarm);

	List showTopRankingReport(final int customerid, final int affiliateid, final int transporterid, final int vehicleid,
			final int param, final String exceptionlevels, final String startDate, final String endDate);
}
