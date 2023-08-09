//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.affiliate.dao;

import java.util.List;
import java.util.Map;

import com.camtrack.affiliate.bean.AffiliateBean;

public interface AffiliateDaoInterface {
	int deactivateAffiliate(final int qid, final int userid);

	List fetchAffiliatesbycostomer(final int customerid);

	List fetchAffiliatesbyListcostomer(final String listcustomerid);

	Map getAffiliateById(final int affiliateid);

	List getAffiliateByListId(final String listaffiliateid);

	int saveAffiliate(final AffiliateBean referral);

	int updateAffiliate(final AffiliateBean referral);
}
