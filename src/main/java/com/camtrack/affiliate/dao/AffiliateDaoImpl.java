//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.affiliate.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.camtrack.affiliate.bean.AffiliateBean;

@Repository("affiliateDao")
@PropertySource({ "classpath:sqlqueries.properties" })
public class AffiliateDaoImpl implements AffiliateDaoInterface {
	@Autowired
	private Environment environment;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int deactivateAffiliate(final int affiliateid, final int userid) {
		final String transporterreferencecheck = this.environment
				.getRequiredProperty("affiliate.transporterreferencecheck");
		final long count = (int) this.jdbcTemplate.queryForObject(transporterreferencecheck, (Class) Integer.class,
				new Object[] { affiliateid });
		if (count > 0L) {
			return -1;
		}
		final String query = this.environment.getRequiredProperty("affiliate.delete");
		return this.jdbcTemplate.update(query, new Object[] { userid, new Date(), affiliateid });
	}

	@Override
	public List fetchAffiliatesbycostomer(final int customerid) {
		String query = "";
		if (customerid > 0) {
			query = "SELECT affiliateid as affid,name as nm FROM customeraffiliate affiliate  WHERE affiliate.status = 1 and affiliate.customerid="
					+ customerid + " order by affiliate.name asc";
		} else {
			query = "SELECT affiliateid as affid,name as nm FROM customeraffiliate WHERE status = 1 order by name asc";
		}
		return this.jdbcTemplate.queryForList(query);
	}

	@Override
	public List fetchAffiliatesbyListcostomer(final String listcustomerid) {
		String query = "";
		if (Objects.isNull(listcustomerid) || listcustomerid.isEmpty()) {
			query = "SELECT affiliateid,name,addressline1,email,telephone,afftimezone,customerid FROM customeraffiliate WHERE status = 1 order by name asc";
		} else {
			query = "SELECT affiliate.affiliateid,affiliate.name,affiliate.addressline1,affiliate.email,affiliate.telephone,affiliate.afftimezone,affiliate.customerid FROM customeraffiliate affiliate  WHERE affiliate.status = 1 and affiliate.customerid in ("
					+ listcustomerid + ") order by affiliate.name asc";
		}
		return this.jdbcTemplate.queryForList(query);
	}

	@Override
	public Map getAffiliateById(final int affiliateid) {
		final String query = this.environment.getRequiredProperty("affiliate.fetchbyid");
		return this.jdbcTemplate.queryForMap(query, new Object[] { affiliateid });
	}

	@Override
	public List getAffiliateByListId(final String listaffiliateid) {
		final String query = this.environment.getRequiredProperty("affiliate.fetchbylistid");
		return this.jdbcTemplate.queryForList(query.replaceAll("ALANIC", listaffiliateid));
	}

	@Override
	public int saveAffiliate(final AffiliateBean affiliate) {
		try {
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int updateAffiliate(final AffiliateBean affiliate) {
		try {
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
