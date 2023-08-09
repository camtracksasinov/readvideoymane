//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.configuartion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.camtrack.configuartion.bean.AffiliateParameterMappingBean;
import com.camtrack.configuartion.dao.AffiliateParameterMappingDaoInterface;

@Service("mappingService")
public class AffiliateParameterMappingServiceImpl implements AffiliateParameterMappingServiceInterface {
	@Autowired
	AffiliateParameterMappingDaoInterface AffiliateParameterMappingDaoInterface;

	@Override
	public List getAffiliateParameterMap(final int customerid, final int affiliateid) throws Exception {
		return this.AffiliateParameterMappingDaoInterface.getAffiliateParameterMap(customerid, affiliateid);
	}

	@Override
	public int mapAffiliateAndParameter(final AffiliateParameterMappingBean mappingBean) throws Exception {
		return this.AffiliateParameterMappingDaoInterface.mapAffiliateAndParameter(mappingBean);
	}
}
