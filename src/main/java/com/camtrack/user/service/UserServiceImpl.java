// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import com.camtrack.config.Mailer;
import com.camtrack.config.StaticValues;
import com.camtrack.entities.Success;
import com.camtrack.entities.User;
import com.camtrack.mfauthentification.dao.MFATokenManager;
import com.camtrack.user.bean.UserBean;
import com.camtrack.user.bean.UserRoleBean;
import com.camtrack.user.dao.UserDaoInterface;
import com.camtrack.user.repository.AllalertlevelRepository;
import com.camtrack.user.repository.CustomerRepository;
import com.camtrack.user.repository.DriverRepository;
import com.camtrack.user.repository.EmailconfigRepository;
import com.camtrack.user.repository.GroupMemberRepository;
import com.camtrack.user.repository.GroupsRepository;
import com.camtrack.user.repository.LanguageRepository;
import com.camtrack.user.repository.ListconfigtypesRepository;
import com.camtrack.user.repository.LogusersRepository;
import com.camtrack.user.repository.LostpasswordRepository;
import com.camtrack.user.repository.OauthAccessTokenRepository;
import com.camtrack.user.repository.ParameterconfigRepository;
import com.camtrack.user.repository.ParametertypeRepository;
import com.camtrack.user.repository.ReelroleusersRepository;
import com.camtrack.user.repository.StatusRepository;
import com.camtrack.user.repository.UserightsRepository;
import com.camtrack.user.repository.UserlogsactivityRepository;
import com.camtrack.user.repository.UsersRepository;
import com.camtrack.user.repository.VehicleRepository;
import com.camtrack.user.repository.VisitorRepository;

@Service("userService")
@CacheConfig(cacheNames = "usercaches")
public class UserServiceImpl implements UserServiceInterface {
	
	@Autowired
	OauthAccessTokenRepository accesstokenR;
	@Autowired
	AllalertlevelRepository allalertLR;
	@Autowired
	CustomerRepository customR;
	@Autowired
	DriverRepository drivR;
	@Autowired
	private Environment environment;
	@Autowired
	FileService fileservices;
	@Autowired
	GroupMemberRepository grpMbRepo;
	@Autowired
	GroupsRepository grpRepo;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	LanguageRepository langR;
	@Autowired
	ListconfigtypesRepository listconfig;
	@Autowired
	LogusersRepository logRepo;
	@Autowired
	Mailer mailTemplate;
	@Value("${user.password.maxdaysvalidation}")
	private Integer maxdaysvalidation;
	@Autowired
	MFATokenManager mfaTokenManager;
	@Autowired
	ParameterconfigRepository paramR;
	@Autowired
	ParametertypeRepository paramtypeR;
	@Autowired
	ReelroleusersRepository reelroleR;
	@Autowired
	UserightsRepository rightR;
	@Autowired
	StatusRepository statusR;
	@Autowired
	TokenStore tokenStore;
	@Autowired
	UserDaoInterface userDaoInterface;
	@Autowired
	UserightsRepository userightsRepo;
	@Autowired
	UserlogsactivityRepository userlogRepo;
	@Autowired
	LostpasswordRepository lostpasswordR;
	@Autowired
	UsersRepository usersR;
	@Autowired
	VehicleRepository vehR;

	@Autowired
	VisitorRepository visitR;

	@Autowired
	GroupMemberRepository grpM;
	@Autowired
	EmailconfigRepository emailconfigR;

	@Override
	@Cacheable("alllisttypeconfig")
	public ResponseEntity<?> alllisttypeconfig() {
		return ResponseEntity.status(HttpStatus.OK).body(this.listconfig.findAll());
	}

	@Override
	public int changePassword(final String userId, final String newpassword) throws Exception {
		return this.userDaoInterface.changePassword(userId, newpassword);
	}

	

	@Override
	public int deactivateUser(final int qid) {
		return this.userDaoInterface.deactivateUser(qid);
	}

	@Override
	public int existemailid(final UserBean userBean) {
		return this.userDaoInterface.existemailid(userBean);
	}

	@Override
	public UserBean getUserById(final UserBean userBean) {
		return this.userDaoInterface.getUserById(userBean);
	}

	@Override
	public UserRoleBean getUserRoleById(final UserRoleBean userRoleBean) throws Exception {
		return this.userDaoInterface.getUserRoleById(userRoleBean);
	}

	@Override
	public List getUsersBySearchCriteria(final String clientid, final String affiliateid, final String transporterid)
			throws Exception {
		return this.userDaoInterface.getUsersBySearchCriteria(clientid, affiliateid, transporterid);
	}

	

	@Override
	public ResponseEntity<?> logout(User user) {
		try {
			user.setIsconn(false);
			usersR.saveAndFlush(user);
			this.accesstokenR.deleteOauthAccessToken(user.getUsername());
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Success(StaticValues.success, StaticValues.success_Int));
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
					.body(new Success(StaticValues.unnexpecdErrorOcured, StaticValues.unnexpecdErrorOcured_Int));
		}
	}



	
}
