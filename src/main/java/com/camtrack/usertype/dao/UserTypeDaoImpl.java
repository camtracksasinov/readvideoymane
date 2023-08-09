//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.usertype.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.camtrack.usertype.bean.UserTypeBean;

@Repository("usertypeDao")
@PropertySource({ "classpath:sqlqueries.properties" })
public class UserTypeDaoImpl implements UserTypeDaoInterface {
	@Autowired
	private Environment environment;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int deactivateUserType(final int item) {
		final String query = this.environment.getRequiredProperty("usertype.delete");
		return this.jdbcTemplate.update(query, new Object[] { item });
	}

	@Override
	public int deleteUserTypeByIds(final String l) {
		final String query = this.environment.getRequiredProperty("usertype.delete");
		return this.jdbcTemplate.update(query, new Object[] { l });
	}

	@Override
	public int existUserTypeName(final String userTypeName, final String dumusertype) {
		final String query = this.environment.getRequiredProperty("existusertypename.edit");
		return (int) this.jdbcTemplate.queryForObject(query, new Object[] { userTypeName, dumusertype },
				(RowMapper) new RowMapper<Object>() {
					@Override
					public Object mapRow(final ResultSet rs, final int i) throws SQLException {
						final int count = rs.getInt(1);
						return count;
					}
				});
	}

	@Override
	public UserTypeBean getUserTypeById(final UserTypeBean usertypebean) {
		final String query = this.environment.getRequiredProperty("usertype.getbyusertypeid");
		return (UserTypeBean) this.jdbcTemplate.queryForObject(query, new Object[] { usertypebean.getUserTypeId() },
				(RowMapper) new RowMapper<Object>() {
					@Override
					public Object mapRow(final ResultSet rs, final int i) throws SQLException {
						usertypebean.setUserTypeId(rs.getInt("UserTypeId"));
						usertypebean.setUserTypeName(rs.getString("UserTypeName"));
						usertypebean.setCreatedOn(rs.getString("CreatedOn"));
						usertypebean.setCreatedBy(rs.getString("CreatedBy"));
						usertypebean.setUpdatedOn(rs.getString("UpdatedOn"));
						usertypebean.setUpdatedBy(rs.getString("UpdatedBy"));
						usertypebean.setUserTypeStatus(rs.getInt("UserTypeStatus"));
						return usertypebean;
					}
				});
	}

	@Override
	public List loadUserTypes() {
		final String query = this.environment.getRequiredProperty("usertype.map");
		final List usertype = this.jdbcTemplate.queryForList(query);
		return usertype;
	}

	@Override
	public int saveUserType(final UserTypeBean usertype) {
		try {
			final String userquery = this.environment.getRequiredProperty("usertype.insert");
			usertype.setUserTypeStatus(1);
			this.jdbcTemplate.update(userquery, new Object[] { usertype.getUserTypeName(), new Date(),
					usertype.getCreatedBy(), new Date(), usertype.getUpdatedBy(), usertype.getUserTypeStatus() });
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int updateUserType(final UserTypeBean usertype) {
		try {
			final String query = this.environment.getRequiredProperty("usertype.update");
			final int i = this.jdbcTemplate.update(query,
					new Object[] { usertype.getUserTypeName(), new Date(), usertype.getCreatedBy(), new Date(),
							usertype.getUpdatedBy(), usertype.getUserTypeStatus(), usertype.getUserTypeId() });
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}
}
