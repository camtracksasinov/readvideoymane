//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.configuartion.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.camtrack.configuartion.bean.AffiliateParameterMappingBean;

@Repository("mappingDao")
@PropertySource({ "classpath:sqlqueries.properties" })
public class AffiliateParameterMappingDaoImpl implements AffiliateParameterMappingDaoInterface {
	@Autowired
	private Environment environment;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List getAffiliateParameterMap(final int customerid, final int affiliateid) throws Exception {
		final List Lmap = new ArrayList();
		final String query = this.environment.getRequiredProperty("parametertype.map");
		final List parameters = this.jdbcTemplate.queryForList(query);
		final int[] parameterid = new int[parameters.size()];
		final String[] label = new String[parameters.size()];
		final Boolean[] status = new Boolean[parameters.size()];
		final Iterator itr = parameters.iterator();
		int j = 0;
		while (itr.hasNext()) {
			final Map map = (Map) itr.next();
			final int parametertypeid = Integer.parseInt(map.get("parametertypeid").toString());
			final int i = 0;
			parameterid[j] = parametertypeid;
			label[j] = map.get("label").toString();
			final String query2 = this.environment.getRequiredProperty("parametertype.affiliatemap");
			final List<Map<String, Object>> sts = this.jdbcTemplate.queryForList(query2,
					new Object[] { parametertypeid, customerid, affiliateid });
			if (sts.isEmpty()) {
				status[j] = false;
				++j;
			} else {
				for (final Map map2 : sts) {
					final int st = Integer.parseInt(map2.get("status").toString());
					if (st == 1) {
						status[j] = true;
						++j;
					} else {
						status[j] = false;
						++j;
					}
				}
			}
		}
		Lmap.add(label);
		Lmap.add(status);
		Lmap.add(parameterid);
		return Lmap;
	}

	@Override
	public int mapAffiliateAndParameter(final AffiliateParameterMappingBean mappingBean) throws Exception {
		int i = 0;
		final String query1 = this.environment.getRequiredProperty("affiliateparammap.insert");
		final String query2 = this.environment.getRequiredProperty("affiliateparammap.update");
		final int clientid = mappingBean.getCustomerid();
		final int affiliateid = mappingBean.getClientaffiliateid();
		final int createdby = Integer.parseInt(mappingBean.getCreatedby());
		final int updatedby = Integer.parseInt(mappingBean.getUpdatedby());
		final String[] status = mappingBean.getStatus();
		final int[] parameterid = mappingBean.getParameterid();
		int sts = 0;
		for (int k = 0; k < parameterid.length; ++k) {
			if (status[k].equals("true")) {
				sts = 1;
			} else {
				sts = 0;
			}
			final String queryforcount = this.environment.getRequiredProperty("affiliateparammap.getcount");
			final int count = (int) this.jdbcTemplate.queryForObject(queryforcount, (Class) Integer.class,
					new Object[] { clientid, affiliateid, parameterid[k] });
			if (count == 0) {
				try {
					i = this.jdbcTemplate.update(query1, new Object[] { clientid, affiliateid, parameterid[k],
							createdby, new Date(), updatedby, new Date(), sts });
				} catch (Exception e) {
					System.out.println(e);
				}
			} else {
				i = this.jdbcTemplate.update(query2,
						new Object[] { sts, updatedby, new Date(), clientid, affiliateid, parameterid[k] });
			}
		}
		return i;
	}
}
