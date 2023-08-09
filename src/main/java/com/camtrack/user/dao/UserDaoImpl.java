//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.camtrack.entities.User;
import com.camtrack.user.bean.DriverBean;
import com.camtrack.user.bean.UserBean;
import com.camtrack.user.bean.UserRoleBean;

@Repository("userDao")
@PropertySource({ "classpath:sqlqueries.properties" })
public class UserDaoImpl implements UserDaoInterface {
	@Autowired
	private Environment environment;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int changePassword(final String userId, final String newpassword) throws Exception {
		try {
			final String query = this.environment.getRequiredProperty("password.change");
			final int i = this.jdbcTemplate.update(query, new Object[] { newpassword, userId });
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public int deactivateUser(final int qid) {
		final String query = this.environment.getRequiredProperty("user.delete");
		return this.jdbcTemplate.update(query, new Object[] { qid });
	}

	@Override
	public int existemailid(final UserBean userBean) {
		try {
			final String query = this.environment.getRequiredProperty("existemailid.edit");
			return jdbcTemplate.queryForObject(query, new Object[] { userBean.getEmail() }, Integer.class);
		} catch (Exception e) {
			System.out.println(e);
			return 0;
		}
	}

	/**
	 * @Override public int existusername(final UserBean userBean) { final String
	 *           query = this.environment.getRequiredProperty("existusername.edit");
	 *           return jdbcTemplate.queryForObject(query, new Object[] {
	 *           userBean.getUserName() }, Integer.class); }
	 */

	@Override
	public List FetchAffilatesList(final User user) {
		final int role = 0;
		final int userid = user.getUserid();
		String query = "";
		if (role == 1) {
			query = "SELECT affiliateid, customerid,name FROM customeraffiliate WHERE status=1 order by name";
		} else if (role == 2) {
			query = "SELECT \"affiliate\".affiliateid, \"affiliate\".customerid,\"affiliate\".name FROM \"customeraffiliate\" as affiliate join \"user\" as usr on usr.customerid = affiliate.customerid WHERE usr.userid ="
					+ userid + " and \"affiliate\".status=1 order by \"affiliate\".name";
		} else if (role == 4) {
			query = "SELECT \"affiliate\".affiliateid,\"affiliate\".customerid,\"affiliate\".name FROM \"customeraffiliate\"  as affiliate  join \"user\" as usr on usr.affiliateid = affiliate.affiliateid WHERE  usr.userid ="
					+ userid + " and \"affiliate\".status=1 order by \"affiliate\".name";
		}
		final List userRoles = this.jdbcTemplate.queryForList(query);
		return userRoles;
	}

	@Override
	public List FetchClientList(final User user) {
		final int role = 0;
		final int userid = user.getUserid();
		String query = "";
		if (role == 1) {
			query = "SELECT customerid,name FROM customer where status=1 order by name";
		} else if (role == 2) {
			query = "SELECT  \"cus\".customerid,\"cus\".name FROM \"customer\" as cus join \"user\" as usr on usr.customerid = cus.customerid WHERE usr.userid ="
					+ userid + " and \"cus\".status=1 order by \"cus\".name";
		}
		final List userRoles = this.jdbcTemplate.queryForList(query);
		return userRoles;
	}

	@Override
	public List FetchTransporterList(final User user) {
		final int role = 0;
		final int userid = user.getUserid();
		String query = "";
		if (role == 1) {
			query = "SELECT  \"transporter\".transporterid,\"transporter\".affiliateid,\"transporter\".name,\"customeraffiliate\".customerid  FROM \"transporter\",\"customeraffiliate\" WHERE \"customeraffiliate\".affiliateid=\"transporter\".affiliateid and \"transporter\".status=1 order by name";
		} else if (role == 2) {
			query = "SELECT  \"transporter\".transporterid,\"transporter\".affiliateid,\"transporter\".name,\"customeraffiliate\".customerid  FROM \"transporter\",\"customeraffiliate\" WHERE \"customeraffiliate\".affiliateid=\"transporter\".affiliateid and \"transporter\".status=1 order by name";
		} else if (role == 4) {
			query = "SELECT  \"transporter\".transporterid,\"transporter\".affiliateid,\"transporter\".name,\"customeraffiliate\".customerid  FROM \"transporter\" join \"user\" on \"user\".affiliateid = \"transporter\".affiliateid join \"customeraffiliate\" on \"customeraffiliate\".affiliateid=\"transporter\".affiliateid   WHERE \"user\".userid ="
					+ userid + " and \"transporter\".status=1 order by name";
		} else if (role == 6) {
			query = "SELECT  \"transporter\".transporterid,\"transporter\".affiliateid,\"transporter\".name,\"customeraffiliate\".customerid  FROM \"transporter\" join \"user\" on \"user\".transporterid = \"transporter\".transporterid join \"customeraffiliate\" on \"customeraffiliate\".affiliateid=\"transporter\".affiliateid  WHERE  \"user\".userid ="
					+ userid + " and \"transporter\".status=1 order by name";
		}
		final List userRoles = this.jdbcTemplate.queryForList(query);
		return userRoles;
	}

	@Override
	public List fetchUserRoles(final String rolelevel) {
		final int role = Integer.parseInt(rolelevel);
		String query = "";
		if (role == 1) {
			query = "SELECT userroleid,name As Role,hierarchy,status FROM userrole WHERE status=1 and hierarchy >= 1 order by Role";
		} else if (role == 2) {
			query = "SELECT userroleid,name As Role,hierarchy,status FROM userrole WHERE status=1 and hierarchy > 2   order by Role";
		} else if (role == 4) {
			query = "SELECT userroleid,name As Role,hierarchy,status FROM userrole WHERE status=1 and hierarchy > 4   order by Role";
		} else if (role == 6) {
			query = "SELECT userroleid,name As Role,hierarchy,status FROM userrole WHERE status=1 and hierarchy=7 order by Role";
		}
		final List userRoles = this.jdbcTemplate.queryForList(query);
		return userRoles;
	}

	@Override
	public List fetchUsers(final User user) {
		List users = null;
		final int userid = user.getUserid();
		final int heirarchy = 0;
		final String queryforadmin = this.environment.getRequiredProperty("users.map");
		final String queryforcustomer = this.environment.getRequiredProperty("users.mapbycusid");
		final String queryforaffiliate = this.environment.getRequiredProperty("users.mapbyaffid");
		final String queryfortransporter = this.environment.getRequiredProperty("users.mapbytrnssportid");
		if (heirarchy == 1) {
			users = this.jdbcTemplate.queryForList(queryforadmin);
		} else if (heirarchy == 2 || heirarchy == 3) {
			final String query = "select \"user\".customerid from \"user\" where  \"user\".UserId=" + userid;
			final int customerid = (int) this.jdbcTemplate.queryForObject(query, (Class) Integer.class);
			users = this.jdbcTemplate.queryForList(queryforcustomer, new Object[] { customerid, heirarchy });
		} else if (heirarchy == 4 || heirarchy == 5) {
			final String query = "select \"user\".affiliateid from \"user\" where  \"user\".UserId=" + userid;
			final int affiliateid = (int) this.jdbcTemplate.queryForObject(query, (Class) Integer.class);
			users = this.jdbcTemplate.queryForList(queryforaffiliate, new Object[] { affiliateid, heirarchy });
		} else if (heirarchy == 6) {
			final String query = "select \"user\".transporterid from \"user\" where  \"user\".UserId=" + userid;
			final int transporterid = (int) this.jdbcTemplate.queryForObject(query, (Class) Integer.class);
			users = this.jdbcTemplate.queryForList(queryfortransporter, new Object[] { transporterid, heirarchy });
		}
		return users;
	}

	@Override
	public List getaffilatesByCusId(final String cusid) throws Exception {
		final String query = this.environment.getRequiredProperty("affiliatelist.bylistcusid");
		final List affiliatelist = this.jdbcTemplate.queryForList(query.replaceAll("ALANIC", cusid));
		return affiliatelist;
	}

	@Override
	public List getDriverDetailsByVehicleId(final int vehicleid) throws Exception {
		final String query = this.environment.getRequiredProperty("vehicle.getdriverid");
		final String query2 = this.environment.getRequiredProperty("driver.getdriverdetails");
		final List<DriverBean> driverdetails = new ArrayList<>();
		final List driverid = this.jdbcTemplate.queryForList(query, new Object[] { vehicleid });
		final Iterator itr = driverid.iterator();
		final int j = 0;
		while (itr.hasNext()) {
			final Map map = (Map) itr.next();
			final int id = Integer.parseInt(map.get("driverid").toString());
			final DriverBean driverBean = new DriverBean();
			System.out.println("id---->" + id);
			final List driver = this.jdbcTemplate.queryForList(query2, new Object[] { id });
			driverBean.setDriverid(id);
			driverBean.setVehicleid(vehicleid);
			final Map map2 = (Map) driver.get(0);
			driverBean.setDrivername(map2.get("name").toString());
			driverdetails.add(driverBean);
		}
		System.out.println(driverdetails);
		return driverdetails;
	}

	@Override
	public List gettransprotersbyAffiId(final String affiliateid) throws Exception {
		final String query = this.environment.getRequiredProperty("transporterlist.byaffiliatelistid");
		final List transporterlist = this.jdbcTemplate.queryForList(query.replaceAll("ALANIC", affiliateid));
		return transporterlist;
	}

	@Override
	public UserBean getUserById(final UserBean userBean) {
		final String query = this.environment.getRequiredProperty("user.getbyuserid");
		return (UserBean) this.jdbcTemplate.queryForObject(query, new Object[] { userBean.getUserid() },
				new RowMapper<Object>() {
					@Override
					public Object mapRow(ResultSet rs, int i) throws SQLException {
						userBean.setUserid(rs.getInt("userid"));
						userBean.setContacts(rs.getString("contacts"));
						userBean.setName(rs.getString("name"));
						userBean.setEmail(rs.getString("emailid"));
						// userBean.setUserName(rs.getString("username"));
						userBean.setRoleid(rs.getInt("reelrole"));
						try {
							userBean.setSexe(rs.getString("sexe").charAt(0));
						} catch (Exception ex) {
						}
						userBean.setMfa(rs.getBoolean("mfa"));
						return userBean;

					}

				});
	}

	@Override
	public UserRoleBean getUserRoleById(final UserRoleBean userRoleBean) throws Exception {
		final String query = this.environment.getRequiredProperty("userrole.getuserrolebyid");
		return (UserRoleBean) this.jdbcTemplate.queryForObject(query, new Object[] { userRoleBean.getUserroleid() },
				(RowMapper) new RowMapper<Object>() {
					@Override
					public Object mapRow(final ResultSet rs, final int i) throws SQLException {
						userRoleBean.setUserroleid(rs.getInt("userroleid"));
						userRoleBean.setName(rs.getString("name"));
						userRoleBean.setStatus(rs.getInt("status"));
						userRoleBean.setHierarchy(rs.getInt("hierarchy"));
						return userRoleBean;
					}
				});
	}

	@Override
	public List getUsersBySearchCriteria(final String clientid, final String affiliateid, final String transporterid)
			throws Exception {
		String query = "SELECT \"user\".UserId,\"user\".UserRoleId,\"user\".customerid,\"user\".affiliateid, \"user\".name as Name,\"user\".UserName,\"user\".Password,\"user\".transporterid,\"user\".status, \"userrole\".name AS UserRole,\"user\".emailid FROM \"user\",\"userrole\" where \"userrole\".UserRoleId=\"user\".UserRoleId and \"user\".\"status\"=1";
		if (!Objects.isNull(clientid) && !clientid.isEmpty()) {
			query = query + " and \"user\".\"customerid\" in (" + clientid + ")";
		}
		if (!Objects.isNull(affiliateid) && !affiliateid.isEmpty()) {
			query = query + " and \"user\". \"affiliateid\" in (" + affiliateid + ")";
		}
		if (!Objects.isNull(transporterid) && !transporterid.isEmpty()) {
			query = query + " and \"user\".\"transporterid\"(" + transporterid + ")";
		}
		query += " order by \"user\".\"name\" asc";
		return this.jdbcTemplate.queryForList(query);
	}

	@Override
	public int saveUser(final UserBean userBean) throws ParseException {
		try {
			return 1;
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int updateUser(final UserBean userBean) {
		try {
			final String query = this.environment.getRequiredProperty("user.update");
			return 1;
		} catch (Exception e) {
			System.out.println(e);
			return 0;
		}
	}

	@Override
	public int updateUserRole(final UserRoleBean userRoleBean) throws Exception {
		try {
			final String query = this.environment.getRequiredProperty("userrole.update");
			final int i = this.jdbcTemplate.update(query,
					new Object[] { userRoleBean.getName(), userRoleBean.getHierarchy(), userRoleBean.getUserroleid() });
			return 1;
		} catch (Exception e) {
			System.out.println(e);
			return 0;
		}
	}
}
