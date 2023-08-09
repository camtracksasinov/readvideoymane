//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.transporter.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.camtrack.entities.User;
import com.camtrack.transporter.bean.TransporterBean;

public interface TransporterServiceInterface {
	Integer deactivateTransporter(final Integer transporterid);

	List fetchalldrivers();

	List fetchallTransporters();

	List fetchallvehicles();

	ResponseEntity<?> FetchDriverList(final User user, final List<Integer> collect);

	List fetchTransporters(final String customerid, final String affiliateid);

	List getAllCustomerAffiliates(final int customerid);

	List getAllCustomers();

	Map getTransporterById(final int transporterid);

	List getTransporterByListId(final String listtransporterid);

	int saveTransporter(final TransporterBean referral);

	int updateTransporter(final TransporterBean referral);
}
