//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.dao;

import java.text.ParseException;
import java.util.List;

import com.camtrack.entities.User;
import com.camtrack.user.bean.UserBean;
import com.camtrack.user.bean.UserRoleBean;

public interface UserDaoInterface {
	int changePassword(final String userId, final String newpassword) throws Exception;

	int deactivateUser(final int qid);

	int existemailid(final UserBean userBean);

	// int existusername(final UserBean userBean);

	List FetchAffilatesList(final User user);

	List FetchClientList(final User user);

	List FetchTransporterList(final User user);

	List fetchUserRoles(final String rolelevel);

	List fetchUsers(final User user);

	List getaffilatesByCusId(final String cusid) throws Exception;

	List getDriverDetailsByVehicleId(final int vehicleid) throws Exception;

	List gettransprotersbyAffiId(final String affiliateid) throws Exception;

	UserBean getUserById(UserBean userBean);

	UserRoleBean getUserRoleById(final UserRoleBean userRoleBean) throws Exception;

	List getUsersBySearchCriteria(final String clientid, final String affiliateid, final String transporterid)
			throws Exception;

	int saveUser(final UserBean userBean) throws ParseException;

	int updateUser(final UserBean userBean);

	int updateUserRole(final UserRoleBean userRoleBean) throws Exception;
}
