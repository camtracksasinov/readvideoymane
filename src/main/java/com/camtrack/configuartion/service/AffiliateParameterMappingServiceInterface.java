//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.configuartion.service;

import java.util.List;

import com.camtrack.configuartion.bean.AffiliateParameterMappingBean;

public interface AffiliateParameterMappingServiceInterface {
	List getAffiliateParameterMap(final int customerid, final int affiliateid) throws Exception;

	int mapAffiliateAndParameter(final AffiliateParameterMappingBean mappingBean) throws Exception;
}
