//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.usertype.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.camtrack.config.Mailer;
import com.camtrack.usertype.bean.UserTypeBean;
import com.camtrack.usertype.dao.UserTypeDaoInterface;

@Service("usertypeService")
public class UserTypeServiceImpl implements UserTypeServiceInterface {
	@Autowired
	Mailer mailTemplate;
	@Autowired
	UserTypeDaoInterface usertypeDaoInterface;

	@Override
	public int deactivateUserType(final int item) {
		return this.usertypeDaoInterface.deactivateUserType(item);
	}

	@Override
	public int deleteUserTypeByIds(final String l) {
		return this.usertypeDaoInterface.deleteUserTypeByIds(l);
	}

	@Override
	public int existUserTypeName(final String userTypeName, final String dumusertype) {
		return this.usertypeDaoInterface.existUserTypeName(userTypeName, dumusertype);
	}

	@Override
	public UserTypeBean getUserTypeById(final UserTypeBean usertypebean) {
		return this.usertypeDaoInterface.getUserTypeById(usertypebean);
	}

	@Override
	public List loadUserTypes() {
		return this.usertypeDaoInterface.loadUserTypes();
	}

	@Override
	public int saveUserType(final UserTypeBean usertype) {
		return this.usertypeDaoInterface.saveUserType(usertype);
	}

	@Override
	public int updateUserType(final UserTypeBean usertype) {
		return this.usertypeDaoInterface.updateUserType(usertype);
	}
}
