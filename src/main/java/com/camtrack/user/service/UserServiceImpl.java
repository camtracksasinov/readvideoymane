//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.camtrack.affiliate.repository.CustomeraffiliateRepository;
import com.camtrack.bean.CreateEntityRole;
import com.camtrack.bean.ListIdBean;
import com.camtrack.bean.Profilroles;
import com.camtrack.bean.Userallidroles;
import com.camtrack.config.Mailer;
import com.camtrack.config.StaticValues;
import com.camtrack.config.Utils;
import com.camtrack.entities.Accessrights;
import com.camtrack.entities.Allalertlevel;
import com.camtrack.entities.Customer;
import com.camtrack.entities.CustomerDao;
import com.camtrack.entities.Customeraffiliate;
import com.camtrack.entities.Driver;
import com.camtrack.entities.Entities;
import com.camtrack.entities.GroupMembers;
import com.camtrack.entities.Groups;
import com.camtrack.entities.Listconfigtypes;
import com.camtrack.entities.Logusers;
import com.camtrack.entities.Parametertype;
import com.camtrack.entities.Reelroleusers;
import com.camtrack.entities.Success;
import com.camtrack.entities.Transporter;
import com.camtrack.entities.User;
import com.camtrack.entities.Userights;
import com.camtrack.entities.Userlogsactivity;
import com.camtrack.entities.Userrole;
import com.camtrack.entities.Vehicle;
import com.camtrack.mfauthentification.dao.MFATokenManager;
import com.camtrack.rolemenu.repository.AccessRightRepository;
import com.camtrack.rolemenu.repository.MenuRepository;
import com.camtrack.transporter.repository.TransporterRepository;
import com.camtrack.user.bean.Hierarchies;
import com.camtrack.user.bean.UserBean;
import com.camtrack.user.bean.UserRoleBean;
import com.camtrack.user.dao.UserDaoInterface;
import com.camtrack.user.repository.AllalertlevelRepository;
import com.camtrack.user.repository.CustomerRepository;
import com.camtrack.user.repository.DriverRepository;
import com.camtrack.user.repository.GroupMemberRepository;
import com.camtrack.user.repository.GroupsRepository;
import com.camtrack.user.repository.LanguageRepository;
import com.camtrack.user.repository.ListconfigtypesRepository;
import com.camtrack.user.repository.LogusersRepository;
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
	AccessRightRepository accessR;
	@Autowired
	OauthAccessTokenRepository accesstokenR;
	@Autowired
	CustomeraffiliateRepository affR;
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
	MenuRepository menuR;
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
	TransporterRepository transR;
	@Autowired
	UserDaoInterface userDaoInterface;
	@Autowired
	UserightsRepository userightsRepo;
	@Autowired
	UserlogsactivityRepository userlogRepo;
	@Autowired
	UsersRepository usersR;
	@Autowired
	VehicleRepository vehR;

	@Autowired
	VisitorRepository visitR;

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
	public ResponseEntity<?> createorupdateuser(final Userallidroles alluseroles, final User usert) {
		final String username = alluseroles.getUsername();
		final String names = alluseroles.getNames();
		final String newpassword = alluseroles.getNewpassword();
		final String newemail = alluseroles.getNewemail();
		final Integer language = alluseroles.getLanguageid();
		final String contacts = alluseroles.getContacts();
		final String sexex = alluseroles.getSexe();
		Boolean isadmin = alluseroles.getIsadmin();
		Character sexe = null;
		try {
			if (Objects.isNull(sexex) || sexex.length() == 0) {
				sexe = null;
			} else {
				sexe = sexex.charAt(0);
			}
		} catch (Exception ex3) {
		}
		final Boolean activateuser = alluseroles.getActivateuser();
		final Integer userid = alluseroles.getUserid();
		final Integer roleuser = alluseroles.getRoleid();
		Reelroleusers role = null;
		boolean updateusername = false;
		if (!Objects.isNull(roleuser)) {
			role = this.reelroleR.findById(roleuser).orElse(null);
		}
		boolean updateuser = false;
		Boolean createornot;
		User users;
		boolean ignorefirst = false;
		if (!Objects.isNull(userid)) {
			createornot = false;
			users = this.usersR.findById(userid).orElse(null);
			updateuser = true;
			User.init((boolean) createornot, usert);
			if (Objects.isNull(users)) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new Success(StaticValues.UsersId, StaticValues.UsersId_Int));
			}

			if (!Objects.isNull(newemail)) {
				final User userss = this.usersR.findByEmail(newemail).orElse(null);
				if (!Objects.isNull(userss) && userss.getUserid() != users.getUserid()) {
					return ResponseEntity.status(HttpStatus.OK)
							.body(new Success(StaticValues.EmailAlreadyExists, StaticValues.EmailAlreadyExists_Int));
				}
				users.setEmailid(newemail, (boolean) createornot);
			}
			/**
			 * if (!Objects.isNull(username)) { final User users2 =
			 * this.usersR.findByUserName(username).orElse(null); if
			 * (!Objects.isNull(users2) && users2.getUserid() != users.getUserid()) { return
			 * ResponseEntity.status(HttpStatus.OK).body( new
			 * Success(StaticValues.UsernameAlreadyExists,
			 * StaticValues.UsernameAlreadyExists_Int)); } updateusername = true;
			 * users.setUsername(username, (boolean) createornot); }
			 */

			/**
			 * if (!Objects.isNull(alluseroles.getEnabled())) {
			 * users.setEnabled(alluseroles.getEnabled(), createornot); }
			 */

			if (!Objects.isNull(alluseroles.getMfa()) && alluseroles.getMfa()) {
				if (Objects.isNull(users.isMfa()) || !users.isMfa()) {
					ignorefirst = true;
					users.setMfa(true, createornot);
					users.setSecret(mfaTokenManager.generateSecretKey());
					users.setFirst(true, createornot);
					users.setIsconn(false);
				}
			} else {
				users.setMfa(false, createornot);
				users.setFirst(false, createornot);
			}
			Integer roles = usert.getReelrole().getIds();
			if ((roles == Utils.adminRoleId) || ((!Objects.isNull(usert.getIsadmin())) && usert.getIsadmin())) {
				if (!Objects.isNull(isadmin)) {
					users.setIsadmin(isadmin);
				}

				if (!Objects.isNull(role)) {
					users.setReelrole(role, (boolean) createornot);
				}

				if (!Objects.isNull(alluseroles.getUnblock())) {
					if (alluseroles.getUnblock()) {
						users.setAccountNonLocked(alluseroles.getUnblock(), createornot);
						users.setLockTime(null);
						users.setFlockn(Short.valueOf("0"));
					} else {
						users.setAccountNonLocked(alluseroles.getUnblock(), createornot);
						users.setLockTime(new Date());
						if (users.getFlockn().intValue() >= 1) {
							users.setFlockn(Short.valueOf("2"));
						} else {
							users.setFlockn(Short.valueOf("1"));
						}

					}
					users.setFailedAttempt((short) 0);
				}
			}
			if (!Objects.isNull(users.isMfa()) && users.isMfa()) {
				// ONLY ADMIN USER AND CLIENT USER REINITALISE FIRST CONNECTED USER

				if ((roles == Utils.adminRoleId) || (roles == Utils.clientRoleId)) {
					if (!Objects.isNull(alluseroles.getFirstconn())) {
						users.setFirst(alluseroles.getFirstconn(), createornot);
						if (alluseroles.getFirstconn()) {
							users.setIsconn(false);
						}
					}
				}

			}
			users.setIsconn(false);
		} else {
			createornot = true;
			users = new User();
			users.setEnabled(true);
			users.setAccountNonLocked(true, createornot);
			users.setLockTime(null);
			users.setFailedAttempt((short) 0);
			users.setSecret(mfaTokenManager.generateSecretKey());
			if (!Objects.isNull(alluseroles.getMfa()) && alluseroles.getMfa()) {
				users.setMfa(true, createornot);
			} else {
				users.setMfa(false, createornot);

			}
			Integer roless = usert.getReelrole().getIds();
			if ((roless == Utils.adminRoleId) || ((!Objects.isNull(usert.getIsadmin())) && usert.getIsadmin())) {
				if (!Objects.isNull(isadmin)) {
					users.setIsadmin(isadmin);
				}
			}
			users.setFirst(true, createornot);
			users.setIsconn(false);

			User.init((boolean) createornot, usert);
			if (!Objects.isNull(username)) {
				final User users2 = this.usersR.findByUserName(username).orElse(null);
				if (!Objects.isNull(users2)) {
					return ResponseEntity.status(HttpStatus.OK).body(
							new Success(StaticValues.UsernameAlreadyExists, StaticValues.UsernameAlreadyExists_Int));
				}
				updateusername = true;
				users.setUsername(username, (boolean) createornot);
			} else {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new Success(StaticValues.UserIdMustNoNull, StaticValues.UserIdMustNoNull_Int));
			}
			if (!Objects.isNull(newemail)) {
				final User userss = this.usersR.findByEmail(newemail).orElse(null);
				if (!Objects.isNull(userss)) {
					return ResponseEntity.status(HttpStatus.OK)
							.body(new Success(StaticValues.EmailAlreadyExists, StaticValues.EmailAlreadyExists_Int));
				}
				users.setEmailid(newemail, (boolean) createornot);
			}
			if (Objects.isNull(role)) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new Success(StaticValues.UserroledoesntExists, StaticValues.UserroledoesntExists_Int));
			}
			users.setReelrole(role, (boolean) createornot);
		}
		/**
		 * if(!Objects.isNull(alluseroles.getMfa())&&alluseroles.getMfa()) {
		 * users.setMfa(true); } else { users.setMfa(false); }
		 */

		if (!Objects.isNull(contacts)) {
			users.setContacts(contacts, (boolean) createornot);
		}
		if (!Objects.isNull(names)) {
			users.setName(names, (boolean) createornot);
		}
		if (!Objects.isNull(contacts)) {
			users.setContacts(contacts, (boolean) createornot);
		}
		if (!Objects.isNull(newpassword) && !newpassword.trim().isEmpty()) {
			if (!Utils.chechpassword(newpassword)) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new Success(StaticValues.WrongPasswordFormat, StaticValues.WrongPasswordFormat_Int));

			}
			users.setPassword(Utils.encodeBcriptPassword(newpassword), maxdaysvalidation, createornot);
			if (users.isFirst() && !createornot && !ignorefirst) {
				users.setFirst(false, createornot);
			}
			if (createornot) {
				users.setVsspassword(newpassword);
			}
		}
		if (!Objects.isNull(sexe)) {
			users.setSexe(sexe);
		}

		if (!Objects.isNull(language)) {
			users.setLanguageid(this.langR.findById(language).orElse(null));
		}
		if (!Objects.isNull(activateuser)) {
			users.setEnabled(activateuser, (boolean) createornot);
		}
		final Date date = new Date();
		users.setStatus(1, (boolean) createornot);
		users.setCreatedby(usert.getUserid());
		users.setCreatedon(date);
		users.setUpdatedby(usert.getUserid());
		users.setUpdatedon(date);
		this.accesstokenR.deleteOauthAccessToken(users.getUsername());
		users = this.usersR.saveAndFlush(users);
		final Date datelog = new Date();
		if (Utils.countnumberligne(User.log) > 1) {
			final Userlogsactivity loguser = new Userlogsactivity(
					this.listconfig.findById(Utils.UserTypeconfig()).orElse(null));
			loguser.setDates(datelog);
			loguser.setUserid(usert);
			loguser.setLogsinfos(User.log);
			this.userlogRepo.saveAndFlush(loguser);
		}
		if (updateusername) {
			final Groups grp = this.grpRepo.findById(Long.parseLong(String.valueOf(2))).orElse(null);
			if (Objects.isNull(grp)) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new Success("Problem with Your Database on read Group please contact administrator", -4));
			}
			GroupMembers grpM = this.grpMbRepo.findByUserName(username).orElse(null);
			if (Objects.isNull(grpM)) {
				grpM = new GroupMembers(username, grp);
				this.grpMbRepo.saveAndFlush(grpM);
			}
		}
		role = users.getReelrole();
		final Integer typeRole = role.getIdtyperole().getUserroleid();
		final List<CreateEntityRole> list = alluseroles.getAllentitiesright();
		final Listconfigtypes config = this.listconfig.findById(Utils.UserTypeconfig()).orElse(null);
		final Short status = Short.valueOf("1");
		boolean first = true;

		if (!Objects.isNull(list)) {
			for (final CreateEntityRole createEntityRole : list) {
				if (typeRole == Utils.clientRoleId) {
					if (first) {
						this.userightsRepo.Updatecustomerstatus(users.getUserid());
						first = false;
					}
					final Integer entitiesid = createEntityRole.getEntityid();
					final Customer cus = this.customR.findById(entitiesid).orElse(null);
					if (Objects.isNull(cus)) {
						continue;
					}
					try {
						final List<Integer> listtypealert = Arrays.asList(createEntityRole.getListidtypeexception());
						final List<Parametertype> listpramtype = this.paramtypeR.findAllById(listtypealert);
						for (final Parametertype typeid : listpramtype) {
							try {
								Userights usersr = this.userightsRepo.findUserightsForCustomer(users.getUserid(),
										cus.getCustomerid(), typeid.getParametertypeid()).orElse(null);
								createornot = false;
								if (Objects.isNull(usersr)) {
									usersr = new Userights(status);
									createornot = true;
								}
								Userights.init(createornot, usert);
								usersr.setAlarmlevel(createEntityRole.getAlarmlevel(), createornot);
								usersr.setAlertlevel(createEntityRole.getAlertlevel(), createornot);
								usersr.setRecordlevel(createEntityRole.getRecordlevel(), createornot);
								usersr.setCreatedby(usert.getCustomerid());
								usersr.setCreatedon(date);
								usersr.setIdtypealert(typeid, createornot);
								usersr.setCustomerid(cus, createornot);
								usersr.setUpdatedby(usert.getCustomerid());
								usersr.setUpdatedon(date);
								usersr.setUserid(users, createornot);
								this.userightsRepo.saveAndFlush(usersr);
								if (Utils.countnumberligne(Userights.log) <= 1) {
									continue;
								}
								final Userlogsactivity loguser = new Userlogsactivity(config);
								loguser.setDates(datelog);
								loguser.setUserid(usert);
								loguser.setLogsinfos(Userights.log);
								this.userlogRepo.saveAndFlush(loguser);
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					} catch (Exception ex2) {
						ex2.printStackTrace();
					}
				} else if (typeRole == Utils.affiliateRoleId) {
					if (first) {
						this.userightsRepo.Updateaffiliatestatus(users.getUserid());
						first = false;
					}
					final Integer entitiesid = createEntityRole.getEntityid();
					final Customeraffiliate aff = this.affR.findById(entitiesid).orElse(null);
					if (Objects.isNull(aff)) {
						continue;
					}
					try {
						final List<Integer> listtypealert = Arrays.asList(createEntityRole.getListidtypeexception());
						final List<Parametertype> listpramtype = this.paramtypeR.findAllById(listtypealert);
						for (final Parametertype typeid : listpramtype) {
							try {
								Userights usersr = this.userightsRepo.findUserightsForAffiliate(users.getUserid(),
										aff.getAffiliateid(), typeid.getParametertypeid()).orElse(null);
								createornot = false;
								if (Objects.isNull(usersr)) {
									usersr = new Userights(status);
									createornot = true;
								}
								Userights.init(createornot, usert);
								usersr.setAlarmlevel(createEntityRole.getAlarmlevel(), createornot);
								usersr.setAlertlevel(createEntityRole.getAlertlevel(), createornot);
								usersr.setRecordlevel(createEntityRole.getRecordlevel(), createornot);
								usersr.setCreatedby(usert.getCustomerid());
								usersr.setCreatedon(date);
								usersr.setIdtypealert(typeid, createornot);
								usersr.setAffiliateid(aff, createornot);
								usersr.setUpdatedby(usert.getCustomerid());
								usersr.setUpdatedon(date);
								usersr.setUserid(users, createornot);
								this.userightsRepo.saveAndFlush(usersr);
								if (Utils.countnumberligne(Userights.log) <= 1) {
									continue;
								}
								final Userlogsactivity loguser = new Userlogsactivity(config);
								loguser.setDates(datelog);
								loguser.setUserid(usert);
								loguser.setLogsinfos(Userights.log);
								this.userlogRepo.saveAndFlush(loguser);
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					} catch (Exception ex2) {
						ex2.printStackTrace();
					}
				} else {
					if (typeRole != Utils.transporterRoleId) {
						continue;
					}
					if (first) {
						this.userightsRepo.Updatetransporterstatus(users.getUserid());
						first = false;
					}
					final Integer entitiesid = createEntityRole.getEntityid();
					final Transporter trans = this.transR.findById(entitiesid).orElse(null);
					if (Objects.isNull(trans)) {
						continue;
					}
					try {
						final List<Integer> listtypealert = Arrays.asList(createEntityRole.getListidtypeexception());
						final List<Parametertype> listpramtype = this.paramtypeR.findAllById(listtypealert);
						for (final Parametertype typeid : listpramtype) {
							try {
								Userights usersr = this.userightsRepo.findUserightsForTransporter(users.getUserid(),
										trans.getTransporterid(), typeid.getParametertypeid()).orElse(null);
								createornot = false;
								if (Objects.isNull(usersr)) {
									usersr = new Userights(status);
									createornot = true;
								}
								Userights.init(createornot, usert);
								usersr.setAlarmlevel(createEntityRole.getAlarmlevel(), createornot);
								usersr.setAlertlevel(createEntityRole.getAlertlevel(), createornot);
								usersr.setRecordlevel(createEntityRole.getRecordlevel(), createornot);
								usersr.setCreatedby(usert.getCustomerid());
								usersr.setCreatedon(date);
								usersr.setIdtypealert(typeid, createornot);
								usersr.setTransporterid(trans, createornot);
								usersr.setUpdatedby(usert.getCustomerid());
								usersr.setUpdatedon(date);
								usersr.setUserid(users, createornot);
								this.userightsRepo.saveAndFlush(usersr);
								if (Utils.countnumberligne(Userights.log) <= 1) {
									continue;
								}
								final Userlogsactivity loguser = new Userlogsactivity(config);
								loguser.setDates(datelog);
								loguser.setUserid(usert);
								loguser.setLogsinfos(Userights.log);
								this.userlogRepo.saveAndFlush(loguser);
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					} catch (Exception ex2) {
						ex2.printStackTrace();
					}
				}
			}
		}
		final ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		final HttpServletRequest req = sra.getRequest();
		Logusers log;
		if (Objects.isNull(usert)) {
			log = new Logusers(username, "AUTHENTICATION_SUCCESS", "Request On Update Infos User: " + names, new Date(),
					req.getRemoteAddr());
		} else {
			log = new Logusers(usert.getName(), "AUTHENTICATION_SUCCESS", "Request On Update Infos User: " + names,
					new Date(), req.getRemoteAddr());
		}
		this.logRepo.saveAndFlush(log);
		return ResponseEntity.status(HttpStatus.OK).body(users);
	}

	@Override
	public int deactivateUser(final int qid) {
		return this.userDaoInterface.deactivateUser(qid);
	}

	@Override
	public int existemailid(final UserBean userBean) {
		return this.userDaoInterface.existemailid(userBean);
	}

	/**
	 * @Override public int existusername(final UserBean userBean) { return
	 *           this.userDaoInterface.existusername(userBean); }
	 */

	@Override
	public ResponseEntity<?> fechUsers(final User user) {
		Accessrights access = this.accessR.findAccessRight(user.getReelrole().getIds(), Utils.menuUserId).orElse(null);
		final List<Userights> users = new ArrayList<>();
		List<User> listuser = new ArrayList<>();
		final List<Integer> listentitiesid = new ArrayList<>();
		final List<UserBean> userbeans = new ArrayList<>();
		final User olduser = null;
		final Userights oldright = null;
		List<Entities> listentities = new ArrayList<>();
		boolean special = false;
		if ((!Objects.isNull(user.getIsadmin())) && (user.getIsadmin())) {
			special = true;
		}
		if (access.getAdd() || access.getDelete() || access.getEdit() || access.getView() || special) {
			Integer niveauUser = user.getReelrole().getIdtyperole().getUserroleid();
			if ((niveauUser == Utils.adminRoleId) || ((!Objects.isNull(user.getIsadmin())) && (user.getIsadmin()))) {
				if (niveauUser == Utils.adminRoleId) {
					listuser = this.usersR.findAllActives();
				} else {
					String request = "";
					List<Integer> initInteger;
					String stringInteger;
					if (niveauUser == Utils.clientRoleId) {
						request = "select distinct u2.customerid from userights u2 where u2.customerid is not null and u2.userid = "
								+ user.getUserid();
						initInteger = jdbcTemplate.queryForList(request, Integer.class);
						stringInteger = initInteger.stream().map(String::valueOf).collect(Collectors.joining(","));
						// request = "select ";
						request = "select distinct u3.userid from  userights u3 where u3.customerid in ("
								+ stringInteger
								+ ") or u3.affiliateid in (select cus.affiliateid from customeraffiliate cus where cus.customerid in ("
								+ stringInteger
								+ ")) or u3.transporterid in (select tr.transporterid from transporter tr where tr.affiliateid in (select cuss.affiliateid from customeraffiliate cuss where cuss.customerid in ("
								+ stringInteger + ")))";
					} else if (niveauUser == Utils.affiliateRoleId) {
						request = "select distinct u2.affiliateid from userights u2 where u2.affiliateid is not null and  u2.userid = "
								+ user.getUserid();
						initInteger = jdbcTemplate.queryForList(request, Integer.class);
						stringInteger = initInteger.stream().map(String::valueOf).collect(Collectors.joining(","));

						request = "select distinct u3.userid from  userights u3 where u3.affiliateid in ("
								+ stringInteger
								+ ") or u3.transporterid in (select transporterid from transporter t where affiliateid in ("
								+ stringInteger + "))";
					} else if (niveauUser == Utils.transporterRoleId) {
						request = "select distinct u2.transporterid from userights u2 where u2.transporterid is not null u2.userid = "
								+ user.getUserid();
						initInteger = jdbcTemplate.queryForList(request, Integer.class);
						stringInteger = initInteger.stream().map(String::valueOf).collect(Collectors.joining(","));

						request = "select distinct u3.userid from  userights u3 where u3.transporterid in ("
								+ stringInteger + ")";
					}
					if (!request.isEmpty()) {
						initInteger = jdbcTemplate.queryForList(request, Integer.class);
						listuser = usersR.findAllById(initInteger);
					}
				}
				for (final User users2 : listuser) {
					// if (users2.getUserid() == 622) {}
					try {
						final Integer typeroleid = users2.getReelrole().getIdtyperole().getUserroleid();
						if (typeroleid == Utils.clientRoleId) {
							final List<Integer> listrights = this.userightsRepo
									.findAllReelCustomerIdOfUserId(users2.getUserid());
							listentities = new ArrayList<>();
							for (final Integer rights : listrights) {
								final Entities entities = new Entities();
								entities.setIds(rights);
								final List<Integer> listdata = this.userightsRepo
										.findAllReelTypeAlertIdOfCustomerIdAndUserId(users2.getUserid(), rights);
								entities.setIdtypealert(listdata);
								if (!listdata.isEmpty()) {
									final Userights userights = this.userightsRepo
											.findUserightsForCustomer(users2.getUserid(), rights, listdata.get(0))
											.orElse(null);
									if (Objects.isNull(userights)) {
										continue;
									}
									entities.setRecord(userights.getRecordlevel());
									entities.setAlarm(userights.getAlarmlevel());
									entities.setAlert(userights.getAlertlevel());
									listentities.add(entities);
								}
							}
							final UserBean userbean = new UserBean();
							userbean.setSexe(users2.getSexe());

							userbean.setEmail(users2.getEmailid());
							userbean.setName(users2.getName());
							userbean.setUserid(users2.getUserid());
							// userbean.setUserName(users2.getUsername());
							userbean.setContacts(users2.getContacts());
							userbean.setEntitiesnames(listentities);

							userbean.setEnabled(users2.getEnabled());
							userbean.setUnblock(users2.isAccountNonLocked());
							userbean.setFirstconn(users2.isFirst());
							userbean.setMfa(users2.isMfa());
							try {
								final Reelroleusers roles = users2.getReelrole();
								final Userrole typeuser = roles.getIdtyperole();
								userbean.setRoleid(roles.getIds());
								userbean.setRolename(roles.getRolenames());
								userbean.setTyperoleid(typeuser.getUserroleid());
								userbean.setEntitiestype(typeuser.getName());
							} catch (Exception ex) {
							}
							userbeans.add(userbean);
						} else if (typeroleid == Utils.affiliateRoleId) {
							final List<Integer> listrights = this.userightsRepo
									.findAllReelAffiliateIdOfUserId(users2.getUserid());
							listentities = new ArrayList<>();
							for (final Integer rights : listrights) {
								final Entities entities = new Entities();
								entities.setIds(rights);
								final List<Integer> listdata = this.userightsRepo
										.findAllReelTypeAlertIdOfAffiliateIdAndUserId(users2.getUserid(), rights);
								entities.setIdtypealert(listdata);
								if (!listdata.isEmpty()) {
									final Userights userights = this.userightsRepo
											.findUserightsForAffiliate(users2.getUserid(), rights, listdata.get(0))
											.orElse(null);
									if (Objects.isNull(userights)) {
										continue;
									}
									entities.setRecord(userights.getRecordlevel());
									entities.setAlarm(userights.getAlarmlevel());
									entities.setAlert(userights.getAlertlevel());
									listentities.add(entities);
								}
							}
							final UserBean userbean = new UserBean();
							userbean.setSexe(users2.getSexe());
							// userbean.setMfa(users2.isMfa());
							userbean.setEmail(users2.getEmailid());
							userbean.setName(users2.getName());
							userbean.setUserid(users2.getUserid());
							// userbean.setUserName(users2.getUsername());
							userbean.setContacts(users2.getContacts());
							userbean.setEntitiesnames(listentities);
							// userbean.setEnabled(users2.getEnabled());
							userbean.setEnabled(users2.getEnabled());
							userbean.setUnblock(users2.isAccountNonLocked());
							userbean.setFirstconn(users2.isFirst());
							userbean.setMfa(users2.isMfa());
							try {
								final Reelroleusers roles = users2.getReelrole();
								final Userrole typeuser = roles.getIdtyperole();
								userbean.setRoleid(roles.getIds());
								userbean.setRolename(roles.getRolenames());
								userbean.setTyperoleid(typeuser.getUserroleid());
								userbean.setEntitiestype(typeuser.getName());
							} catch (Exception ex2) {
							}
							userbeans.add(userbean);
						} else {
							final List<Integer> listrights = this.userightsRepo
									.findAllReelTransporterIdOfUserId(users2.getUserid());
							listentities = new ArrayList<>();
							for (final Integer rights : listrights) {
								final Entities entities = new Entities();
								entities.setIds(rights);
								final List<Integer> listdata = this.userightsRepo
										.findAllReelTypeAlertIdOfTransporterIdAndUserId(users2.getUserid(), rights);
								entities.setIdtypealert(listdata);
								if (!listdata.isEmpty()) {
									final Userights userights = this.userightsRepo
											.findUserightsForTransporter(users2.getUserid(), rights, listdata.get(0))
											.orElse(null);
									if (Objects.isNull(userights)) {
										continue;
									}
									entities.setRecord(userights.getRecordlevel());
									entities.setAlarm(userights.getAlarmlevel());
									entities.setAlert(userights.getAlertlevel());
									listentities.add(entities);
								}
							}
							final UserBean userbean = new UserBean();
							userbean.setSexe(users2.getSexe());
							// userbean.setMfa(users2.isMfa());
							userbean.setEmail(users2.getEmailid());
							userbean.setName(users2.getName());
							userbean.setUserid(users2.getUserid());
							// userbean.setUserName(users2.getUsername());
							userbean.setContacts(users2.getContacts());
							userbean.setEntitiesnames(listentities);
							// userbean.setEnabled(users2.getEnabled());
							userbean.setEnabled(users2.getEnabled());
							userbean.setUnblock(users2.isAccountNonLocked());
							userbean.setFirstconn(users2.isFirst());
							userbean.setMfa(users2.isMfa());
							try {
								final Reelroleusers roles = users2.getReelrole();
								final Userrole typeuser = roles.getIdtyperole();
								userbean.setRoleid(roles.getIds());
								userbean.setRolename(roles.getRolenames());
								userbean.setTyperoleid(typeuser.getUserroleid());
								userbean.setEntitiestype(typeuser.getName());
							} catch (Exception ex3) {
							}
							userbeans.add(userbean);
						}
					} catch (Exception ex4) {
					}
				}
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(userbeans);
	}

	@Override
	public ResponseEntity<?> FetchAffilatesList(final User user, final List<Integer> listcostoerid) {
		List<Customeraffiliate> result = new ArrayList<>();
		if (!Objects.isNull(listcostoerid) && !listcostoerid.isEmpty()) {
			if (user.getReelrole().getIdtyperole().getUserroleid() == Utils.adminRoleId
					|| user.getReelrole().getIdtyperole().getUserroleid() == Utils.clientRoleId) {
				result = this.affR.findAllActiveCustomer(listcostoerid);
			} else {
				result = this.userightsRepo.findAllCustomerAffiliateHierarchieUser(user.getUserid(), listcostoerid);
				if (Objects.isNull(result) || result.isEmpty()) {
					return ResponseEntity.status(HttpStatus.OK).body(new ArrayList());
				}
			}
		} else {
			result = this.userightsRepo.findAllCustomerAffiliateUser(user.getUserid());
			if (Objects.isNull(result) || result.isEmpty()) {
				return ResponseEntity.status(HttpStatus.OK).body(new ArrayList());
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(Utils.listtobean(result, this.paramR, this.allalertLR));
	}

	@Override
	public ResponseEntity<?> FetchClientList(final User user) {
		final List<CustomerDao> listdao = new ArrayList<>();
		try {
			List<Customer> listcustom;
			if (user.getReelrole().getIdtyperole().getUserroleid() == Utils.adminRoleId) {
				listcustom = this.customR.findAllActiveCustomer();
			} else {
				listcustom = this.userightsRepo.findAllCustomerHierarchieOfUserId(user.getUserid());
			}
			for (final Customer customer : listcustom) {
				try {
					final CustomerDao dao = new CustomerDao();
					final List<Integer> listparameter = this.paramR
							.findAllParameteridForcustomer(customer.getCustomerid());
					dao.setListtypealert(listparameter);
					dao.setAddressline1(customer.getAddressline1());
					dao.setAddressline2(customer.getAddressline2());
					dao.setEmail(customer.getEmail());
					dao.setLogourl(customer.getLogourl());
					dao.setName(customer.getName());
					dao.setNumberofusers(customer.getNumberofusers());
					dao.setTelephone(customer.getTelephone());
					dao.setCustomerid(customer.getCustomerid());
					dao.setStatus(customer.getStatus());
					final Allalertlevel listlevelalert = this.allalertLR
							.findCustomerLevelAlert(customer.getCustomerid()).orElse(null);
					try {
						dao.setAlarmlevel(listlevelalert.getAlarmlevel());
						dao.setAlertlevel(listlevelalert.getAlertlevel());
						dao.setRecordlevel(listlevelalert.getRecordlevel());
					} catch (Exception ex2) {
						dao.setAlarmlevel(null);
						dao.setAlertlevel(null);
						dao.setRecordlevel(null);
					}
					listdao.add(dao);
				} catch (Exception exd) {
					exd.printStackTrace();
				}
			}
			return ResponseEntity.status(HttpStatus.OK).body(listdao);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Success(StaticValues.UserroledoesntExists, StaticValues.UserroledoesntExists_Int));
		}
	}

	@Override
	public ResponseEntity<?> FetchTransporterList(final User user, final List<Integer> listaffiliateid) {
		List<Transporter> result = new ArrayList<>();
		if (!Objects.isNull(listaffiliateid) && !listaffiliateid.isEmpty()) {
			if (user.getReelrole().getIdtyperole().getUserroleid() == Utils.adminRoleId
					|| user.getReelrole().getIdtyperole().getUserroleid() == Utils.clientRoleId
					|| user.getReelrole().getIdtyperole().getUserroleid() == Utils.affiliateRoleId) {
				result = this.transR.findAllActiveTransporterAffiliate(listaffiliateid);
			} else {
				result = this.userightsRepo.findAllCustomerTransporterHierarchieUser(user.getUserid(), listaffiliateid);
				if (Objects.isNull(result) || result.isEmpty()) {
					return ResponseEntity.status(HttpStatus.OK).body(new ArrayList());
				}
			}
		} else {
			result = this.userightsRepo.findAllTransporterUser(user.getUserid());
			if (Objects.isNull(result) || result.isEmpty()) {
				return ResponseEntity.status(HttpStatus.OK).body(new ArrayList());
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(Utils.listtobeantrans(result));
	}

	@Override
	public List fetchUserRoles(final String rolelevel) {
		return this.userDaoInterface.fetchUserRoles(rolelevel);
	}

	@Override
	public List fetchUsers(final User user) {
		return this.userDaoInterface.fetchUsers(user);
	}

	@Override
	public ResponseEntity<?> FetchVehicleList(final User user, final List<Integer> listtransporter) {
		List<Vehicle> listvehicles = new ArrayList<>();
		if (!Objects.isNull(listtransporter) && !listtransporter.isEmpty()) {
			listvehicles = this.vehR.findAllActiveVehicleTransporter(listtransporter);
		}
		return ResponseEntity.status(HttpStatus.OK).body(Utils.vehtovehbeans(listvehicles));
	}

	@Override
	public List getaffilatesByCusId(final String cusid) throws Exception {
		return this.userDaoInterface.getaffilatesByCusId(cusid);
	}

	@Override
	public List getDriverDetailsByVehicleId(final int vehicleid) throws Exception {
		return this.userDaoInterface.getDriverDetailsByVehicleId(vehicleid);
	}

	@Override
	public List gettransprotersbyAffiId(final String affiliateid) throws Exception {
		return this.userDaoInterface.gettransprotersbyAffiId(affiliateid);
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
	public ResponseEntity<?> hierarchie(User user, Integer position) {
		// TODO Auto-generated method stub
		List<Driver> listdriv;
		List<Transporter> listtrans;
		List<Customeraffiliate> listaff;
		List<Customer> listcus;
		List<Hierarchies> listhierr = new ArrayList<>();
		List<Object[]> listobj;
		Hierarchies small;
		Customeraffiliate aff;
		Customer cus;
		if (position == 5) {
			if (user.getReelrole().getIdtyperole().getUserroleid() == Utils.adminRoleId) {
				listtrans = transR.findAllActiveTransporterComplete();
			} else {
				listtrans = userightsRepo.findAllTransporterUser(user.getUserid());
				if (Objects.isNull(listtrans) || listtrans.isEmpty()) {
					listtrans = new ArrayList<>();
				}
			}
			for (Transporter trans : listtrans) {
				try {
					listobj = drivR.alldriverinfosWithId(trans.getTransporterid());
					aff = trans.getAffiliateid();
					cus = aff.getCustomerid();
					for (Object[] objects : listobj) {
						small = new Hierarchies();
						small.setDrivid(Utils.castIntegerObject(objects[0]));
						small.setDrivnm(Utils.ObjectToString(objects[1]));
						small.setTrspid(trans.getTransporterid());
						small.setTrspnm(trans.getName());
						small.setAffid(aff.getAffiliateid());
						small.setAffnm(aff.getName());
						small.setClid(cus.getCustomerid());
						small.setClnm(cus.getName());
						listhierr.add(small);
					}
				} catch (Exception ex) {

				}
			}
		} else if (position == 4) {
			if (user.getReelrole().getIdtyperole().getUserroleid() == Utils.adminRoleId) {
				listtrans = transR.findAllActiveTransporterComplete();
			} else {
				listtrans = userightsRepo.findAllTransporterUser(user.getUserid());
				if (Objects.isNull(listtrans) || listtrans.isEmpty()) {
					listtrans = new ArrayList<>();
				}
			}

			for (Transporter trans : listtrans) {
				try {
					listobj = vehR.alldriverinfosWithId(trans.getTransporterid());
					aff = trans.getAffiliateid();
					cus = aff.getCustomerid();
					for (Object[] objects : listobj) {
						small = new Hierarchies();
						small.setVehid(Utils.castIntegerObject(objects[0]));
						small.setVehnm(Utils.ObjectToString(objects[1]));
						small.setTrspid(trans.getTransporterid());
						small.setTrspnm(trans.getName());
						small.setAffid(aff.getAffiliateid());
						small.setAffnm(aff.getName());
						small.setClid(cus.getCustomerid());
						small.setClnm(cus.getName());
						listhierr.add(small);
					}
				} catch (Exception ex) {

				}
			}
		} else if (position == 3) {
			if (user.getReelrole().getIdtyperole().getUserroleid() == Utils.adminRoleId) {
				listtrans = transR.findAllActiveTransporterComplete();
			} else {
				listtrans = userightsRepo.findAllTransporterUser(user.getUserid());
				if (Objects.isNull(listtrans) || listtrans.isEmpty()) {
					listtrans = new ArrayList<>();
				}
			}

			for (Transporter trans : listtrans) {
				try {
					// listobj = vehR.alldriverinfosWithId(trans.getTransporterid());
					aff = trans.getAffiliateid();
					cus = aff.getCustomerid();
					small = new Hierarchies();
					small.setTrspid(trans.getTransporterid());
					small.setTrspnm(trans.getName());
					small.setAffid(aff.getAffiliateid());
					small.setAffnm(aff.getName());
					small.setClid(cus.getCustomerid());
					small.setClnm(cus.getName());
					listhierr.add(small);
				} catch (Exception ex) {

				}
			}
		} else if (position == 2) {
			if (user.getReelrole().getIdtyperole().getUserroleid() == Utils.adminRoleId) {
				listaff = affR.findAllActiveCustomerAffiliate();
			} else {
				listaff = this.userightsRepo.findAllCustomerAffiliateUser(user.getUserid());
				if (Objects.isNull(listaff) || listaff.isEmpty()) {
					listaff = new ArrayList<>();
				}
			}

			for (Customeraffiliate affs : listaff) {
				try {
					cus = affs.getCustomerid();
					small = new Hierarchies();
					small.setAffid(affs.getAffiliateid());
					small.setAffnm(affs.getName());
					small.setClid(cus.getCustomerid());
					small.setClnm(cus.getName());
					listhierr.add(small);
				} catch (Exception ex) {

				}
			}
		}

		return ResponseEntity.status(HttpStatus.OK).body(listhierr);
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

	@Override
	public ResponseEntity<?> logusers(final User user, final String datedebut, final String datefin,
			final List<Integer> listuserids) {
		Date date01 = Utils.StringToDate("", datedebut, "yyyy-MM-dd");
		Date date2 = Utils.StringToDate("", datefin, "yyyy-MM-dd");
		if (Objects.isNull(date2) || Objects.isNull(date01)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new Success(StaticValues.wrongdateformat, StaticValues.wrongdateformat_Int));
		}
		date01 = Utils.StringToDateInitDates("", datedebut + " 00:00:00", "yyyy-MM-dd HH:mm:ss", -1);
		date2 = Utils.StringToDateInitDates("", datefin + " 23:59:59", "yyyy-MM-dd HH:mm:ss", 1);
		final Integer niveauUser = user.getReelrole().getIdtyperole().getUserroleid();
		if (Objects.isNull(listuserids)) {
			if (niveauUser == Utils.adminRoleId) {
				return ResponseEntity.status(HttpStatus.OK).body(this.visitR.findAllExists(date01, date2));
			}
		} else {
			List<String> listusername = new ArrayList<>();
			listusername = this.usersR.findListUserName(listuserids);
			if (niveauUser == Utils.adminRoleId) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(this.visitR.findAllByListUsername(listusername, date01, date2));
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(new ArrayList());
	}

	@Override
	public ResponseEntity<?> Profilroles(Profilroles alluseroles, User user) {
		final String names = alluseroles.getNames();
		final String newpassword = alluseroles.getNewpassword();
		final String newemail = alluseroles.getNewemail();
		final Integer language = alluseroles.getLanguageid();
		final String contacts = alluseroles.getContacts();
		final String sexex = alluseroles.getSexe();
		Character sexe = null;
		try {
			if (Objects.isNull(sexex) || sexex.length() == 0) {
				sexe = null;
			} else {
				sexe = sexex.charAt(0);
			}
		} catch (Exception ex) {
		}

		if (!Objects.isNull(newemail)) {
			final User userss = this.usersR.findByEmail(newemail).orElse(null);
			if (!Objects.isNull(userss) && userss.getUserid() != user.getUserid()) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new Success(StaticValues.EmailAlreadyExists, StaticValues.EmailAlreadyExists_Int));
			}
			user.setEmailid(newemail, false);
		}

		if (!Objects.isNull(contacts)) {
			user.setContacts(contacts, false);
		}
		if (!Objects.isNull(names)) {
			user.setName(names, false);
		}
		if (!Objects.isNull(contacts)) {
			user.setContacts(contacts, false);
		}
		if (!Objects.isNull(newpassword) && !newpassword.trim().isEmpty()) {
			if (!Utils.chechpassword(newpassword)) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new Success(StaticValues.WrongPasswordFormat, StaticValues.WrongPasswordFormat_Int));

			}
			user.setPassword(Utils.encodeBcriptPassword(newpassword), maxdaysvalidation, false);
			// user.setPassword(Utils.encodeBcriptPassword(newpassword));

		}
		if (!Objects.isNull(sexe)) {
			user.setSexe(sexe);
		}
		if (!Objects.isNull(language)) {
			user.setLanguageid(this.langR.findById(language).orElse(null));
		}
		user = usersR.saveAndFlush(user);
		this.accesstokenR.deleteOauthAccessToken(user.getUsername());

		return ResponseEntity.status(HttpStatus.OK).body(user);
	}

	@Override
	public int saveUser(final UserBean userBean) throws ParseException {
		return this.userDaoInterface.saveUser(userBean);
	}

	@Override
	public ResponseEntity<?> updateLogoUser(final MultipartFile file, final User cus) {
		if (!Objects.isNull(file)) {
			final String extension = FilenameUtils.getExtension(file.getOriginalFilename());
			final String filename = "users_" + cus.getUsername().replaceAll("\\s+", "") + "." + extension;
			this.fileservices.uploadFile(file, filename);
			User.init(false, cus);
			cus.setPictures(filename, false);
			this.usersR.saveAndFlush(cus);
			final Date datelog = new Date();
			final Listconfigtypes config = this.listconfig.findById(Utils.UserTypeconfig()).orElse(null);
			if (Utils.countnumberligne(User.log) > 1) {
				final Userlogsactivity loguser = new Userlogsactivity(config);
				loguser.setDates(datelog);
				loguser.setUserid(cus);
				loguser.setLogsinfos(User.log);
				this.userlogRepo.saveAndFlush(loguser);
			}
			this.accesstokenR.deleteOauthAccessToken(cus.getUsername());
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Success(StaticValues.success, StaticValues.success_Int));
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(new Success(StaticValues.Filedonestvalide, StaticValues.Filedonestvalide_Int));
	}

	@Override
	public int updateUser(final UserBean userBean) {
		return this.userDaoInterface.updateUser(userBean);
	}

	@Override
	public int updateUserRole(final UserRoleBean userRoleBean) throws Exception {
		return this.userDaoInterface.updateUserRole(userRoleBean);
	}

	@Override
	public List<Map<String, Object>> useractivity(final User user, final String datedebut, final String datefin,
			final ListIdBean listuserids, final Short typeconfig) {
		final Date date01 = Utils.StringToDate("", datedebut, "yyyy-MM-dd");
		Date date2 = Utils.StringToDate("", datefin, "yyyy-MM-dd");
		if (Objects.isNull(date2) || Objects.isNull(date01)) {
			return null;
		}
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date2);
		cal.add(5, 1);
		date2 = cal.getTime();
		final Integer niveauUser = user.getReelrole().getIdtyperole().getUserroleid();
		final List<Map<String, Object>> allresukt = new ArrayList<>();
		if (Objects.isNull(listuserids) || Objects.isNull(listuserids.getListids())
				|| listuserids.getListids().length == 0) {
			if (niveauUser == Utils.adminRoleId) {
				if (Objects.isNull(typeconfig)) {
					String completeRequest = this.environment.getRequiredProperty("userlogsactivity");
					completeRequest = completeRequest.replace("DATE1", datedebut);
					completeRequest = completeRequest.replace("DATE2", Utils.DateFormat(date2, "yyyy-MM-dd"));
					return this.jdbcTemplate.queryForList(completeRequest);
				}
				final Listconfigtypes config = this.listconfig.findById(typeconfig).orElse(null);
				if (!Objects.isNull(config)) {
					String completeRequest = this.environment.getRequiredProperty("userlogsactivitywithtype");
					completeRequest = completeRequest.replace("DATE1", datedebut);
					completeRequest = completeRequest.replace("DATE2", Utils.DateFormat(date2, "yyyy-MM-dd"));
					completeRequest = completeRequest.replace("IDLOGCONFIGSID", typeconfig + "");
					return this.jdbcTemplate.queryForList(completeRequest);
				}
				return null;
			}
		} else if (niveauUser == Utils.adminRoleId || niveauUser == Utils.clientRoleId
				|| niveauUser == Utils.affiliateRoleId || niveauUser == Utils.transporterRoleId) {
			final String listids = ListIdBean.listToString(listuserids.getListids());
			if (!Objects.isNull(listids) && !listids.equalsIgnoreCase("")) {
				if (Objects.isNull(typeconfig)) {
					String completeRequest = this.environment.getRequiredProperty("userlogsactivityinperiod");
					completeRequest = completeRequest.replace("DATE1", datedebut);
					completeRequest = completeRequest.replace("DATE2", Utils.DateFormat(date2, "yyyy-MM-dd"));
					completeRequest = completeRequest.replaceFirst("USERSID", listids);
					return this.jdbcTemplate.queryForList(completeRequest);
				}
				final Listconfigtypes config = this.listconfig.findById(typeconfig).orElse(null);
				if (!Objects.isNull(config)) {
					String completeRequest = this.environment.getRequiredProperty("userlogsactivityinperiodwithtype");
					completeRequest = completeRequest.replace("DATE1", datedebut);
					completeRequest = completeRequest.replace("DATE2", Utils.DateFormat(date2, "yyyy-MM-dd"));
					completeRequest = completeRequest.replaceFirst("USERSID", listids);
					completeRequest = completeRequest.replace("IDLOGCONFIGSID", typeconfig + "");
					return this.jdbcTemplate.queryForList(completeRequest);
				}
				return null;
			}
		}
		return null;
	}
}
