//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.client.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.camtrack.client.bean.ClientBean;
import com.camtrack.entities.User;

public interface ClientServiceInterface {
	Integer deactivateClient(final Integer userid);

	List fetchClients();

	List getAllCitiesOfState(final int stateid);

	List getAllCounties();

	List getAllLanguages();

	List getAllStatesOfCountry(final int countryid);

	Map getClientById(final int clientid);

	List getClientByListId(final String clientid);

	ResponseEntity<?> hierachicallistclientuser(final User user);

	ResponseEntity<?> saveClient(final ClientBean referral, final User user);

	int updateClient(final ClientBean referral) throws Exception;

	ResponseEntity<?> updateLogoClient(final Integer clientId, final MultipartFile file, final User user);

	int updateLogoUrl(final int customerid, final String logourl);
}
