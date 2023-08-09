//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.configuartion.dao;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.camtrack.configuartion.bean.ConfigurationBean;
import com.camtrack.entities.User;

public interface ConfigurationDaoInterface {
	List CcAutSubSpeedMap(final int clientid, final int affiliateid);

	int deactivateConfig(final int qid, final int userid);

	List getAffliates(final int clientid);

	List getAffParameterMap(final int clientid, final int affiliateid);

	List getClientMap();

	Map getParameterById(final int clientid, final int affiliateid, final int transid, final int parametertypeid);

	Map getParameterById2(final int clientid, final int affiliateid, final int transid, final int parametertypeid);

	List getParameterMap();

	List manualSubtractionMap(final int clientid, final int affiliateid);

	boolean monthOverLap(final int frommonth, final int tomonth, final int configid, final int clientid,
			final int affiliateid);

	List obcParamMap();

	List recoveryParam(final int clientid, final int affiliateid);

	ResponseEntity<?> saveConfiguration(final ConfigurationBean configurationBean, final User user)
			throws ParseException;

	List visualParamMap(final int clientid, final int affiliateid);
}
