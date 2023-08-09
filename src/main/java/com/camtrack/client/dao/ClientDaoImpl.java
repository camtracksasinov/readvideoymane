//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.client.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.camtrack.client.bean.ClientBean;
import com.camtrack.config.AppConstants;

@Repository("clientDao")
@PropertySource({ "classpath:sqlqueries.properties" })
public class ClientDaoImpl implements ClientDaoInterface {
	@Autowired
	private Environment environment;
	private byte[] img;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int deactivateClient(final int clientid, final int userid) {
		final String affiliatereferencecheck = this.environment.getRequiredProperty("client.affiliatereferencecheck");
		final long count = (long) this.jdbcTemplate.queryForObject(affiliatereferencecheck, (Class) Long.class,
				new Object[] { clientid });
		if (count > 0L) {
			return -1;
		}
		final String query = this.environment.getRequiredProperty("client.delete");
		return this.jdbcTemplate.update(query, new Object[] { userid, new Date(), clientid });
	}

	@Override
	public List fetchClients(final int roleId, final int userId) {
		List clients;
		if (roleId == AppConstants.ROLE_SUPER_ADMIN) {
			final String query = this.environment.getRequiredProperty("client.fetchall");
			clients = this.jdbcTemplate.queryForList(query);
		} else {
			final String query = this.environment.getRequiredProperty("client.fetchallbyuser");
			clients = this.jdbcTemplate.queryForList(query, new Object[] { userId });
		}
		return clients;
	}

	@Override
	public List getAllCitiesOfState(final int stateid) {
		final String query = this.environment.getRequiredProperty("city.fetchallcitiesofstate");
		return this.jdbcTemplate.queryForList(query, new Object[] { stateid });
	}

	@Override
	public List getAllCounties() {
		final String query = this.environment.getRequiredProperty("country.fetchallcountries");
		return this.jdbcTemplate.queryForList(query);
	}

	@Override
	public List getAllLanguages() {
		final String query = this.environment.getRequiredProperty("language.fetchalllanguages");
		return this.jdbcTemplate.queryForList(query);
	}

	@Override
	public List getAllStatesOfCountry(final int countryid) {
		final String query = this.environment.getRequiredProperty("state.fetchallstatesofcountry");
		return this.jdbcTemplate.queryForList(query, new Object[] { countryid });
	}

	@Override
	public Map getClientById(final int clientid) {
		final String query = this.environment.getRequiredProperty("client.fetchbyid");
		return this.jdbcTemplate.queryForMap(query, new Object[] { clientid });
	}

	@Override
	public List getClientByListId(final String listclientid) {
		final String query = this.environment.getRequiredProperty("client.fetchbylistid");
		return this.jdbcTemplate.queryForList(query.replaceAll("ALANIC", listclientid));
	}

	@Override
	public int saveClient(final ClientBean client) {
		try {
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int updateClient(final ClientBean client) throws Exception {
		try {
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public int updateLogoUrl(final int customerid, final String logourl) {
		final String query = this.environment.getRequiredProperty("client.updatelogourl");
		final int count = this.jdbcTemplate.update(query, new Object[] { logourl, customerid });
		return count;
	}
}
