//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.transporter.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.camtrack.transporter.bean.TransporterBean;

@Repository("transporterDao")
@PropertySource({ "classpath:sqlqueries.properties" })
public class TransporterDaoImpl implements TransporterDaoInterface {
	@Autowired
	private Environment environment;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int deactivateTransporter(final int transporterid) {
		final String query = this.environment.getRequiredProperty("transporter.delete");
		return this.jdbcTemplate.update(query, new Object[] { transporterid });
	}

	@Override
	public List fetchalldrivers() {
		final String query = this.environment.getRequiredProperty("driver.fechalldrivers");
		return this.jdbcTemplate.queryForList(query);
	}

	@Override
	public List fetchallTransporters() {
		final String query = "SELECT transporterid as ids,name as nm FROM transporter WHERE status = 1 order by name asc";
		final List transporters = this.jdbcTemplate.queryForList(query);
		return transporters;
	}

	@Override
	public List fetchallvehicles() {
		final String query = this.environment.getRequiredProperty("vehicle.fechallvehicle");
		return this.jdbcTemplate.queryForList(query);
	}

	@Override
	public List fetchTransporters(final String listcustomerid, final String listaffiliateid) {
		String query = "";
		if (!Objects.isNull(listaffiliateid) && !listaffiliateid.isEmpty()) {
			query = "SELECT transporterid,name,transportuniqueid FROM transporter WHERE status = 1 and affiliateid in ("
					+ listaffiliateid + ") order by name asc";
		} else if (!Objects.isNull(listcustomerid) && !listcustomerid.isEmpty()) {
			query = "SELECT transporter.transporterid,transporter.name,transporter.transportuniqueid FROM transporter join customeraffiliate on customeraffiliate.affiliateid=transporter.affiliateid WHERE transporter.status = 1 and customeraffiliate.customerid in ("
					+ listcustomerid + ") order by transporter.name asc";
		} else {
			query = "SELECT transporterid,name,transportuniqueid FROM transporter WHERE status = 1 order by name asc";
		}
		final List transporters = this.jdbcTemplate.queryForList(query);
		return transporters;
	}

	@Override
	public List getAllCustomerAffiliates(final int customerid) {
		final String query = this.environment.getRequiredProperty("affiliate.fetchallaffiliatenamessundercustomer");
		return this.jdbcTemplate.queryForList(query, new Object[] { customerid });
	}

	@Override
	public List getAllCustomers() {
		final String query = this.environment.getRequiredProperty("client.fetchallcustomernames");
		return this.jdbcTemplate.queryForList(query);
	}

	@Override
	public Map getTransporterById(final int transporterid) {
		final String query = this.environment.getRequiredProperty("transporter.fetchbyid");
		return this.jdbcTemplate.queryForMap(query, new Object[] { transporterid });
	}

	@Override
	public List getTransporterByListId(final String listtransporterid) {
		final String query = this.environment.getRequiredProperty("transporter.fetchbylistid");
		return this.jdbcTemplate.queryForList(query.replaceAll("ALANIC", listtransporterid));
	}

	@Override
	public int saveTransporter(final TransporterBean transporter) {
		try {
			int transporterid = 0;
			final String transporterquery = this.environment.getRequiredProperty("transporter.insert");
			final KeyHolder holder = new GeneratedKeyHolder();
			final int i = this.jdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(final Connection connection) throws SQLException {
					final PreparedStatement ps = connection.prepareStatement(transporterquery,
							new String[] { "transporterid" });
					ps.setInt(1, transporter.getAffiliateid());
					ps.setString(2, transporter.getName());
					ps.setString(5, transporter.getTransportuniqueid());
					return ps;
				}
			}, holder);
			transporterid = holder.getKey().intValue();
			return transporterid;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int updateTransporter(final TransporterBean transporter) {
		try {
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
