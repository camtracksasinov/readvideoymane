//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.dashboard.dao;

import java.util.List;
import java.util.Map;

public interface DashboardDaoInterface {
	Map checkPermissions(final String userroleid, final String menuId);

	List fetchAllMenus(final int roleid, final int parent) throws Exception;

	List fetchVehicleByTransporter(final String transid);
}
