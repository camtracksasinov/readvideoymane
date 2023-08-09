//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.configuartion.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.camtrack.affiliate.repository.CustomeraffiliateRepository;
import com.camtrack.config.StaticValues;
import com.camtrack.config.Utils;
import com.camtrack.configuartion.bean.AdasParams;
import com.camtrack.configuartion.bean.ConfigurationBean;
import com.camtrack.configuartion.bean.FatigueParams;
import com.camtrack.configuartion.bean.ObcParams;
import com.camtrack.configuartion.bean.RecoveryParams;
import com.camtrack.configuartion.bean.VisualParams;
import com.camtrack.configuartion.repository.AdasparameterRepository;
import com.camtrack.configuartion.repository.FatigueparameterRepository;
import com.camtrack.configuartion.repository.FixrecoveryparamsRepository;
import com.camtrack.configuartion.repository.FixvisualparamsRepository;
import com.camtrack.configuartion.repository.ObcParameterRepository;
import com.camtrack.configuartion.repository.RecoveryRepository;
import com.camtrack.configuartion.repository.ScpRepository;
import com.camtrack.configuartion.repository.ScpadasparameterRepository;
import com.camtrack.configuartion.repository.ScpfatigueparameterRepository;
import com.camtrack.configuartion.repository.ScpobcparameterRepository;
import com.camtrack.configuartion.repository.ScprecoveryRepository;
import com.camtrack.configuartion.repository.ScpvisualparameterRepository;
import com.camtrack.configuartion.repository.VisualparameterRepository;
import com.camtrack.entities.Fatigueparameter;
import com.camtrack.entities.Fixrecoveryparams;
import com.camtrack.entities.Fixvisualparams;
import com.camtrack.entities.Listconfigtypes;
import com.camtrack.entities.Obcparameter;
import com.camtrack.entities.Recovery;
import com.camtrack.entities.Scp;
import com.camtrack.entities.Scpadasparameter;
import com.camtrack.entities.Scpfatigueparameter;
import com.camtrack.entities.Scpobcparameter;
import com.camtrack.entities.Scprecovery;
import com.camtrack.entities.Scpvisualparameter;
import com.camtrack.entities.Success;
import com.camtrack.entities.User;
import com.camtrack.entities.Userlogsactivity;
import com.camtrack.entities.Visualparameter;
import com.camtrack.transporter.repository.TransporterRepository;
import com.camtrack.user.repository.CustomerRepository;
import com.camtrack.user.repository.ListconfigtypesRepository;
import com.camtrack.user.repository.UserlogsactivityRepository;

@Repository("configDao")
@PropertySource({ "classpath:sqlqueries.properties" })
public class ConfigurationDaoImpl implements ConfigurationDaoInterface {
	@Autowired
	AdasparameterRepository adasR;
	@Autowired
	CustomeraffiliateRepository affR;
	@Autowired
	CustomerRepository cusR;
	@Autowired
	private Environment environment;
	@Autowired
	FatigueparameterRepository fatigueR;
	@Autowired
	FixrecoveryparamsRepository fixrecovR;
	@Autowired
	FixvisualparamsRepository fixviR;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	ListconfigtypesRepository listconfigs;
	@Autowired
	ObcParameterRepository obcR;
	@Autowired
	RecoveryRepository recovR;
	@Autowired
	ScpadasparameterRepository scpadasR;
	@Autowired
	ScpfatigueparameterRepository scpfatigueR;
	@Autowired
	ScpobcparameterRepository scpobcR;
	@Autowired
	ScpRepository scpR;
	@Autowired
	ScprecoveryRepository scprecovR;
	@Autowired
	ScpvisualparameterRepository scpvisulR;
	@Autowired
	TransporterRepository transR;
	@Autowired
	UserlogsactivityRepository userlogRepo;
	@Autowired
	VisualparameterRepository visulR;

	@Override
	public List CcAutSubSpeedMap(final int clientid, final int affiliateid) {
		System.out.println(clientid + "" + affiliateid);
		final String query = this.environment.getRequiredProperty("cc.autsubspeedparamlist");
		return this.jdbcTemplate.queryForList(query, new Object[] { clientid, affiliateid });
	}

	@Override
	public int deactivateConfig(final int qid, final int userid) {
		String query = this.environment.getRequiredProperty("parameterconfig.deactivate");
		final int i = this.jdbcTemplate.update(query, new Object[] { new Date(), userid, qid });
		query = this.environment.getRequiredProperty("parameterconfig.delete");
		if (i > 0) {
			return this.jdbcTemplate.update(query, new Object[] { qid });
		}
		return 0;
	}

	@Override
	public List getAffliates(final int clientid) {
		if (clientid > 0) {
			final String query = this.environment.getRequiredProperty("affiliate.map");
			return this.jdbcTemplate.queryForList(query, new Object[] { clientid });
		}
		final String query = this.environment.getRequiredProperty("affiliateonly.map");
		return this.jdbcTemplate.queryForList(query);
	}

	@Override
	public List getAffParameterMap(final int clientid, final int affiliateid) {
		final String query = this.environment.getRequiredProperty("affparametermap.byaffclientid");
		return this.jdbcTemplate.queryForList(query, new Object[] { affiliateid, clientid });
	}

	@Override
	public List getClientMap() {
		final String query = this.environment.getRequiredProperty("client.map");
		return this.jdbcTemplate.queryForList(query);
	}

	@Override
	public Map getParameterById(final int clientid, final int affiliateid, int transporterid,
			final int parametertypeid) {
		final Integer itnitparam = transporterid;
		transporterid = 0;
		Map Lmap = null;
		String query = this.environment.getRequiredProperty("scp.edit");
		try {
			Lmap = this.jdbcTemplate.queryForMap(query, new Object[] { clientid, affiliateid, transporterid });
			Lmap.put("parametertypeid", parametertypeid);
			Lmap.put("customerid", clientid);
			Lmap.put("clientaffiliateid", affiliateid);
			Lmap.put("transporterid", itnitparam);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		query = this.environment.getRequiredProperty("scp.obcparamlist");
		List obcparams = null;
		try {
			obcparams = this.jdbcTemplate.queryForList(query);
		} catch (Exception ex2) {
			ex2.printStackTrace();
		}
		query = this.environment.getRequiredProperty("scpobcparam.list");
		List<Map<String, Object>> scpobcparams = null;
		try {
			scpobcparams = this.jdbcTemplate.queryForList(query, new Object[] { clientid, affiliateid, transporterid });
		} catch (Exception ex3) {
			ex3.printStackTrace();
		}
		Iterator itr = obcparams.iterator();
		final int[] paramid = new int[obcparams.size()];
		final int[] recvalue = new int[obcparams.size()];
		final int[] alarmvalue = new int[obcparams.size()];
		final int[] alertvalue = new int[obcparams.size()];
		final boolean[] select = new boolean[obcparams.size()];
		int j = 0;
		while (itr.hasNext()) {
			final Map map = (Map) itr.next();
			paramid[j] = Integer.parseInt(map.get("obcparamid").toString());
			for (final Map mapscp : scpobcparams) {
				final int id = Integer.parseInt(mapscp.get("obcparamid").toString());
				if (id == paramid[j]) {
					recvalue[j] = Integer.parseInt(mapscp.get("recordvalue").toString());
					alarmvalue[j] = Integer.parseInt(mapscp.get("alarmvalue").toString());
					alertvalue[j] = Integer.parseInt(mapscp.get("alertvalue").toString());
					select[j] = true;
					break;
				}
				recvalue[j] = 0;
				alertvalue[j] = (alarmvalue[j] = 0);
				select[j] = false;
			}
			++j;
		}
		Lmap.put("obcparams", obcparams);
		Lmap.put("obcparamid", paramid);
		Lmap.put("obcrec", recvalue);
		Lmap.put("obcalert", alertvalue);
		Lmap.put("obcalarm", alarmvalue);
		Lmap.put("paramselect", select);
		query = this.environment.getRequiredProperty("scp.visualparamlist");
		List visualparamlist = null;
		try {
			visualparamlist = this.jdbcTemplate.queryForList(query,
					new Object[] { clientid, affiliateid, transporterid });
		} catch (Exception ex4) {
			ex4.printStackTrace();
		}
		query = this.environment.getRequiredProperty("scpvisualparameter.list");
		List<Map<String, Object>> scpvisualparamlist = null;
		try {
			scpvisualparamlist = this.jdbcTemplate.queryForList(query,
					new Object[] { clientid, affiliateid, transporterid });
		} catch (Exception ex5) {
			ex5.printStackTrace();
		}
		itr = visualparamlist.iterator();
		final int[] vparamid = new int[visualparamlist.size()];
		final String[] vlabels = new String[visualparamlist.size()];
		final String[] vsublabels = new String[visualparamlist.size()];
		final int[] vpoints = new int[visualparamlist.size()];
		final boolean[] vselect = new boolean[visualparamlist.size()];
		j = 0;
		while (itr.hasNext()) {
			final Map map2 = (Map) itr.next();
			vparamid[j] = Integer.parseInt(map2.get("visualparamid").toString());
			vlabels[j] = map2.get("visuallabel").toString();
			vsublabels[j] = map2.get("visualsublabel").toString();
			for (final Map mapscp2 : scpvisualparamlist) {
				final int id2 = Integer.parseInt(mapscp2.get("visualparamid").toString());
				if (id2 == vparamid[j]) {
					vpoints[j] = Integer.parseInt(mapscp2.get("points").toString());
					vselect[j] = true;
					break;
				}
				vpoints[j] = 0;
				vselect[j] = false;
			}
			++j;
		}
		Lmap.put("visualparams", visualparamlist);
		Lmap.put("vparamid", vparamid);
		Lmap.put("vparamlabel", vlabels);
		Lmap.put("vparamsublabel", vsublabels);
		Lmap.put("vpoints", vpoints);
		Lmap.put("vparamselect", vselect);
		query = this.environment.getRequiredProperty("scp.recoverylist");
		List recoverylist = null;
		try {
			recoverylist = this.jdbcTemplate.queryForList(query, new Object[] { clientid, affiliateid, transporterid });
		} catch (Exception ex6) {
			ex6.printStackTrace();
		}
		query = this.environment.getRequiredProperty("scprecovery.list");
		List<Map<String, Object>> scprecoverylist = null;
		try {
			scprecoverylist = this.jdbcTemplate.queryForList(query,
					new Object[] { clientid, affiliateid, transporterid });
		} catch (Exception ex7) {
			ex7.printStackTrace();
		}
		itr = recoverylist.iterator();
		final int[] rparamid = new int[recoverylist.size()];
		final String[] rlabels = new String[recoverylist.size()];
		final String[] rsublabels = new String[recoverylist.size()];
		final int[] rpoints = new int[recoverylist.size()];
		final boolean[] rselect = new boolean[recoverylist.size()];
		j = 0;
		while (itr.hasNext()) {
			final Map map3 = (Map) itr.next();
			rparamid[j] = Integer.parseInt(map3.get("recoveryid").toString());
			rlabels[j] = map3.get("recoverylabel").toString();
			rsublabels[j] = map3.get("recoverysublabel").toString();
			for (final Map mapscp3 : scprecoverylist) {
				final int id3 = Integer.parseInt(mapscp3.get("recoveryid").toString());
				if (id3 == rparamid[j]) {
					rpoints[j] = Integer.parseInt(mapscp3.get("points").toString());
					rselect[j] = true;
					break;
				}
				rpoints[j] = 0;
				rselect[j] = false;
			}
			++j;
		}
		Lmap.put("recoveryid", rparamid);
		Lmap.put("reclabel", rlabels);
		Lmap.put("recsublabel", rsublabels);
		Lmap.put("recpoints", rpoints);
		Lmap.put("rparamselect", rselect);
		Lmap.put("recovery", recoverylist);
		Lmap.put("parameterconfigid", Lmap.get("scpid").toString());
		System.out.println(Lmap);
		return Lmap;
	}

	@Override
	public Map getParameterById2(final int clientid, final int affiliateid, int transporterid,
			final int parametertypeid) {
		final Integer itnitparam = transporterid;
		transporterid = 0;
		Map Lmap = null;
		String query = this.environment.getRequiredProperty("scp.edit");
		try {
			Lmap = this.jdbcTemplate.queryForMap(query, new Object[] { clientid, affiliateid, transporterid });
			Lmap.put("parametertypeid", parametertypeid);
			Lmap.put("customerid", clientid);
			Lmap.put("clientaffiliateid", affiliateid);
			Lmap.put("transporterid", itnitparam);
		} catch (Exception ex) {
			ex.printStackTrace();
			Lmap = new HashMap();
			Lmap.put("parametertypeid", parametertypeid);
			Lmap.put("customerid", clientid);
			Lmap.put("clientaffiliateid", affiliateid);
			Lmap.put("transporterid", itnitparam);
		}
		query = this.environment.getRequiredProperty("scp.obcparamlist");
		List obcparams = null;
		try {
			obcparams = this.jdbcTemplate.queryForList(query);
		} catch (Exception ex2) {
			ex2.printStackTrace();
		}
		query = this.environment.getRequiredProperty("scpobcparam.list");
		List scpobcparams = null;
		int j = 0;
		Integer record = 0;
		Integer alarm = 0;
		Integer alert = 0;
		boolean paramselec = false;
		try {
			scpobcparams = this.jdbcTemplate.queryForList(query, new Object[] { clientid, affiliateid, transporterid });
			final Iterator itr = obcparams.iterator();
			final int[] paramid = new int[obcparams.size()];
			final List<Map> resultobcparam = new ArrayList<>();
			while (itr.hasNext()) {
				final Map map = (Map) itr.next();
				paramid[j] = Integer.parseInt(map.get("obcparamid").toString());
				final Iterator itrscp = scpobcparams.iterator();
				record = 0;
				alarm = 0;
				alert = 0;
				paramselec = false;
				while (itrscp.hasNext()) {
					final Map mapscp = (Map) itrscp.next();
					final int id = Integer.parseInt(mapscp.get("obcparamid").toString());
					if (id == paramid[j]) {
						record = Integer.parseInt(mapscp.get("recordvalue").toString());
						alarm = Integer.parseInt(mapscp.get("alarmvalue").toString());
						alert = Integer.parseInt(mapscp.get("alertvalue").toString());
						paramselec = true;
						break;
					}
				}
				map.put("record", record);
				map.put("alarm", alarm);
				map.put("alert", alert);
				map.put("select", paramselec);
				resultobcparam.add(map);
				++j;
			}
			Lmap.put("obcparams", resultobcparam);
		} catch (Exception ex3) {
			ex3.printStackTrace();
		}
		query = this.environment.getRequiredProperty("scp.fatigueparamlist");
		List fatigueparams = null;
		try {
			fatigueparams = this.jdbcTemplate.queryForList(query);
		} catch (Exception ex4) {
			ex4.printStackTrace();
		}
		query = this.environment.getRequiredProperty("scpfatigueparam.list");
		List scpfatigueparams = null;
		j = 0;
		record = 0;
		alarm = 0;
		alert = 0;
		paramselec = false;
		try {
			scpfatigueparams = this.jdbcTemplate.queryForList(query,
					new Object[] { clientid, affiliateid, transporterid });
			final Iterator itr = fatigueparams.iterator();
			final int[] paramid2 = new int[fatigueparams.size()];
			final List<Map> resultfatigueparam = new ArrayList<>();
			while (itr.hasNext()) {
				final Map map2 = (Map) itr.next();
				paramid2[j] = Integer.parseInt(map2.get("fatigparamid").toString());
				final Iterator itrscp2 = scpfatigueparams.iterator();
				record = 0;
				alarm = 0;
				alert = 0;
				paramselec = false;
				while (itrscp2.hasNext()) {
					final Map mapscp2 = (Map) itrscp2.next();
					final int id2 = Integer.parseInt(mapscp2.get("fatigparamid").toString());
					if (id2 == paramid2[j]) {
						record = Integer.parseInt(mapscp2.get("recordvalue").toString());
						alarm = Integer.parseInt(mapscp2.get("alarmvalue").toString());
						alert = Integer.parseInt(mapscp2.get("alertvalue").toString());
						paramselec = true;
						break;
					}
				}
				map2.put("record", record);
				map2.put("alarm", alarm);
				map2.put("alert", alert);
				map2.put("select", paramselec);
				resultfatigueparam.add(map2);
				++j;
			}
			Lmap.put("fatigparams", resultfatigueparam);
		} catch (Exception ex5) {
			ex5.printStackTrace();
		}
		query = this.environment.getRequiredProperty("scp.adasparamlist");
		List adasparams = null;
		try {
			adasparams = this.jdbcTemplate.queryForList(query);
		} catch (Exception ex6) {
			ex6.printStackTrace();
		}
		query = this.environment.getRequiredProperty("scpadasparam.list");
		List scpadasparams = null;
		j = 0;
		record = 0;
		alarm = 0;
		alert = 0;
		paramselec = false;
		try {
			scpadasparams = this.jdbcTemplate.queryForList(query,
					new Object[] { clientid, affiliateid, transporterid });
			final Iterator itr = adasparams.iterator();
			final int[] paramid3 = new int[adasparams.size()];
			final List<Map> resultadasparam = new ArrayList<>();
			while (itr.hasNext()) {
				final Map map3 = (Map) itr.next();
				paramid3[j] = Integer.parseInt(map3.get("adasparamid").toString());
				final Iterator itrscp3 = scpadasparams.iterator();
				record = 0;
				alarm = 0;
				alert = 0;
				paramselec = false;
				while (itrscp3.hasNext()) {
					final Map mapscp3 = (Map) itrscp3.next();
					final int id3 = Integer.parseInt(mapscp3.get("adasparamid").toString());
					if (id3 == paramid3[j]) {
						record = Integer.parseInt(mapscp3.get("recordvalue").toString());
						alarm = Integer.parseInt(mapscp3.get("alarmvalue").toString());
						alert = Integer.parseInt(mapscp3.get("alertvalue").toString());
						paramselec = true;
						break;
					}
				}
				map3.put("record", record);
				map3.put("alarm", alarm);
				map3.put("alert", alert);
				map3.put("select", paramselec);
				resultadasparam.add(map3);
				++j;
			}
			Lmap.put("adasparams", resultadasparam);
		} catch (Exception ex7) {
			ex7.printStackTrace();
		}
		query = this.environment.getRequiredProperty("scp.visualparamlist2");
		List visualparamlist = null;
		try {
			visualparamlist = this.jdbcTemplate.queryForList(query,
					new Object[] { clientid, affiliateid, transporterid });
			query = this.environment.getRequiredProperty("scpvisualparameter.list");
			List scpvisualparamlist = null;
			try {
				scpvisualparamlist = this.jdbcTemplate.queryForList(query,
						new Object[] { clientid, affiliateid, transporterid });
			} catch (Exception ex8) {
				ex8.printStackTrace();
			}
			final Iterator itr = visualparamlist.iterator();
			j = 0;
			final List<Map> resultvisual = new ArrayList<>();
			while (itr.hasNext()) {
				final Map map4 = (Map) itr.next();
				final int vparamid = Integer.parseInt(map4.get("visualparamid").toString());
				final Iterator itrscp4 = scpvisualparamlist.iterator();
				int vpoints = 0;
				boolean vselect = false;
				while (itrscp4.hasNext()) {
					final Map mapscp4 = (Map) itrscp4.next();
					final int id4 = Integer.parseInt(mapscp4.get("visualparamid").toString());
					if (id4 == vparamid) {
						vpoints = Integer.parseInt(mapscp4.get("points").toString());
						vselect = true;
						break;
					}
				}
				map4.put("pts", vpoints);
				map4.put("select", vselect);
				resultvisual.add(map4);
				++j;
			}
			Lmap.put("visualparams", resultvisual);
		} catch (Exception ex9) {
			ex9.printStackTrace();
		}
		query = this.environment.getRequiredProperty("scp.recoverylist2");
		List recoverylist = null;
		try {
			recoverylist = this.jdbcTemplate.queryForList(query, new Object[] { clientid, affiliateid, transporterid });
			query = this.environment.getRequiredProperty("scprecovery.list");
			List scprecoverylist = null;
			try {
				scprecoverylist = this.jdbcTemplate.queryForList(query,
						new Object[] { clientid, affiliateid, transporterid });
			} catch (Exception ex10) {
				ex10.printStackTrace();
			}
			final Iterator itr = recoverylist.iterator();
			j = 0;
			final List<Map> resultrecovery = new ArrayList<>();
			Short nbmonths = 0;
			while (itr.hasNext()) {
				final Map map5 = (Map) itr.next();
				final int rparamid = Integer.parseInt(map5.get("recoveryid").toString());
				final Iterator itrscp5 = scprecoverylist.iterator();
				int rpoints = 0;
				boolean rselect = false;
				nbmonths = null;
				while (itrscp5.hasNext()) {
					final Map mapscp5 = (Map) itrscp5.next();
					final int id5 = Integer.parseInt(mapscp5.get("recoveryid").toString());
					if (id5 == rparamid) {
						rpoints = Integer.parseInt(mapscp5.get("points").toString());
						try {
							nbmonths = Short.parseShort(mapscp5.get("nbmonths").toString());
						} catch (Exception ex11) {
							nbmonths = null;
						}
						rselect = true;
						break;
					}
				}
				map5.put("rpts", rpoints);
				map5.put("rselect", rselect);
				if (!Objects.isNull(nbmonths)) {
					map5.put("nbmonths", nbmonths);
				} else {
					try {
						final Integer idfixe = Integer.parseInt(map5.get("idfixe").toString());
						if (idfixe == 1) {
							map5.put("nbmonths", 0);
						}
					} catch (Exception ex12) {
					}
				}
				resultrecovery.add(map5);
				++j;
			}
			Lmap.put("recovery", resultrecovery);
		} catch (Exception ex8) {
			ex8.printStackTrace();
		}
		return Lmap;
	}

	@Override
	public List getParameterMap() {
		final String query = this.environment.getRequiredProperty("parametertype.map");
		return this.jdbcTemplate.queryForList(query);
	}

	@Override
	public List manualSubtractionMap(final int clientid, final int affiliateid) {
		final String query = this.environment.getRequiredProperty("cc.manualsubparamlistrecovery");
		return this.jdbcTemplate.queryForList(query, new Object[] { clientid, affiliateid, clientid, affiliateid });
	}

	@Override
	public boolean monthOverLap(final int frommonth, final int tomonth, final int configid, final int clientid,
			final int affiliateid) {
		String query = "SELECT count(*) from parameterconfig where status= 1 and customerid=" + clientid;
		if (affiliateid > 0) {
			query = query + " and clientaffiliateid = " + affiliateid;
		}
		if (configid > 0) {
			query = query + " and parameterconfigid != " + configid;
		}
		query = query + " and  (" + frommonth + " between frommonth and tomonth or  " + tomonth
				+ " between frommonth and tomonth)";
		final int i = (int) this.jdbcTemplate.queryForObject(query, (Class) Integer.class);
		return i > 0;
	}

	@Override
	public List obcParamMap() {
		final String query = this.environment.getRequiredProperty("scp.obcparamlist");
		return this.jdbcTemplate.queryForList(query);
	}

	@Override
	public List recoveryParam(final int clientid, final int affiliateid) {
		final String query = this.environment.getRequiredProperty("scp.recoverylist");
		return this.jdbcTemplate.queryForList(query, new Object[] { clientid, affiliateid });
	}

	@Override
	public ResponseEntity<?> saveConfiguration(final ConfigurationBean configurationBean, final User user)
			throws ParseException {
		final Date frmdate = null;
		final Date todate = null;
		Integer transid = 0;
		Integer affid = 0;
		Integer cusid = 0;
		boolean createorupdate = false;
		Map<String, Object> entities = null;
		if (!Objects.isNull(configurationBean.getTransporterid()) && (configurationBean.getTransporterid() != 0)) {
			String querydriver = "select t.transporterid  as id,t.name as nm from transporter t where t.transporterid = "
					+ configurationBean.getTransporterid();
			entities = this.jdbcTemplate.queryForMap(querydriver);
			transid = (Integer) entities.get("id");
			if (Objects.isNull(transid)) {
				return ResponseEntity.status(HttpStatus.OK).body(
						new Success(StaticValues.codetransporterontexists, StaticValues.codetransporterontexists_Int));
			}
			querydriver = "select t.affiliateid as id from transporter t where t.transporterid = "
					+ configurationBean.getTransporterid() + "";
			affid = (Integer) this.jdbcTemplate.queryForObject(querydriver, (Class) Integer.class);
			querydriver = "select c.customerid from customeraffiliate c where c.affiliateid = " + affid;
			cusid = (Integer) this.jdbcTemplate.queryForObject(querydriver, (Class) Integer.class);
		} else if (!Objects.isNull(configurationBean.getClientaffiliateid())
				&& (configurationBean.getClientaffiliateid() != 0)) {
			transid = 0;
			String querydriver = "select c.affiliateid as id,c.name as nm from customeraffiliate c where c.affiliateid = "
					+ configurationBean.getClientaffiliateid();
			try {
				entities = this.jdbcTemplate.queryForMap(querydriver);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			affid = (Integer) entities.get("id");
			if (Objects.isNull(affid)) {
				return ResponseEntity.status(HttpStatus.OK).body(
						new Success(StaticValues.codeaffiliateontexists, StaticValues.codeaffiliateontexists_Int));
			}
			querydriver = "select c.customerid as id from customeraffiliate c where c.affiliateid = " + affid;
			try {
				cusid = (Integer) this.jdbcTemplate.queryForObject(querydriver, (Class) Integer.class);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else if (!Objects.isNull(configurationBean.getCustomerid()) && (configurationBean.getCustomerid() != 0)) {
			transid = 0;
			affid = 0;
			final String querydriver = "select c.customerid as id,c.name as nm from customer c where c.customerid = "
					+ configurationBean.getCustomerid();
			entities = this.jdbcTemplate.queryForMap(querydriver);
			affid = (Integer) entities.get("id");
			if (Objects.isNull(cusid)) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new Success(StaticValues.codecustomerontexists, StaticValues.codecustomerontexists_Int));
			}
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Success(StaticValues.BadParameters, 400));
		}
		final Date date = new Date();
		Scp scp;
		scp = this.scpR.findUniqueSCP(cusid, affid, transid).orElse(null);
		if (Objects.isNull(scp)) {
			final String query = this.environment.getRequiredProperty("scp.insert");
			final Integer scpid = this.jdbcTemplate.update(query,
					new Object[] { cusid, affid, configurationBean.getInitialpoint(),
							configurationBean.getNoofcalendaryear(), configurationBean.getDrivergreenlimit(),
							configurationBean.getDriveryellowlimit(), configurationBean.getDriverredlimit(),
							configurationBean.getDriverdisqualifiedlimit(), configurationBean.getTransporterredlimit(),
							configurationBean.getMinsenioritydriver(), configurationBean.getMinsenioritytransporter(),
							configurationBean.getMindistance(), date, user.getUserid(), date, user.getUserid(),
							transid });
			scp = this.scpR.findUniqueSCP(cusid, affid, transid).orElse(null);
			createorupdate = true;
			Scp.init(createorupdate, user);
		} else {
			createorupdate = false;
			Scp.init(createorupdate, user);
			/**
			 * scp = this.scpR.findById(configurationBean.getScpid()).orElse(null); if
			 * (Objects.isNull(scp)) { return ResponseEntity.status(HttpStatus.OK) .body(new
			 * Success(StaticValues.codecscpdontexists,
			 * StaticValues.codecscpdontexists_Int)); }
			 */
			scp.setUpdatedby(user.getUserid());
			scp.setUpdatedon(date);
		}
		final String name = String.valueOf(entities.get("nm"));
		if (!Objects.isNull(configurationBean.getCustomerid())) {
			scp.setClientid(configurationBean.getCustomerid(), name, createorupdate);
		}
		if (!Objects.isNull(configurationBean.getClientaffiliateid())) {
			scp.setAffiliateid(configurationBean.getClientaffiliateid(), name, createorupdate);
		}
		if (!Objects.isNull(configurationBean.getTransporterid())) {
			scp.setTransporterid(configurationBean.getTransporterid(), name, createorupdate);
		}
		if (!Objects.isNull(configurationBean.getDrivergreenlimit())) {
			scp.setDrivergreenlimit(configurationBean.getDrivergreenlimit(), createorupdate);
		}
		if (!Objects.isNull(configurationBean.getDriverdisqualifiedlimit())) {
			scp.setDriverdisqualifiedlimit(configurationBean.getDriverdisqualifiedlimit(), createorupdate);
		}
		if (!Objects.isNull(configurationBean.getDriverredlimit())) {
			scp.setDriverredlimit(configurationBean.getDriverredlimit(), createorupdate);
		}
		if (!Objects.isNull(configurationBean.getDriveryellowlimit())) {
			scp.setDriveryellowlimit(configurationBean.getDriveryellowlimit(), createorupdate);
		}
		if (!Objects.isNull(configurationBean.getInitialpoint())) {
			scp.setInitialpoint(configurationBean.getInitialpoint(), createorupdate);
		}
		if (!Objects.isNull(configurationBean.getMindistance())) {
			scp.setMindistance(configurationBean.getMindistance(), createorupdate);
		}
		if (!Objects.isNull(configurationBean.getMinsenioritydriver())) {
			scp.setMinsenioritydriver(configurationBean.getMinsenioritydriver(), createorupdate);
		}
		if (!Objects.isNull(configurationBean.getMinsenioritytransporter())) {
			scp.setMinsenioritytransporter(configurationBean.getMinsenioritytransporter(), createorupdate);
		}
		if (!Objects.isNull(configurationBean.getNoofcalendaryear())) {
			scp.setNoofcalendaryear(configurationBean.getNoofcalendaryear(), createorupdate);
		}
		if (!Objects.isNull(configurationBean.getTransporterredlimit())) {
			scp.setTransporterredlimit(configurationBean.getTransporterredlimit(), createorupdate);
		}
		this.scpR.saveAndFlush(scp);
		if (Utils.countnumberligne(Scp.log) > 1) {
			final Listconfigtypes paramconfig = this.listconfigs.findById(Utils.ParamTypeconfig()).orElse(null);
			final Userlogsactivity loguser = new Userlogsactivity(paramconfig);
			loguser.setDates(date);
			loguser.setUserid(user);
			loguser.setLogsinfos(Scp.log);
			this.userlogRepo.saveAndFlush(loguser);
		}
		String query = this.environment.getRequiredProperty("scpobcparameter0.delete");
		try {
			this.jdbcTemplate.update(query, new Object[] { cusid, affid, transid });
		} catch (Exception ex2) {
			ex2.printStackTrace();
		}
		query = this.environment.getRequiredProperty("scpobcparameter.insert");
		final List<ObcParams> listobc = configurationBean.getObcparams();
		final Obcparameter obc = null;
		if (!Objects.isNull(listobc) && !listobc.isEmpty()) {
			for (final ObcParams obcParams : listobc) {
				final Integer obcid = obcParams.getObcparamid();
				if (!Objects.isNull(obcParams.getSelect()) && obcParams.getSelect() && !Objects.isNull(obcid)) {
					final Scpobcparameter scpobc = this.scpobcR.findUniqueSCP(cusid, affid, transid, obcid)
							.orElse(null);
					if (Objects.isNull(scpobc)) {
						try {
							this.jdbcTemplate.update(query,
									new Object[] { obcid, cusid, affid, obcParams.getRecord(), obcParams.getAlert(),
											obcParams.getAlarm(), date, user.getUserid(), date, user.getUserid(),
											transid });
						} catch (Exception ex3) {
							ex3.printStackTrace();
						}
					} else {
						if (!Objects.isNull(obcParams.getAlarm()) && obcParams.getAlarm() > 0) {
							scpobc.setAlarmvalue(obcParams.getAlarm());
						} else {
							scpobc.setAlarmvalue(0);
						}
						if (!Objects.isNull(obcParams.getAlert()) && obcParams.getAlert() > 0) {
							scpobc.setAlertvalue(obcParams.getAlert());
						} else {
							scpobc.setAlertvalue(0);
						}
						if (!Objects.isNull(obcParams.getRecord()) && obcParams.getRecord() > 0) {
							scpobc.setRecordvalue(obcParams.getRecord());
						} else {
							scpobc.setRecordvalue(0);
						}
						scpobc.setUpdatedby(user.getUserid());
						scpobc.setUpdatedon(date);
						if (Objects.isNull(scpobc.getCreatedby()) || Objects.isNull(scpobc.getCreatedon())) {
							scpobc.setCreatedby(user.getUserid());
							scpobc.setCreatedon(date);
						}
						this.scpobcR.saveAndFlush(scpobc);
					}
				}
			}
		}
		query = this.environment.getRequiredProperty("scpfatigueparameter0.delete");
		this.jdbcTemplate.update(query, new Object[] { cusid, affid, transid });
		query = this.environment.getRequiredProperty("scpfatigueparameter.insert");
		final List<FatigueParams> listfatigue = configurationBean.getFatigparams();
		final Fatigueparameter fatigue = null;
		if (!Objects.isNull(listfatigue) && !listfatigue.isEmpty()) {
			for (final FatigueParams fatigueParams : listfatigue) {
				final Integer fatigueid = fatigueParams.getFatigparamid();
				if (!Objects.isNull(fatigueParams.getSelect()) && fatigueParams.getSelect()
						&& !Objects.isNull(fatigueid)) {
					Scpfatigueparameter scpfatigue;
					try {
						scpfatigue = this.scpfatigueR.findUniqueSCP(cusid, affid, transid, fatigueid).orElse(null);
					} catch (Exception ex4) {
						ex4.printStackTrace();
						scpfatigue = null;
					}
					if (Objects.isNull(scpfatigue)) {
						try {
							this.jdbcTemplate.update(query,
									new Object[] { fatigueid, cusid, affid, fatigueParams.getRecord(),
											fatigueParams.getAlert(), fatigueParams.getAlarm(), date, user.getUserid(),
											date, user.getUserid(), transid });
						} catch (Exception ex4) {
							ex4.printStackTrace();
						}
					} else {
						if (!Objects.isNull(fatigueParams.getAlarm()) && fatigueParams.getAlarm() > 0) {
							scpfatigue.setAlarmvalue(fatigueParams.getAlarm());
						} else {
							scpfatigue.setAlarmvalue(0);
						}
						if (!Objects.isNull(fatigueParams.getAlert()) && fatigueParams.getAlert() > 0) {
							scpfatigue.setAlertvalue(fatigueParams.getAlert());
						} else {
							scpfatigue.setAlertvalue(0);
						}
						if (!Objects.isNull(fatigueParams.getRecord()) && fatigueParams.getRecord() > 0) {
							scpfatigue.setRecordvalue(fatigueParams.getRecord());
						} else {
							scpfatigue.setRecordvalue(0);
						}
						scpfatigue.setUpdatedby(user.getUserid());
						scpfatigue.setUpdatedon(date);
						if (Objects.isNull(scpfatigue.getCreatedby()) || Objects.isNull(scpfatigue.getCreatedon())) {
							scpfatigue.setCreatedby(user.getUserid());
							scpfatigue.setCreatedon(date);
						}
						this.scpfatigueR.saveAndFlush(scpfatigue);
					}
				}
			}
		}
		query = this.environment.getRequiredProperty("scpadasparameter0.delete");
		this.jdbcTemplate.update(query, new Object[] { cusid, affid, transid });
		query = this.environment.getRequiredProperty("scpadasparameter.insert");
		final List<AdasParams> listadas = configurationBean.getAdasparams();
		if (!Objects.isNull(listadas) && !listadas.isEmpty()) {
			for (final AdasParams adadsParams : listadas) {
				final Integer adasid = adadsParams.getAdasparamid();
				if (!Objects.isNull(adadsParams.getSelect()) && adadsParams.getSelect() && !Objects.isNull(adasid)) {
					final Scpadasparameter scpadas = this.scpadasR.findUniqueSCP(cusid, affid, transid, adasid)
							.orElse(null);
					if (Objects.isNull(scpadas)) {
						try {
							this.jdbcTemplate.update(query,
									new Object[] { adasid, cusid, affid, adadsParams.getRecord(),
											adadsParams.getAlert(), adadsParams.getAlarm(), date, user.getUserid(),
											date, user.getUserid(), transid });
						} catch (Exception ex5) {
							ex5.printStackTrace();
						}
					} else {
						if (!Objects.isNull(adadsParams.getAlarm()) && adadsParams.getAlarm() > 0) {
							scpadas.setAlarmvalue(adadsParams.getAlarm());
						} else {
							scpadas.setAlarmvalue(0);
						}
						if (!Objects.isNull(adadsParams.getAlert()) && adadsParams.getAlert() > 0) {
							scpadas.setAlertvalue(adadsParams.getAlert());
						} else {
							scpadas.setAlertvalue(0);
						}
						if (!Objects.isNull(adadsParams.getRecord()) && adadsParams.getRecord() > 0) {
							scpadas.setRecordvalue(adadsParams.getRecord());
						} else {
							scpadas.setRecordvalue(0);
						}
						scpadas.setUpdatedby(user.getUserid());
						scpadas.setUpdatedon(date);
						if (Objects.isNull(scpadas.getCreatedby()) || Objects.isNull(scpadas.getCreatedon())) {
							scpadas.setCreatedby(user.getUserid());
							scpadas.setCreatedon(date);
						}
						this.scpadasR.saveAndFlush(scpadas);
					}
				}
			}
		}
		final List<VisualParams> listvisualp = configurationBean.getVisualparams();
		Visualparameter visualP = null;
		query = this.environment.getRequiredProperty("scpvisualparameter0.delete");
		this.jdbcTemplate.update(query, new Object[] { cusid, affid, transid });
		query = this.environment.getRequiredProperty("visualparameter0.delete");
		this.jdbcTemplate.update(query, new Object[] { cusid, affid, transid });
		if (!Objects.isNull(listvisualp) && !listvisualp.isEmpty()) {
			for (final VisualParams visparam : listvisualp) {
				if (!Objects.isNull(visparam.getSelect()) && visparam.getSelect()) {
					if (!Objects.isNull(visparam.getSublbl()) && !visparam.getSublbl().equalsIgnoreCase("")) {
						final Fixvisualparams fixvi = this.fixviR.findById(visparam.getIdfixe()).orElse(null);
						if (Objects.isNull(fixvi)) {
							return ResponseEntity.status(HttpStatus.OK).body(new Success(
									StaticValues.codevisualparameter, StaticValues.codevisualparameter_Int));
						}
						visualP = this.visulR.findUniqueSCP(cusid, affid, transid, visparam.getIdfixe()).orElse(null);
						if (!Objects.isNull(visualP)) {
							visualP.setVisualsublabel(visparam.getSublbl());
							visualP.setUpdatedby(user.getUserid());
							visualP.setUpdatedon(date);
							visualP.setStatus(1);
						} else {
							visualP = new Visualparameter();
							visualP.setClientid(cusid);
							visualP.setAffiliateid(affid);
							visualP.setTransporterid(transid);
							visualP.setCreatedby(user.getUserid());
							visualP.setCreatedon(date);
							visualP.setVisualsublabel(visparam.getSublbl());
							visualP.setUpdatedby(user.getUserid());
							visualP.setUpdatedon(date);
							visualP.setIdfixe(fixvi);
							visualP.setStatus(1);
						}
						visualP = this.visulR.saveAndFlush(visualP);
					}
					if (Objects.isNull(visualP)) {
						continue;
					}
					Scpvisualparameter scpvisual = this.scpvisulR
							.findUniqueSCP(cusid, affid, transid, visualP.getVisualparamid()).orElse(null);
					if (Objects.isNull(scpvisual)) {
						scpvisual = new Scpvisualparameter();
						scpvisual.setClientid(cusid);
						scpvisual.setAffiliateid(affid);
						scpvisual.setTransporterid(transid);
						scpvisual.setCreatedby(user.getUserid());
						scpvisual.setCreatedon(date);
						scpvisual.setPoints(visparam.getPts());
						scpvisual.setUpdatedby(user.getUserid());
						scpvisual.setUpdatedon(date);
						scpvisual.setVisualparamid(visualP.getVisualparamid());
						scpvisual.setStatus(1);
					} else {
						scpvisual.setPoints(visparam.getPts());
						scpvisual.setUpdatedby(user.getUserid());
						scpvisual.setUpdatedon(date);
						if (Objects.isNull(scpvisual.getCreatedby())) {
							scpvisual.setCreatedby(user.getUserid());
							scpvisual.setCreatedon(date);
						}
					}
					scpvisual = this.scpvisulR.saveAndFlush(scpvisual);
				}
			}
		}
		final List<RecoveryParams> listrecovp = configurationBean.getRecovery();
		Recovery recoveryp = null;
		query = this.environment.getRequiredProperty("scprecovery0.delete");
		this.jdbcTemplate.update(query, new Object[] { cusid, affid, transid });
		query = this.environment.getRequiredProperty("recovery0.delete");
		this.jdbcTemplate.update(query, new Object[] { cusid, affid, transid });
		if (!Objects.isNull(listrecovp) && !listrecovp.isEmpty()) {
			for (final RecoveryParams recovparam : listrecovp) {
				if (!Objects.isNull(recovparam.getSelect()) && recovparam.getSelect()) {
					final Fixrecoveryparams fixrecov = this.fixrecovR.findById(recovparam.getIdfixe()).orElse(null);
					if (Objects.isNull(fixrecov)) {
						return ResponseEntity.status(HttpStatus.OK).body(new Success(StaticValues.coderecoveryparameter,
								StaticValues.coderecoveryparameter_Int));
					}
					recoveryp = this.recovR.findUniqueSCP(cusid, affid, transid, recovparam.getIdfixe()).orElse(null);
					if (!Objects.isNull(recoveryp)) {
						recoveryp.setRecoverysublabel(recovparam.getSublbl());
						recoveryp.setUpdatedby(user.getUserid());
						recoveryp.setUpdatedon(date);
						recoveryp.setStatus(1);
					} else {
						recoveryp = new Recovery();
						recoveryp.setClientid(cusid);
						recoveryp.setAffiliateid(affid);
						recoveryp.setTransporterid(transid);
						recoveryp.setCreatedby(user.getUserid());
						recoveryp.setCreatedon(date);
						recoveryp.setRecoverysublabel(recovparam.getSublbl());
						recoveryp.setUpdatedby(user.getUserid());
						recoveryp.setUpdatedon(date);
						recoveryp.setIdfixe(fixrecov);
						recoveryp.setStatus(1);
						if (Objects.isNull(recovparam.getSublbl())) {
							recoveryp.setRecoverysublabel(fixrecov.getNames());
						} else {
							recoveryp.setRecoverysublabel(recovparam.getSublbl());
						}
					}
					recoveryp = this.recovR.saveAndFlush(recoveryp);
					if (Objects.isNull(recoveryp)) {
						continue;
					}
					Scprecovery scprecov = this.scprecovR
							.findUniqueSCP(cusid, affid, transid, recoveryp.getRecoveryid()).orElse(null);
					if (Objects.isNull(scprecov)) {
						scprecov = new Scprecovery();
						scprecov.setClientid(cusid);
						scprecov.setAffiliateid(affid);
						scprecov.setTransporterid(transid);
						scprecov.setCreatedby(user.getUserid());
						scprecov.setCreatedon(date);
						scprecov.setPoints(recovparam.getPts());
						scprecov.setUpdatedby(user.getUserid());
						scprecov.setUpdatedon(date);
						scprecov.setRecoveryid(recoveryp.getRecoveryid());
						if (!Objects.isNull(recovparam.getNbmonths()) && recovparam.getNbmonths() > 0) {
							scprecov.setNbmonths(recovparam.getNbmonths());
						}
						scprecov.setStatus(1);
					} else {
						if (!Objects.isNull(recovparam.getNbmonths()) && recovparam.getNbmonths() > 0) {
							scprecov.setNbmonths(recovparam.getNbmonths());
						} else {
							scprecov.setNbmonths(Short.valueOf("0"));
						}
						if (!Objects.isNull(recovparam.getPts())) {
							scprecov.setPoints(recovparam.getPts());
						} else {
							scprecov.setPoints(0);
						}
						scprecov.setUpdatedby(user.getUserid());
						scprecov.setUpdatedon(date);
						if (Objects.isNull(scprecov.getCreatedby())) {
							scprecov.setCreatedby(user.getUserid());
							scprecov.setCreatedon(date);
						}
					}
					try {
						scprecov = this.scprecovR.saveAndFlush(scprecov);
					} catch (Exception ex6) {
						ex6.printStackTrace();
					}
				}
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(new Success(StaticValues.success, StaticValues.success_Int));
	}

	@Override
	public List visualParamMap(final int clientid, final int affiliateid) {
		final String query = this.environment.getRequiredProperty("scp.visualparamlist");
		return this.jdbcTemplate.queryForList(query, new Object[] { clientid, affiliateid });
	}
}
