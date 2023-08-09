//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.reports.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.camtrack.config.Utils;

@Repository("ccrawstatisticsdao")
@PropertySource({ "classpath:sqlqueries.properties" })
public class CCRawstatisticsDaoImp implements CCRawstatisticsDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List viewRawstatisticReeport(final int clientid, final int affiliateid, final int transporterid,
			final int driverid, final int startyear, final int endyear, final int startmonth, final int endmonth,
			final String language) throws Exception {
		String query = "select  x.*,case when x.tableorder in(1,4) and x.noofocc !=0 then (x.points/x.noofocc) else x.noofocc end as noofocc from sp_cc_rawstatistics("
				+ clientid + "," + affiliateid + "," + transporterid + "," + driverid + "," + startmonth + ", "
				+ endmonth + "," + startyear + " ," + endyear + ") x";
		System.out.println(query);
		final List<Map<String, Object>> ccrawstatisticslist = this.jdbcTemplate.queryForList(query);
		for (final Map<String, Object> map : ccrawstatisticslist) {
			final int tableorder = Utils.castIntegerObject(map.get("tableorder"));
			if (language.equalsIgnoreCase("English")) {
				map.put("paramname", map.get("parmnameeng"));
			} else if (language.equalsIgnoreCase("French")) {
				map.put("paramname", map.get("parmnamefre"));
			} else if (language.equalsIgnoreCase("Spanish")) {
				map.put("paramname", map.get("parmnamespan"));
			}
			if (tableorder == 2) {
				final int paramid = Utils.castIntegerObject(map.get("parmid"));
				query = "select case when noofoccurrence!=0 then concat(dateofoccurrence,' : ',noofoccurrence) else null end as dateofocurrence from cc_manual_subtraction_occurrence where manualsubid="
						+ paramid + " and clientid=" + clientid + " and affiliateid=" + affiliateid
						+ " and transporterid=" + transporterid + " and driverid in (0," + driverid
						+ ") and date_part('month',dateofoccurrence) between " + startmonth + " and " + endmonth
						+ " and date_part('year',dateofoccurrence) between " + startyear + " and " + endyear;
				map.put("datelist", this.jdbcTemplate.queryForList(query));
			} else {
				if (tableorder != 5) {
					continue;
				}
				final int paramid = Utils.castIntegerObject(map.get("parmid"));
				query = "select case when noofoccurrence!=0 then concat(dateofoccurrence,' : ',noofoccurrence) else null end as dateofocurrence from cc_manual_recovery_occurrence where manualrecid="
						+ paramid + " and clientid=" + clientid + " and affiliateid=" + affiliateid
						+ " and transporterid=" + transporterid + " and driverid in(0," + driverid
						+ ") and date_part('month',dateofoccurrence) between " + startmonth + " and " + endmonth
						+ " and date_part('year',dateofoccurrence) between " + startyear + " and " + endyear;
				map.put("datelist", this.jdbcTemplate.queryForList(query));
			}
		}
		return ccrawstatisticslist;
	}
}
