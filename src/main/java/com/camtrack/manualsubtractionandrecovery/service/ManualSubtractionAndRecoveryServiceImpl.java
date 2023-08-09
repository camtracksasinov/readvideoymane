//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.manualsubtractionandrecovery.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.camtrack.entities.User;
import com.camtrack.manualsubtractionandrecovery.bean.Manualsubnrec;
import com.camtrack.manualsubtractionandrecovery.bean.Manualsubnreclist;
import com.camtrack.manualsubtractionandrecovery.dao.ManualSubtractionAndRecoveryDaoInterface;

@Service("ManualRecoveryService")
@CacheConfig(cacheNames = "manualsubstraction")
public class ManualSubtractionAndRecoveryServiceImpl implements ManualSubtractionAndRecoveryServiceInterface {
	@Autowired
	private Environment environment;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	ManualSubtractionAndRecoveryDaoInterface manualRecoveryDaoInterface;

	@Override
	public int deactivateRecord(final int customerid, final int affid, final int transid, final int driverid,
			final String occdate) throws Exception {
		return this.manualRecoveryDaoInterface.deactivateRecord(customerid, affid, transid, driverid, occdate);
	}

	@Override
	public List fetchMyList(final User user, final String codeclient, final String codeafiliate,
			final String transporter, final String codedriver, final String fromdate, final String todate)
			throws Exception {
		return this.manualRecoveryDaoInterface.fetchMyList(user, codeclient, codeafiliate, transporter, codedriver,
				fromdate, todate);
	}

	@Override
	public int fetchresetfreqbyaffiliateid(final int affiliateid) throws Exception {
		return this.manualRecoveryDaoInterface.fetchresetfreqbyaffiliateid(affiliateid);
	}

	@Override
	public List getRawStaticList(final int clientid, final int affiliateid, final int transporterid, final int driverid,
			final int startyear, final int endyear, final int startmonth, final int endmonth) throws Exception {
		return this.manualRecoveryDaoInterface.getRawStaticList(clientid, affiliateid, transporterid, driverid,
				startyear, endyear, startmonth, endmonth);
	}

	@Override
	public Map recoveryParamMap(final int clientid, final int affiliateid, final int transid, final int driverid,
			final String occdate) throws Exception {
		return this.manualRecoveryDaoInterface.recoveryParamMap(clientid, affiliateid, transid, driverid, occdate);
	}

	@Override
	public int saveManualSubtractionAndRecovery(final User user, final Manualsubnrec manualsubnrecBean)
			throws Exception {
		return this.manualRecoveryDaoInterface.saveManualSubtractionAndRecovery(user, manualsubnrecBean);
	}

	@Override
	@Cacheable("scpobcparams")
	public List scpobcparameters() {
		final String query = this.environment.getRequiredProperty("scp.newobcparamlist");
		return this.jdbcTemplate.queryForList(query);
	}

	@Override
	@Cacheable("scprecovery")
	public List scprecovery() {
		final String query = this.environment.getRequiredProperty("scp.newrecoverylist");
		return this.jdbcTemplate.queryForList(query);
	}

	@Override
	public List viewRawstatisticReeport(final int clientid, final int affiliateid, final int transporterid,
			final int driverid, final int startyear, final int endyear, final int startmonth, final int endmonth)
			throws Exception {
		return this.manualRecoveryDaoInterface.viewRawstatisticReeport(clientid, affiliateid, transporterid, driverid,
				startyear, endyear, startmonth, endmonth);
	}

	@Override
	@Cacheable("visualparameter")
	public List visualparameter() {
		final String query = this.environment.getRequiredProperty("scp.newvisualparamlist");
		return this.jdbcTemplate.queryForList(query);
	}

	@Override
	public Map visualParamMap(final int clientid, final int affiliateid, final int transid, final int driverid,
			final String occdate) throws Exception {
		return this.manualRecoveryDaoInterface.visualParamMap(clientid, affiliateid, transid, driverid, occdate);
	}

	@Override
	public int saveManualSubtractionAndRecoverylist(User user, Manualsubnreclist manualsubnrecBean) {
		// TODO Auto-generated method stub
		return this.manualRecoveryDaoInterface.saveManualSubtractionAndRecoverylist(user, manualsubnrecBean);
	}

}
