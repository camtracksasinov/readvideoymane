//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.application.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.camtrack.application.bean.ApplicationBean;

@Repository("ApplicationDao")
@PropertySource({ "classpath:sqlqueries.properties" })
public class ApplicationDaoImpl implements ApplicationDaoInterface {
	@Autowired
	private Environment environment;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int deactivateApplicationMapping(final int clientaffapplicationid) throws Exception {
		final String query = this.environment.getRequiredProperty("application.delete");
		return this.jdbcTemplate.update(query, new Object[] { clientaffapplicationid });
	}

	@Override
	public List fetchAllApplications() throws Exception {
		final String query = this.environment.getRequiredProperty("application.list");
		return this.jdbcTemplate.queryForList(query);
	}

	@Override
	public List loadClientApplicationMap(final int customerid, final int affiliateid, final int applicationid)
			throws Exception {
		String query = "select caf.clientaffapplicationid,c.customerId,c.name as clientName,ca.affiliateid,ca.name as affiliateName,a.applicationid,a.name as applicationName from clientaffapplication caf join customer c on caf.clientid = c.customerid join customeraffiliate ca on caf.affiliateid = ca.affiliateid join application a on caf.applicationid = a.applicationid where caf.status_id = 1";
		if (customerid > 0) {
			query = query + " and caf.clientid = " + customerid;
		}
		if (affiliateid > 0) {
			query = query + " and caf.affiliateid = " + affiliateid;
		}
		if (applicationid > 0) {
			query = query + " and caf.applicationid = " + applicationid;
		}
		System.out.println(query);
		return this.jdbcTemplate.queryForList(query);
	}

	@Override
	public int mapClientApplication(final ApplicationBean applicationBean) throws Exception {
		final int client_id = applicationBean.getClientid();
		final String query_check = this.environment.getRequiredProperty("application.checkmap");
		final int clientaffappid = applicationBean.getClientaffapplicationid();
		if (clientaffappid <= 0) {
			final int[] clientaff = applicationBean.getClientaffiliateid();
			final int[] app_id = applicationBean.getApplnid();
			for (final int element : clientaff) {
				for (final int element2 : app_id) {
					final int count = (int) this.jdbcTemplate.queryForObject(query_check, (Class) Integer.class,
							new Object[] { client_id, element, element2 });
					if (count <= 0) {
						final String query = this.environment.getRequiredProperty("application.map");
						this.jdbcTemplate.update(query, new Object[] { client_id, element, element2 });
					}
				}
			}
			return 1;
		}
		final int[] clientaff = applicationBean.getClientaffiliateid();
		final int[] app_id = applicationBean.getApplnid();
		final int count2 = (int) this.jdbcTemplate.queryForObject(query_check, (Class) Integer.class,
				new Object[] { client_id, clientaff[0], app_id[0] });
		if (count2 <= 0) {
			final String query2 = this.environment.getRequiredProperty("application.mapupdate");
			this.jdbcTemplate.update(query2, new Object[] { client_id, clientaff[0], app_id[0], clientaffappid });
			return 1;
		}
		return 0;
	}
}
