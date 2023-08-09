//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.reports.service;

import java.util.List;

import com.camtrack.entities.User;

public interface WeeklyExceptionService {
	List showWeeklyExceptionReport(final User user, final String codeclient, final String codeafiliate,
			final String transporter, final String codevehicle, final String codedriver, final String datedebut,
			final String datefin, final String listidtypeexception, final Boolean record, final Boolean alert,
			final Boolean alarm) throws Exception;
}
