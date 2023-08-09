//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.affiliate.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.camtrack.affiliate.bean.AffiliateBean;
import com.camtrack.affiliate.bean.AffiliateInfos;
import com.camtrack.entities.User;

public interface AffiliateServiceInterface {
	Integer deactivateAffiliate(final Integer qid, final Integer userid);

	List fetchAffiliatesbycostomer(final int customerid);

	List fetchAffiliatesbyListcostomer(final String listcustomerid);

	Map getAffiliateById(final int affiliateid);

	List getAffiliateByListId(final String listaffiliateid);

	ResponseEntity<?> hierachicallistaffiliateuser(final User user);

	ResponseEntity<?> saveAffiliate(final AffiliateInfos affiliate, final User user);

	int updateAffiliate(final AffiliateBean referral);
}
