//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.camtrack.user.bean.EmailConfigBean;

@Repository("emailConfigDao")
@PropertySource({ "classpath:sqlqueries.properties" })
public class EmailConfigDaoImpl implements EmailConfigDaoInterface {
	@Autowired
	private Environment environment;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List getConfigByUserId(final int userid) {
		final String query = this.environment.getRequiredProperty("emailconfig.getbyuserid");
		return this.jdbcTemplate.queryForList(query, new Object[] { userid });
	}

	@Override
	public List getUsers(final int clientid, final int affiliateid, final int transporterid) {
		String query = "SELECT \"userid\",\"name\",\"emailflag\" FROM \"user\" where \"status\"=1";
		if (clientid > 0) {
			query = query + " and \"customerid\"=" + clientid;
		}
		if (affiliateid > 0) {
			query = query + " and \"affiliateid\"=" + affiliateid;
		}
		if (transporterid > 0) {
			query = query + " and \"transporterid\"=" + transporterid;
		}
		query += " order by \"name\" asc";
		return this.jdbcTemplate.queryForList(query);
	}

	@Override
	public List Parameters() {
		final String query = this.environment.getRequiredProperty("parametertype.map");
		return this.jdbcTemplate.queryForList(query);
	}

	@Override
	public int updateConfig(final EmailConfigBean emailConfigBean) {
		return 1;
	}
}
