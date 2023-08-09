//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.dashboard.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.camtrack.bean.ResumeResult;
import com.camtrack.bean.ResumeResults;
import com.camtrack.entities.User;
import com.camtrack.reports.bean.TotalDistances;
import com.camtrack.reports.bean.TrendBean;

public interface DashboardServiceInterface {
	ResponseEntity<?> activedriverandvehicle(final User user, final String listToString, final String listToString2,
			final String listToString3, final String listToString4, final String listToString5, final String datedebut,
			final String datefin);

	ResumeResult actvehordrivpersearch(final User user, final String listToString, final String listToString2,
			final String listToString3, final String listToString4, final String listToString5, final String datedebut,
			final String datefin);

	Map checkPermissions(final String userroleid, final String menuId);

	List fetchAllParentMenus(final String roleid) throws Exception;

	List fetchVehicleByTransporter(final String transid);

	ResumeResult resumeactivedrive(final User user, final String listToString, final String listToString2,
			final String listToString3, final String listToString4, final String listToString5, final String datedebut,
			final String datefin);

	ResumeResults resumelistdriveandvehicle(final User user, final String listToString, final String listToString2,
			final String listToString3, final String listToString4, final String listToString5);

	ResumeResults resumeworktime(final User user, final String listToString, final String listToString2,
			final String listToString3, final String listToString4, final String listToString5, final String datedebut,
			final String datefin);

	ResumeResult resumexception(final User user, final String listToString, final String listToString2,
			final String listToString3, final String listToString4, final String listToString5, final String datedebut,
			final String datefin, final String listToString6, final Boolean record, final Boolean alert,
			final Boolean alarm);

	ResumeResult resumexception100km(final User user, final String listToString, final String listToString2,
			final String listToString3, final String listToString4, final String listToString5, final String datedebut,
			final String datefin, final String listToString6, final Boolean record, final Boolean alert,
			final Boolean alarm);

	TotalDistances trendistance(final User user, final String listToString, final String listToString2,
			final String listToString3, final String listToString4, final String listToString5, final String datedebut,
			final String datefin, final Integer frequencetreands);

	List<TrendBean> trendresumexception(final User user, final String listToString, final String listToString2,
			final String listToString3, final String listToString4, final String listToString5, final String datedebut,
			final String datefin, final String listToString6, final Boolean record, final Boolean alert,
			final Boolean alarm, final Integer frequencetreands);

	List<TrendBean> trendresumexception100km(final User user, final String listToString, final String listToString2,
			final String listToString3, final String listToString4, final String listToString5, final String datedebut,
			final String datefin, final String listToString6, final Boolean record, final Boolean alert,
			final Boolean alarm, final Integer frequencetreands);

	ResponseEntity<?> worktime(final User user, final String listToString, final String listToString2,
			final String listToString3, final String listToString4, final String listToString5, final String datedebut,
			final String datefin);
}
