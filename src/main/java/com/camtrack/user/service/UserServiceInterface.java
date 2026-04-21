//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.camtrack.bean.ListIdBean;
import com.camtrack.bean.Profilroles;
import com.camtrack.bean.Userallidroles;
import com.camtrack.entities.User;
import com.camtrack.user.bean.UserBean;
import com.camtrack.user.bean.UserRoleBean;

public interface UserServiceInterface {
	ResponseEntity<?> alllisttypeconfig();

	int changePassword(final String userId, final String newpassword) throws Exception;

	int deactivateUser(final int qid);

	int existemailid(final UserBean userBean);
	UserBean getUserById(UserBean userBean);

	UserRoleBean getUserRoleById(final UserRoleBean userRoleBean) throws Exception;

	List getUsersBySearchCriteria(final String clientid, final String affiliateid, final String transporterid)
			throws Exception;


	ResponseEntity<?> logout(User user);

}
