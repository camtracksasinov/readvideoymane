//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.manualsubtractionandrecovery.service;

import java.util.List;
import java.util.Map;

import com.camtrack.entities.User;
import com.camtrack.manualsubtractionandrecovery.bean.Manualsubnrec;
import com.camtrack.manualsubtractionandrecovery.bean.Manualsubnreclist;
import com.camtrack.manualsubtractionandrecovery.bean.RecoveryBean;

public interface ManualSubtractionAndRecoveryServiceInterface {
	int deactivateRecord(final int customerid, final int affid, final int transid, final int driverid,
			final String occdate) throws Exception;

	List<RecoveryBean> fetchMyList(final User user, final String codeclient, final String codeafiliate,
			final String transporter, final String codedriver, final String fromdate, final String todate)
			throws Exception;

	int fetchresetfreqbyaffiliateid(final int affiliateid) throws Exception;

	List getRawStaticList(final int clientid, final int affiliateid, final int transporterid, final int driverid,
			final int startyear, final int endyear, final int startmonth, final int endmonth) throws Exception;

	Map recoveryParamMap(final int clientid, final int affiliateid, final int transid, final int driverid,
			final String occdate) throws Exception;

	int saveManualSubtractionAndRecovery(final User user, final Manualsubnrec manualsubnrecBean) throws Exception;

	List scpobcparameters();

	List scprecovery();

	List viewRawstatisticReeport(final int clientid, final int affiliateid, final int transporterid, final int driverid,
			final int startyear, final int endyear, final int startmonth, final int endmonth) throws Exception;

	List visualparameter();

	Map visualParamMap(final int clientid, final int affiliateid, final int transid, final int driverid,
			final String occdate) throws Exception;

	int saveManualSubtractionAndRecoverylist(User user, Manualsubnreclist manualsubnrecBean);

}
