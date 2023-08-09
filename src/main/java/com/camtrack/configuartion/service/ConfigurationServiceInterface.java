//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.configuartion.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.camtrack.bean.CreateParameterconfigInfos;
import com.camtrack.bean.ParameterconfigInfos;
import com.camtrack.configuartion.bean.ConfigurationBean;
import com.camtrack.entities.User;

public interface ConfigurationServiceInterface {
	List CcAutSubSpeedMap(final int clientid, final int affiliateid);

	ResponseEntity<?> createparameterconfig(final CreateParameterconfigInfos config, final User user);

	Integer deactivateConfig(final Long paramconfigid, final User user);

	ResponseEntity<?> fetchlistparams(final User user);

	List getAffliates(final int clientid);

	List getAffParameterMap(final int clientid, final int affiliateid);

	List getClientMap();

	ResponseEntity<?> getListParameterById(final Integer customerid, final Integer affid, final Integer transporterid,
			final Integer vehicleid, final Integer driverid);

	Map getParameterById(final int clientid, final int affiliateid, final int transporterid, final int parametertypeid);

	List getParameterMap();

	ResponseEntity<?> listlevelalert(final User user);

	List manualSubtractionMap(final int clientid, final int affiliateid);

	boolean monthOverLap(final int frommonth, final int tomonth, final int configid, final int clientid,
			final int affiliateid);

	List obcParamMap();

	List recoveryParam(final int clientid, final int affiliateid);

	ResponseEntity<?> saveConfiguration(final ConfigurationBean configurationBean, final User user)
			throws ParseException;

	ResponseEntity<?> updateparameterconfig(final ParameterconfigInfos config, final User user);

	List visualParamMap(final int clientid, final int affiliateid);
}
