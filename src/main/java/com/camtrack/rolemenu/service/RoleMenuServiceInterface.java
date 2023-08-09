//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.rolemenu.service;

import org.springframework.http.ResponseEntity;

import com.camtrack.bean.CreateNewUserRole;
import com.camtrack.entities.User;

public interface RoleMenuServiceInterface {
	ResponseEntity<?> createorupdatenewrole(final User user, final CreateNewUserRole newrole);

	int deactivateRoleMenu(final String userRoleId);

	ResponseEntity<?> fetchALLMenus();

	ResponseEntity<?> fetchMenus(final int userRoleId);

	ResponseEntity<?> fetchUserRoles(final User user);

	ResponseEntity<?> listallrolewithaccess(final User user);

	ResponseEntity<?> listtyperole();
}
