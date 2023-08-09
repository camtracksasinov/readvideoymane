//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.noauths.service;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.camtrack.config.Encryption;
import com.camtrack.config.HttpRequestResponseUtils;
import com.camtrack.config.StaticValues;
import com.camtrack.config.Utils;
import com.camtrack.entities.Linkloastpassword;
import com.camtrack.entities.Lostpassword;
import com.camtrack.entities.Success;
import com.camtrack.entities.SuccessMFA;
import com.camtrack.entities.User;
import com.camtrack.mfauthentification.bean.MfaTokenData;
import com.camtrack.mfauthentification.dao.MFATokenManager;
import com.camtrack.security.CustomLoginFailureHandler;
import com.camtrack.transporter.repository.TransporterRepository;
import com.camtrack.user.repository.DriverRepository;
import com.camtrack.user.repository.LinkloastpasswordRepository;
import com.camtrack.user.repository.LostpasswordRepository;
import com.camtrack.user.repository.OauthAccessTokenRepository;
import com.camtrack.user.repository.UsersRepository;
import com.camtrack.user.repository.VehicleRepository;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

@Service("noauthservices")
public class NoauthsServices implements NoauthInterface {

	@Autowired
	JavaMailSender _mailSender;
	@Autowired
	OauthAccessTokenRepository accesstokenR;
	@Autowired
	private CustomLoginFailureHandler customAuthenticationSuccessHandler;
	@Autowired
	DriverRepository drivR;
	@Value("${email.maxhour.validatechange}")
	private Integer email_maxhour_validatechange;

	@Value("${linkdnsforexternallogin.dns}")
	private String linkdnsforexternallogin;

	@Autowired
	LinkloastpasswordRepository linkRepo;
	@Value("${server.port}")
	private int localServerPort;

	@Autowired
	LostpasswordRepository lostpassR;
	@Value("${attempsfails.maxfailed}")
	private Integer MAX_FAILED_ATTEMPTS;

	@Value("${attempsfails.initialhour}")
	private Integer MAX_FAILED_ATTEMPTS_initialhour;
	@Value("${attempsfails.limitnumber}")
	private Integer MAX_FAILED_ATTEMPTS_limitnumber;
	@Value("${attempsfails.limitnumberend}")
	private Integer MAX_FAILED_ATTEMPTS_limitnumberend;
	@Value("${attempsfails.secondhour}")
	private Integer MAX_FAILED_ATTEMPTS_secondhour;
	@Value("${user.password.maxdaysvalidation}")
	private Integer maxdaysvalidation;
	@Autowired
	MFATokenManager mfaTokenManager;
	@Autowired
	NoauthInterface noaths;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	TransporterRepository transR;
	@Autowired
	UsersRepository userR;
	@Autowired
	VehicleRepository vehR;

	@Override
	public ResponseEntity<?> chpasswd(String username, String oldpwd, String newpwd) throws Exception {
		try {
			User user = userR.findByUserName(username).orElse(null);
			if (Objects.isNull(user)) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new Success(StaticValues.UsernameIncorrect, StaticValues.UsernameIncorrect_Int));
			}
			Boolean matches = passwordEncoder.matches(oldpwd, user.getPassword());
			if (Objects.isNull(matches) || !matches) {
				return ResponseEntity.status(HttpStatus.OK).body(new Success(StaticValues.UserNameorPasswordIncorrect,
						StaticValues.UserNameorPasswordIncorrect_Int));
			}
			if (!Utils.chechpassword(newpwd)) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new Success(StaticValues.WrongPasswordFormat, StaticValues.WrongPasswordFormat_Int));

			}
			accesstokenR.deleteOauthAccessToken(user.getUsername());

			if (user.isMfa()) {
				return ResponseEntity.status(HttpStatus.OK).body(new SuccessMFA(StaticValues.successConnected,
						StaticValues.successConnected_Int, user.isMfa(), user.isIsconn()));
			} else {

				user.setLpw(true);
				user.setPassword(Utils.encodeBcriptPassword(newpwd), maxdaysvalidation, false);
				user.setIsconn(true);
				user.setNops(Encryption.encrypt(newpwd));
				userR.saveAndFlush(user);

				OkHttpClient client = new OkHttpClient();
				MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
				RequestBody body = RequestBody.create(mediaType,
						"username=" + username + "&password=" + newpwd + "&grant_type=password&client_id=ymaneprod");
				Request request = new Request.Builder().url("http://localhost:" + localServerPort + "/oauth/token")
						.method("POST", body).addHeader("Authorization", "Basic eW1hbmVwcm9kOjEyMzQ1Ng==")
						.addHeader("Content-Type", "application/x-www-form-urlencoded").build();

				ResponseBody response = client.newCall(request).execute().body();
				String responsess = response.string();
				try {
					JSONObject json = new JSONObject(responsess);
					user.setLls(json.getString("access_token"));
				} catch (Exception ex) {
					user.setLls("");
				}
				userR.saveAndFlush(user);
				return ResponseEntity.status(HttpStatus.OK).body(responsess);
			}
		} catch (Exception ex) {
			throw ex;
		}
	}

	@Override
	public ResponseEntity<?> chpasswd0(String randoms, String newpwd) throws Exception {
		try {
			User user = userR.findrandoms(randoms).orElse(null);
			if (Objects.isNull(user)) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new Success(StaticValues.UsernameIncorrect, StaticValues.UsernameIncorrect_Int));
			}

			if (user.getLpw()) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new Success(StaticValues.YourLinkAlreadyUse, StaticValues.YourLinkAlreadyUse_Int));
			}

			Date date = new Date();
			if (Objects.isNull(user.getRdates()) || user.getRdates().before(date)) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new Success(StaticValues.YourLinkExpire, StaticValues.YourLinkExpire_Int));
			}

			if (!Utils.chechpassword(newpwd)) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new Success(StaticValues.WrongPasswordFormat, StaticValues.WrongPasswordFormat_Int));

			}

			accesstokenR.deleteOauthAccessToken(user.getUsername());
			/**
			 * if (user.isMfa()) { return ResponseEntity.status(HttpStatus.OK).body(new
			 * Success(StaticValues.success, StaticValues.success_Int)); /**return
			 * ResponseEntity.status(HttpStatus.OK).body(new
			 * SuccessMFA(StaticValues.successConnected, StaticValues.successConnected_Int,
			 * user.isMfa(), user.isIsconn()));
			 */
			// } else {

			user.setLpw(true);
			user.setPassword(Utils.encodeBcriptPassword(newpwd), maxdaysvalidation, false);
			user.setIsconn(true);
			user.setNops(Encryption.encrypt(newpwd));
			userR.saveAndFlush(user);

			OkHttpClient client = new OkHttpClient();
			MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
			RequestBody body = RequestBody.create(mediaType, "username=" + user.getUsername() + "&password=" + newpwd
					+ "&grant_type=password&client_id=ymaneprod");
			Request request = new Request.Builder().url("http://localhost:" + localServerPort + "/oauth/token")
					.method("POST", body).addHeader("Authorization", "Basic eW1hbmVwcm9kOjEyMzQ1Ng==")
					.addHeader("Content-Type", "application/x-www-form-urlencoded").build();

			ResponseBody response = client.newCall(request).execute().body();
			String responsess = response.string();
			try {
				JSONObject json = new JSONObject(responsess);
				user.setLls(json.getString("access_token"));
			} catch (Exception ex) {
				user.setLls("");
			}
			userR.saveAndFlush(user);

			return ResponseEntity.status(HttpStatus.OK)
					.body(new Success(StaticValues.success, StaticValues.success_Int));
			// return ResponseEntity.status(HttpStatus.OK).body(responsess);
			// }
		} catch (Exception ex) {
			throw ex;
		}
	}

	@Override
	public ResponseEntity<?> chpasswd2(String randoms, String oldpwd, String newpwd) throws Exception {
		try {
			User user = userR.findrandoms(randoms).orElse(null);
			if (Objects.isNull(user)) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new Success(StaticValues.UsernameIncorrect, StaticValues.UsernameIncorrect_Int));
			}

			if (user.getLpw()) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new Success(StaticValues.YourLinkAlreadyUse, StaticValues.YourLinkAlreadyUse_Int));
			}

			Date date = new Date();
			if (Objects.isNull(user.getRdates()) || user.getRdates().before(date)) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new Success(StaticValues.YourLinkExpire, StaticValues.YourLinkExpire_Int));
			}
			Boolean matches = passwordEncoder.matches(oldpwd, user.getPassword());
			if (Objects.isNull(matches) || !matches) {
				return ResponseEntity.status(HttpStatus.OK).body(new Success(StaticValues.UserNameorPasswordIncorrect,
						StaticValues.UserNameorPasswordIncorrect_Int));
			}
			if (!Utils.chechpassword(newpwd)) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new Success(StaticValues.WrongPasswordFormat, StaticValues.WrongPasswordFormat_Int));

			}

			accesstokenR.deleteOauthAccessToken(user.getUsername());

			if (user.isMfa()) {
				return ResponseEntity.status(HttpStatus.OK).body(new SuccessMFA(StaticValues.successConnected,
						StaticValues.successConnected_Int, user.isMfa(), user.isIsconn()));
			} else {

				user.setLpw(true);
				user.setPassword(Utils.encodeBcriptPassword(newpwd), maxdaysvalidation, false);
				user.setIsconn(true);
				user.setNops(Encryption.encrypt(newpwd));
				userR.saveAndFlush(user);

				OkHttpClient client = new OkHttpClient();
				MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
				RequestBody body = RequestBody.create(mediaType, "username=" + user.getUsername() + "&password="
						+ newpwd + "&grant_type=password&client_id=ymaneprod");
				Request request = new Request.Builder().url("http://localhost:" + localServerPort + "/oauth/token")
						.method("POST", body).addHeader("Authorization", "Basic eW1hbmVwcm9kOjEyMzQ1Ng==")
						.addHeader("Content-Type", "application/x-www-form-urlencoded").build();

				ResponseBody response = client.newCall(request).execute().body();
				String responsess = response.string();
				try {
					JSONObject json = new JSONObject(responsess);
					user.setLls(json.getString("access_token"));
				} catch (Exception ex) {
					user.setLls("");
				}
				userR.saveAndFlush(user);
				return ResponseEntity.status(HttpStatus.OK).body(responsess);
			}
		} catch (Exception ex) {
			throw ex;
		}
	}

	@Override
	public ResponseEntity<?> chpasswd3expire(String username, String oldpwd, String newpwd) throws Exception {
		try {
			User user = userR.findByUserName(username).orElse(null);
			if (Objects.isNull(user)) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new Success(StaticValues.UsernameIncorrect, StaticValues.UsernameIncorrect_Int));
			}
			Boolean matches = passwordEncoder.matches(oldpwd, user.getPassword());
			if (Objects.isNull(matches) || !matches) {
				return ResponseEntity.status(HttpStatus.OK).body(new Success(StaticValues.UserNameorPasswordIncorrect,
						StaticValues.UserNameorPasswordIncorrect_Int));
			}
			if (!Utils.chechpassword(newpwd)) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new Success(StaticValues.WrongPasswordFormat, StaticValues.WrongPasswordFormat_Int));

			}
			accesstokenR.deleteOauthAccessToken(user.getUsername());

			user.setLpw(true);
			user.setPassword(Utils.encodeBcriptPassword(newpwd), maxdaysvalidation, false);
			user.setIsconn(true);
			user.setNops(Encryption.encrypt(newpwd));
			userR.saveAndFlush(user);
			/**
			 * OkHttpClient client = new OkHttpClient(); MediaType mediaType =
			 * MediaType.parse("application/x-www-form-urlencoded"); RequestBody body =
			 * RequestBody.create(mediaType, "username=" + user.getUsername() + "&password="
			 * + newpwd + "&grant_type=password&client_id=ymaneprod"); Request request = new
			 * Request.Builder().url("http://localhost:" + localServerPort + "/oauth/token")
			 * .method("POST", body).addHeader("Authorization", "Basic
			 * eW1hbmVwcm9kOjEyMzQ1Ng==") .addHeader("Content-Type",
			 * "application/x-www-form-urlencoded").build();
			 * 
			 * ResponseBody response = client.newCall(request).execute().body(); String
			 * responsess = response.string(); try { JSONObject json = new
			 * JSONObject(responsess); user.setLls(json.getString("access_token")); } catch
			 * (Exception ex) { user.setLls(""); } userR.saveAndFlush(user);
			 */

			return ResponseEntity.status(HttpStatus.OK)
					.body(new Success(StaticValues.success, StaticValues.success_Int));

			// return ResponseEntity.status(HttpStatus.OK).body(responsess);
		} catch (Exception ex) {
			throw ex;
		}
	}

	/**
	 * @Override public ResponseEntity<?> initlostpassword(String username) { //
	 *           System.out.println(username); username =
	 *           Utils.removeEscapeChars(username).replaceAll("^\"|\"$", ""); //
	 *           System.out.println(username); final User user =
	 *           this.userR.findByUserName(username).orElse(null); if
	 *           (Objects.isNull(user)) { return
	 *           ResponseEntity.status(HttpStatus.OK) .body(new
	 *           Success(StaticValues.UsernameIncorrect,
	 *           StaticValues.UsernameIncorrect_Int)); } final String EmailUser =
	 *           user.getEmailid(); if (Objects.isNull(EmailUser)) { return
	 *           ResponseEntity.status(HttpStatus.OK) .body(new
	 *           Success(StaticValues.Emailincorrect,
	 *           StaticValues.Emailincorrect_Int)); } final Linkloastpassword link =
	 *           this.linkRepo.findByUniqueId(Short.valueOf(Utils.defaultLinkUniqueID))
	 *           .orElse(null); if (!Objects.isNull(link)) { Lostpassword lost =
	 *           this.lostpassR .findByUniqueId(user.getUserid(),
	 *           Short.valueOf(Utils.defaultLinkUniqueID)).orElse(null); final Date
	 *           date = new Date(); if (Objects.isNull(lost) ||
	 *           lost.getLimitdate().before(date)) { final Calendar cal =
	 *           Calendar.getInstance(); cal.setTime(date); cal.add(5, 1); lost =
	 *           new Lostpassword(); lost.setIdlink(link); lost.setIdusers(user);
	 *           lost.setLimitdate(cal.getTime()); lost =
	 *           this.lostpassR.saveAndFlush(lost); } try { final String bodymail =
	 *           Utils.BeginEmailBody(email_maxhour_validatechange) +
	 *           link.getValues() + "?idser=" + user.getUserid() + "&aaaadsze=" +
	 *           lost.getIds(); Utils.sendMail("Lost Password", bodymail, new
	 *           String[] { user.getEmailid() }, this._mailSender); } catch
	 *           (IOException ex2) { ex2.printStackTrace(); } catch
	 *           (MessagingException | MailSendException ex3) {
	 *           ex3.printStackTrace(); return ResponseEntity.status(HttpStatus.OK)
	 *           .body(new Success(StaticValues.ErrorOnSendingEMail,
	 *           StaticValues.ErrorOnSendingEMail_Int)); } return
	 *           ResponseEntity.status(HttpStatus.OK) .body(new
	 *           Success(StaticValues.success, StaticValues.success_Int)); } return
	 *           ResponseEntity.status(HttpStatus.OK) .body(new
	 *           Success(StaticValues.linkerrorpleasecheck,
	 *           StaticValues.linkerrorpleasecheck_Int)); }
	 */
	@Override
	public ResponseEntity<?> initlostpassword2(String username) {
		// System.out.println(username);
		username = Utils.removeEscapeChars(username).replaceAll("^\"|\"$", "");
		// System.out.println(username);
		final User user = this.userR.findByUserName(username).orElse(null);
		User user2;
		if (Objects.isNull(user)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Success(StaticValues.UsernameIncorrect, StaticValues.UsernameIncorrect_Int));
		}
		final String EmailUser = user.getEmailid();
		if (Objects.isNull(EmailUser)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Success(StaticValues.Emailincorrect, StaticValues.Emailincorrect_Int));
		}
		final Linkloastpassword link = this.linkRepo.findByUniqueId(Short.valueOf(Utils.defaultLinkUniqueID))
				.orElse(null);
		if (!Objects.isNull(link)) {
			final Date date = new Date();
			/**
			 * Lostpassword lost = this.lostpassR .findByUniqueId(user.getUserid(),
			 * Short.valueOf(Utils.defaultLinkUniqueID)).orElse(null);
			 * 
			 * if (Objects.isNull(lost) || lost.getLimitdate().before(date)) { final
			 * Calendar cal = Calendar.getInstance(); cal.setTime(date); cal.add(5, 1); lost
			 * = new Lostpassword(); lost.setIdlink(link); lost.setIdusers(user);
			 * lost.setLimitdate(cal.getTime()); lost = this.lostpassR.saveAndFlush(lost); }
			 */
			try {
				// String generatedString = RandomStringUtils.random(50, true, true);
				String randoms = RandomStringUtils.random(70, true, true);
				randoms = randoms.replaceAll("&", "_h").replaceAll("=", "az_-");
				user2 = userR.findrandoms(randoms).orElse(null);
				if (!Objects.isNull(user2)) {
					while (!Objects.isNull(user2)) {
						randoms = RandomStringUtils.random(70, true, true);
						randoms = randoms.replaceAll("&", "_h").replaceAll("=", "az_-");
						user2 = userR.findrandoms(randoms).orElse(null);
					}
				}
				user.setRandoms(randoms);
				user.setLpw(false);
				// Date date=new Date();
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				cal.add(Calendar.HOUR, email_maxhour_validatechange);
				user.setRdates(cal.getTime());
				userR.saveAndFlush(user);
				// user.setRandoms(EmailUser);
				/**
				 * final String bodymail = Utils.BeginEmailBody(email_maxhour_validatechange) +
				 * link.getValues() + "?oupahhumm=" + randoms + "&aaaadsze=" + lost.getIds();
				 */
				String randoms2 = RandomStringUtils.random(150, true, true);
				randoms2 = randoms2.replaceAll("&", "_h").replaceAll("=", "az_-");
				String bodymail = Utils.BeginEmailBody(email_maxhour_validatechange) + link.getValues() + "?oupahhumm="
						+ user.getRandoms() + "&qls" + randoms2;

				Utils.sendMail("Lost Password", bodymail, new String[] { user.getEmailid() }, this._mailSender);
			} catch (IOException ex2) {
				ex2.printStackTrace();
			} catch (MessagingException | MailSendException ex3) {
				ex3.printStackTrace();
				return ResponseEntity.status(HttpStatus.OK)
						.body(new Success(StaticValues.ErrorOnSendingEMail, StaticValues.ErrorOnSendingEMail_Int));
			}
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Success(StaticValues.success, StaticValues.success_Int));
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(new Success(StaticValues.linkerrorpleasecheck, StaticValues.linkerrorpleasecheck_Int));
	}

	@Override
	public ResponseEntity<?> login(String username, String password, HttpServletRequest requests,
			HttpServletResponse responses) throws Exception {
		try {
			Calendar calendaru;
			User user = userR.findByUserName(username).orElse(null);
			Boolean checkpass = false;
			if (!Objects.isNull(user)) {

				Date dat = new Date();
				Date limitdate = user.getLpsw();
				if (Objects.isNull(limitdate)) {
					calendaru = Calendar.getInstance();
					calendaru.setTime(dat);
					calendaru.add(Calendar.DAY_OF_MONTH, maxdaysvalidation);
					limitdate = calendaru.getTime();
					user.setLpsw(limitdate);
					userR.saveAndFlush(user);
				}

				Boolean matches = passwordEncoder.matches(password, user.getPassword());
				if (!Objects.isNull(matches) && matches) {
					checkpass = true;
					if (dat.after(limitdate)) {
						return ResponseEntity.status(HttpStatus.OK).body(
								new Success(StaticValues.YourPasswordIsExpre, StaticValues.YourPasswordIsExpre_Int));
					}
				}

				int k = 0 + 8;
				Date datu = user.getLockTime();
				if (!Objects.isNull(datu) && user.getFailedAttempt().intValue() >= (MAX_FAILED_ATTEMPTS - 1)) {

					calendaru = Calendar.getInstance();
					calendaru.setTime(datu);
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(dat);
					if (!Utils.isDateSame(calendar, calendaru)) {
						user.setFailedAttempt(Short.valueOf("0"));
						user.setLockTime(null);
						user.setAccountNonLocked(true, false);
						user.setFlockd(null);
						user.setFlockn(Short.valueOf("0"));
						// userR.saveAndFlush(user);
						userR.saveAndFlush(user);
					} else {
						if (user.getFlockn().intValue() <= MAX_FAILED_ATTEMPTS_limitnumber) {
							datu = Utils.AddHoursToDate(datu, MAX_FAILED_ATTEMPTS_initialhour);
						} else {
							datu = Utils.AddHoursToDate(datu, MAX_FAILED_ATTEMPTS_secondhour);
						}
						if (dat.after(datu)) {
							user.setFailedAttempt(Short.valueOf("0"));
							user.setLockTime(null);
							user.setAccountNonLocked(true, false);
							if (user.getFlockn().intValue() <= MAX_FAILED_ATTEMPTS_limitnumber) {
								if (Objects.isNull(user.getFlockd())) {
									user.setFlockd(dat);
								}
								// user.setFlockn(Short.valueOf((user.getFlockn()+Short.valueOf("1"))+""));
							} else if (user.getFlockn().intValue() >= MAX_FAILED_ATTEMPTS_limitnumberend) {
								user.setFlockd(null);
								user.setFlockn(Short.valueOf("0"));
							}
							userR.saveAndFlush(user);
						} else {
							if (user.getFlockn().intValue() <= MAX_FAILED_ATTEMPTS_limitnumber) {
								return ResponseEntity.status(HttpStatus.OK)
										.body(new Success(
												StaticValues.WrongAttempFail.replace("XXXX",
														MAX_FAILED_ATTEMPTS_initialhour + "") + " at "
														+ Utils.DateFormat(datu, "yyyy-MM-dd HH:mm:ss"),
												StaticValues.WrongAttempFail_Int));
							} else if (user.getFlockn().intValue() >= MAX_FAILED_ATTEMPTS_limitnumberend) {
								return ResponseEntity.status(HttpStatus.OK)
										.body(new Success(
												StaticValues.WrongAttempFail22.replace("XXXX",
														MAX_FAILED_ATTEMPTS_secondhour + "") + " at "
														+ Utils.DateFormat(datu, "yyyy-MM-dd HH:mm:ss"),
												StaticValues.WrongAttempFail22_Int));
							}
						}
					}
				}
			}

			if (Objects.isNull(user)) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new Success(StaticValues.UsernameIncorrect, StaticValues.UsernameIncorrect_Int));
			} else if (!Objects.isNull(user.isMfa()) && user.isMfa()) {
				if (!checkpass) {
					Boolean matches = passwordEncoder.matches(password, user.getPassword());
					if (Objects.isNull(matches) || !matches) {
						return customAuthenticationSuccessHandler.onAuthenticationFailure(username);
					}
				}
				accesstokenR.deleteOauthAccessToken(username);
				if (user.isFirst()) {
					if (Objects.isNull(user.getSecret())) {
						user.setSecret(mfaTokenManager.generateSecretKey());

					}
					user.setIsconn(true);
					user.setNops(Encryption.encrypt(password));
					userR.saveAndFlush(user);
					return ResponseEntity.status(HttpStatus.OK)
							.body(new MfaTokenData(mfaTokenManager.getQRCode(user.getSecret(), username),
									user.getSecret(), StaticValues.NewPasswordToUpdate, user.isMfa(), user.isFirst()));

				}
				if (Objects.isNull(user.getSecret())) {
					user.setSecret(mfaTokenManager.generateSecretKey());
				}
				user.setIsconn(true);
				user.setNops(Encryption.encrypt(password));
				userR.saveAndFlush(user);
				return ResponseEntity.status(HttpStatus.OK).body(new SuccessMFA(StaticValues.successConnected,
						StaticValues.successConnected_Int, user.isMfa(), user.isFirst()));
			} else {
				Boolean matches = passwordEncoder.matches(password, user.getPassword());
				if (Objects.isNull(matches) || !matches) {
					return customAuthenticationSuccessHandler.onAuthenticationFailure(username);
				}
				accesstokenR.deleteOauthAccessToken(username);

				OkHttpClient client = new OkHttpClient();
				MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
				RequestBody body = RequestBody.create(mediaType,
						"username=" + username + "&password=" + password + "&grant_type=password&client_id=ymaneprod");
				Request request = new Request.Builder().url("http://localhost:" + localServerPort + "/oauth/token")
						.method("POST", body).addHeader("Authorization", "Basic eW1hbmVwcm9kOjEyMzQ1Ng==")
						.addHeader("Content-Type", "application/x-www-form-urlencoded").build();

				ResponseBody response = client.newCall(request).execute().body();
				String responsess = response.string();
				user.setIsconn(false);
				user.setIps(HttpRequestResponseUtils.getClientIpAddress());
				try {
					JSONObject json = new JSONObject(responsess);
					user.setLls(json.getString("access_token"));
				} catch (Exception ex) {
					user.setLls("");
				}
				userR.saveAndFlush(user);
				return ResponseEntity.status(HttpStatus.OK).body(responsess);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public ResponseEntity<?> lostpassword(final String username, final String newpassword, final Long linkid) {
		User user = this.userR.findById(Integer.valueOf(username)).orElse(null);
		if (Objects.isNull(user)) {
			user = this.userR.findByUserName(username).orElse(null);
			if (Objects.isNull(user)) {
			}
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Success(StaticValues.UsernameIncorrect, StaticValues.UsernameIncorrect_Int));
		}
		final Lostpassword lost = this.lostpassR.findById(linkid).orElse(null);
		if (Objects.isNull(lost)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Success(StaticValues.InvalidId, StaticValues.InvalidId_Int));
		}
		if (Objects.isNull(lost.getLimitdate()) || !lost.getLimitdate().after(new Date())) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Success(StaticValues.YourLinkExpire, StaticValues.YourLinkExpire_Int));
		}
		if (!Objects.isNull(newpassword) && !newpassword.trim().isEmpty()) {
			this.accesstokenR.deleteOauthAccessToken(user.getUsername());
			user.setLpw(true);
			user.setPassword(Utils.encodeBcriptPassword(newpassword), maxdaysvalidation, false);
			this.userR.saveAndFlush(user);

			return ResponseEntity.status(HttpStatus.OK)
					.body(new Success(StaticValues.success, StaticValues.success_Int));
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(new Success(StaticValues.passwordisempty, StaticValues.passwordisempty_Int));
	}
}
