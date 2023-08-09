//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.usertype.dao;

import java.util.List;

import com.camtrack.usertype.bean.UserTypeBean;

public interface UserTypeDaoInterface {
	int deactivateUserType(final int item);

	int deleteUserTypeByIds(final String l);

	int existUserTypeName(final String userTypeName, final String dumusertype);

	UserTypeBean getUserTypeById(final UserTypeBean usertypebean);

	List loadUserTypes();

	int saveUserType(final UserTypeBean usertype);

	int updateUserType(final UserTypeBean usertype);
}
