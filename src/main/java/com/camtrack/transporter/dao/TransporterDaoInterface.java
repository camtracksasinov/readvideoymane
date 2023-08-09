//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.transporter.dao;

import java.util.List;
import java.util.Map;

import com.camtrack.transporter.bean.TransporterBean;

public interface TransporterDaoInterface {
	int deactivateTransporter(final int qid);

	List fetchalldrivers();

	List fetchallTransporters();

	List fetchallvehicles();

	List fetchTransporters(final String listcustomerid, final String listaffiliateid);

	List getAllCustomerAffiliates(final int customerid);

	List getAllCustomers();

	Map getTransporterById(final int transporterid);

	List getTransporterByListId(final String listtransporterid);

	int saveTransporter(final TransporterBean referral);

	int updateTransporter(final TransporterBean referral);
}
