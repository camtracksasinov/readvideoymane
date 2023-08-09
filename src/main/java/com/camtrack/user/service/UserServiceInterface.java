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

	ResponseEntity<?> createorupdateuser(final Userallidroles alluseroles, final User user);

	int deactivateUser(final int qid);

	int existemailid(final UserBean userBean);

	// int existusername(final UserBean userBean);

	ResponseEntity<?> fechUsers(final User user);

	ResponseEntity<?> FetchAffilatesList(final User user, final List<Integer> listcostoerid);

	ResponseEntity<?> FetchClientList(final User user);

	ResponseEntity<?> FetchTransporterList(final User user, final List<Integer> list);

	List fetchUserRoles(final String rolelevel);

	List fetchUsers(final User user);

	ResponseEntity<?> FetchVehicleList(final User user, final List<Integer> collect);

	List getaffilatesByCusId(final String cusid) throws Exception;

	List getDriverDetailsByVehicleId(final int vehicleid) throws Exception;

	List gettransprotersbyAffiId(final String affiliateid) throws Exception;

	UserBean getUserById(UserBean userBean);

	UserRoleBean getUserRoleById(final UserRoleBean userRoleBean) throws Exception;

	List getUsersBySearchCriteria(final String clientid, final String affiliateid, final String transporterid)
			throws Exception;

	ResponseEntity<?> hierarchie(User user, Integer position);

	ResponseEntity<?> logout(User user);

	ResponseEntity<?> logusers(final User user, final String datedebut, final String datefin, final List<Integer> lis);

	ResponseEntity<?> Profilroles(Profilroles alluseroles, User user);

	int saveUser(final UserBean userBean) throws ParseException;

	ResponseEntity<?> updateLogoUser(final MultipartFile file, final User user);

	int updateUser(final UserBean userBean);

	int updateUserRole(final UserRoleBean userRoleBean) throws Exception;

	List<Map<String, Object>> useractivity(final User user, final String datedebut, final String datefin,
			final ListIdBean lis, final Short typeconfig);

}
