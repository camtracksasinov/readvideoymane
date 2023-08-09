//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.reports.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.camtrack.bean.AutomaticreportBean;
import com.camtrack.bean.ListAllIdBean;
import com.camtrack.entities.User;

public interface AutomaticReportService {
	List<Map<String, Object>> activelistallconfigs(final String nextdate, final String enddates,
			final String smallrequest);

	ResponseEntity<?> autoreports(final User user, final AutomaticreportBean autobean, final String subject,
			final String emailist, final Integer idreportname, final Short idfrequence, final Short formatreport,
			final Short timerange, final String hourofrequest, final Short dayofweekordayofmonth, final Date date,
			final String bodymail);

	List<Map<String, Object>> listformatrepor();

	List<Map<String, Object>> listfrequence();

	List<Map<String, Object>> listimerange();

	List<Map<String, Object>> listreportnames();

	List<Map<String, Object>> newactivelistallconfigs(final ListAllIdBean filter, final String startdate,
			final String endate, final User user);

	ResponseEntity<?> updatereportstatus(final Long[] listconfigreportid);
}
