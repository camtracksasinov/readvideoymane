//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.transporter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.camtrack.config.Mailer;
import com.camtrack.config.Utils;
import com.camtrack.entities.User;
import com.camtrack.transporter.bean.DriverBean;
import com.camtrack.transporter.bean.TransporterBean;
import com.camtrack.transporter.dao.TransporterDaoInterface;
import com.camtrack.transporter.repository.TransporterRepository;
import com.camtrack.user.repository.DriverRepository;

@Service("transporterService")
@CacheConfig(cacheNames = "transportercache")
public class TransporterServiceImpl implements TransporterServiceInterface {
	@Autowired
	DriverRepository driverR;
	@Autowired
	Mailer mailTemplate;
	@Autowired
	TransporterDaoInterface transporterDao;
	@Autowired
	TransporterRepository transR;

	@Override
	public Integer deactivateTransporter(final Integer transporterid) {
		return this.transR.updatestatustransporter(transporterid);
	}

	@Override
	@Cacheable("fetchalldrivers")
	public List fetchalldrivers() {
		return this.transporterDao.fetchalldrivers();
	}

	@Override
	public List fetchallTransporters() {
		return this.transporterDao.fetchallTransporters();
	}

	@Override
	public List fetchallvehicles() {
		return this.transporterDao.fetchallvehicles();
	}

	@Override
	public ResponseEntity<?> FetchDriverList(final User user, final List<Integer> collect) {
		final List<DriverBean> listdriverbean = new ArrayList<>();
		if (Objects.isNull(collect) || collect.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body((Object) new ArrayList());
		}
		final List<Object[]> listobject = this.driverR.alldriverinfos(collect);
		if (!Objects.isNull(listobject) && !listobject.isEmpty()) {
			for (final Object[] objects : listobject) {
				final DriverBean bean = new DriverBean();
				bean.setDriverid(Utils.castIntegerObject(objects[0]));
				bean.setDrivername(Utils.ObjectToString(objects[1]));
				bean.setTransporterid(Utils.castIntegerObject(objects[2]));
				bean.setDriverkeycode(Utils.ObjectToString(objects[3]));
				bean.setVehicleId(Utils.castIntegerObject(objects[4]));
				bean.setStatus(Utils.castIntegerObject(objects[5]));
				listdriverbean.add(bean);
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body((Object) listdriverbean);
	}

	@Override
	public List fetchTransporters(final String customerid, final String affiliateid) {
		return this.transporterDao.fetchTransporters(customerid, affiliateid);
	}

	@Override
	public List getAllCustomerAffiliates(final int customerid) {
		return this.transporterDao.getAllCustomerAffiliates(customerid);
	}

	@Override
	public List getAllCustomers() {
		return this.transporterDao.getAllCustomers();
	}

	@Override
	public Map getTransporterById(final int transporterid) {
		return this.transporterDao.getTransporterById(transporterid);
	}

	@Override
	public List getTransporterByListId(final String listtransporterid) {
		return this.transporterDao.getTransporterByListId(listtransporterid);
	}

	@Override
	public int saveTransporter(final TransporterBean referral) {
		return this.transporterDao.saveTransporter(referral);
	}

	@Override
	public int updateTransporter(final TransporterBean referral) {
		return this.transporterDao.updateTransporter(referral);
	}
}
