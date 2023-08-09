//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.dashboard.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("dashboardDao")
@PropertySource({ "classpath:sqlqueries.properties" })
public class DashboardDaoImpl implements DashboardDaoInterface {
	@Autowired
	private Environment environment;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Map checkPermissions(final String userroleid, final String menuURL) {
		if (menuURL.equals("#/dashboard")) {
			final Map mp = new HashMap();
			Map map1 = new HashMap();
			Map map2 = new HashMap();
			int flag = 0;
			mp.put("roadriskanalysis", false);
			final List<Map<String, Object>> menuid = this.jdbcTemplate
					.queryForList("SELECT menuid FROM \"menu\" WHERE menuurl = '" + menuURL + "'");
			if (menuid.size() > 0) {
				final String query = this.environment.getRequiredProperty("menu.checkpermissions");
				map1 = this.jdbcTemplate.queryForMap(query,
						new Object[] { Integer.parseInt(userroleid), menuid.get(0).get("menuid") });
				map2 = this.jdbcTemplate.queryForMap(query,
						new Object[] { Integer.parseInt(userroleid), menuid.get(1).get("menuid") });
			}
			for (int i = 0; i < map2.size(); ++i) {
				final ArrayList<Object> a = new ArrayList<Object>(map2.keySet());
				final Object o = a.get(i);
				if (map2.get(o).equals(true)) {
					flag = 1;
				} else {
					flag = 0;
				}
			}
			if (flag == 1) {
				mp.put("roadriskanalysis", true);
			}
			mp.putAll(map1);
			return mp;
		}
		final List<Map<String, Object>> menuid2 = this.jdbcTemplate
				.queryForList("SELECT menuid FROM \"menu\" WHERE menuurl = '" + menuURL + "'");
		if (menuid2.size() > 0) {
			final String query2 = this.environment.getRequiredProperty("menu.checkpermissions");
			return this.jdbcTemplate.queryForMap(query2,
					new Object[] { Integer.parseInt(userroleid), menuid2.get(0).get("menuid") });
		}
		final Map mp2 = new HashMap();
		mp2.put("view", true);
		mp2.put("add", true);
		mp2.put("edit", true);
		mp2.put("delete", true);
		return mp2;
	}

	@Override
	public List fetchAllMenus(final int roleid, final int parent) throws Exception {
		final String query = this.environment.getRequiredProperty("parentmenu.dashboard");
		final List menuList = this.jdbcTemplate.queryForList(query, new Object[] { roleid, parent });
		return menuList;
	}

	@Override
	public List fetchVehicleByTransporter(final String transid) {
		String query = "select distinct vehicledesc, \"vehicle\".vehicleid ,\"vehicletype\".names as typeveh, \"transporter\".transporterid from \"vehicle\",\"transporter\",\"customeraffiliate\",\"vehicletype\"  where \"transporter\".transporterid=\"vehicle\".transporterid and \"customeraffiliate\".affiliateid=\"transporter\".affiliateid and \"vehicle\".status=1 and \"vehicle\".vehicletypeid=\"vehicletype\".ids";
		if (!Objects.isNull(transid) && !transid.isEmpty()) {
			query = query + " and \"vehicle\".transporterid in (" + transid + ")";
			query += " order by vehicledesc asc";
			return this.jdbcTemplate.queryForList(query);
		}
		return new ArrayList();
	}
}
