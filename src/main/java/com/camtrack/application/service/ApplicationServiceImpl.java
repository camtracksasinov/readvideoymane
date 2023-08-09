//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.camtrack.application.bean.ApplicationBean;
import com.camtrack.application.dao.ApplicationDaoInterface;

@Service("ApplicationService")
public class ApplicationServiceImpl implements ApplicationServiceInterface {
	@Autowired
	ApplicationDaoInterface applicationDaoInterface;

	@Override
	public int deactivateApplicationMapping(final int clientaffapplicationid) throws Exception {
		return this.applicationDaoInterface.deactivateApplicationMapping(clientaffapplicationid);
	}

	@Override
	public List fetchAllApplications() throws Exception {
		return this.applicationDaoInterface.fetchAllApplications();
	}

	@Override
	public List loadClientApplicationMap(final int customerid, final int affiliateid, final int applicationid)
			throws Exception {
		return this.applicationDaoInterface.loadClientApplicationMap(customerid, affiliateid, applicationid);
	}

	@Override
	public int mapClientApplication(final ApplicationBean applicationBean) throws Exception {
		return this.applicationDaoInterface.mapClientApplication(applicationBean);
	}
}
