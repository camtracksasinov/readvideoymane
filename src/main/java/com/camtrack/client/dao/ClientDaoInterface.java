//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.client.dao;

import java.util.List;
import java.util.Map;

import com.camtrack.client.bean.ClientBean;

public interface ClientDaoInterface {
	int deactivateClient(final int qid, final int userid);

	List fetchClients(final int roleId, final int userId);

	List getAllCitiesOfState(final int stateid);

	List getAllCounties();

	List getAllLanguages();

	List getAllStatesOfCountry(final int countryid);

	Map getClientById(final int clientid);

	List getClientByListId(final String listclientid);

	int saveClient(final ClientBean referral);

	int updateClient(final ClientBean referral) throws Exception;

	int updateLogoUrl(final int customerid, final String logourl);
}
