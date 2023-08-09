//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.application.service;

import java.util.List;

import com.camtrack.application.bean.ApplicationBean;

public interface ApplicationServiceInterface {
	int deactivateApplicationMapping(final int clientaffapplicationid) throws Exception;

	List fetchAllApplications() throws Exception;

	List loadClientApplicationMap(final int customerid, final int affiliateid, final int applicationid)
			throws Exception;

	int mapClientApplication(final ApplicationBean applicationBean) throws Exception;
}
