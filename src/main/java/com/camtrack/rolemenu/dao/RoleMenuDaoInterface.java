//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.rolemenu.dao;

import java.util.List;

import com.camtrack.rolemenu.bean.RoleMenuBean;

public interface RoleMenuDaoInterface {
	int deactivateRoleMenu(final String userRoleId);

	List fetchAllMenus();

	List fetchMenus(final int userRoleId);

	List fetchUserRoles();

	int RoleMenumap(final RoleMenuBean rolemenuBean);
}
