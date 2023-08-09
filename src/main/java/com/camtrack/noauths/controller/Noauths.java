//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.noauths.controller;

import java.time.Instant;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import springfox.documentation.annotations.ApiIgnore;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.camtrack.captcha.RequiresCaptcha;
import com.camtrack.captcha.ValidateCaptcha;
import com.camtrack.config.Encryption;
import com.camtrack.config.HttpRequestResponseUtils;
import com.camtrack.config.StaticValues;
import com.camtrack.config.Utils;
import com.camtrack.entities.HummMXGa;
import com.camtrack.entities.HummMXGaMouna;
import com.camtrack.entities.Is1Object;
import com.camtrack.entities.Pdswies;
import com.camtrack.entities.PdswiesNew;
import com.camtrack.entities.ServiceStatus;
import com.camtrack.entities.Success;
import com.camtrack.entities.User;
import com.camtrack.mfauthentification.dao.MFATokenManager;
import com.camtrack.noauths.service.NoauthInterface;
import com.camtrack.user.repository.UsersRepository;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RefreshScope
@CrossOrigin
@RequestMapping({ "/noauths" })
public class Noauths {
	@Value("${linkdnsforexternallogin.dns}")
	private String linkdnsforexternallogin;
	@Value("${server.port}")
	private int localServerPort;
	@Autowired
	MFATokenManager mfaTokenManager;
	@Autowired
	NoauthInterface noaths;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	ValidateCaptcha service;
	@Autowired
	UsersRepository userR;

	@Autowired
	CacheManager cacheManager;

	// service /login -- > /alhumm2
	// username -- > is1
	// password -- > is2
	/**
	 * @RequestMapping(value = { "/alhumm2" }, method = { RequestMethod.POST })
	 *                       public ResponseEntity<?>
	 *                       alhumm2(@org.springframework.web.bind.annotation.RequestBody
	 *                       Is1Object is1is2,
	 * @Parameter(hidden = true)HttpServletRequest request, @Parameter(hidden =
	 *                   true)HttpServletResponse response) throws Exception { try {
	 *                   return noaths.login(Utils.StringEscape(is1is2.getIs1()),
	 *                   Utils.StringEscape(is1is2.getIs2()), request, response); }
	 *                   catch (Exception e) { e.printStackTrace(); throw e; } }
	 */

	// service /login -- > /alhumm2
	// username -- > is1
	// password -- > is2
	@RequestMapping(value = { "/alhumm2" }, method = { RequestMethod.POST })
	@RequiresCaptcha
	public ResponseEntity<?> alhumm2(@org.springframework.web.bind.annotation.RequestBody Is1Object is1is2,
			@Parameter(hidden = true) HttpServletRequest request,
			@Parameter(hidden = true) HttpServletResponse response) throws Exception {
		try {
			/**
			 * String isValidCaptcha = service.verifyRecaptcha(request.getRemoteAddr(),
			 * request.getHeader("g-recaptcha-response"));
			 *
			 * if ( StringUtils.isNotEmpty(isValidCaptcha)) { return (ResponseEntity<?>)
			 * ResponseEntity.status(HttpStatus.OK) .body((Object) new
			 * Success(StaticValues.InvalidCaptcha,StaticValues.InvalidCaptcha_Int)); }
			 */
			return noaths.login(Utils.StringEscape(is1is2.getIs1()), Utils.StringEscape(is1is2.getIs2()), request,
					response);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping(value = { "/exppw" }, method = { RequestMethod.POST })
	@RequiresCaptcha
	public ResponseEntity<?> expirepw(@org.springframework.web.bind.annotation.RequestBody @Valid Pdswies lsqt,
			@Parameter(hidden = true) HttpServletRequest request) throws Exception {
		try {
			/**
			 * String isValidCaptcha = service.verifyRecaptcha(request.getRemoteAddr(),
			 * request.getHeader("g-recaptcha-response"));
			 *
			 * if ( StringUtils.isNotEmpty(isValidCaptcha)) { return (ResponseEntity<?>)
			 * ResponseEntity.status(HttpStatus.OK) .body((Object) new
			 * Success(StaticValues.InvalidCaptcha,StaticValues.InvalidCaptcha_Int)); }
			 */
			if (lsqt.getOldis2().equalsIgnoreCase(lsqt.getNewoldis())) {
				return ResponseEntity.status(HttpStatus.OK)
						.body((Object) new Success(StaticValues.PasswordIdentique, StaticValues.PasswordIdentique_Int));
			}
			return this.noaths.chpasswd3expire(Utils.StringEscape(lsqt.getIs1()), Utils.StringEscape(lsqt.getOldis2()),
					Utils.StringEscape(lsqt.getNewoldis()));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	// lostpassword -- > /lostids0
	// Param Username -- > isdperds
	/**
	 * @RequestMapping(value = { "/lostids0" }, method = { RequestMethod.POST })
	 *                       public ResponseEntity<?>
	 *                       lostids0(@org.springframework.web.bind.annotation.RequestBody
	 *                       HummMXGaMouna is1) throws Exception { try { return
	 *                       (ResponseEntity<?>)
	 *                       this.noaths.initlostpassword(is1.getIs1()); } catch
	 *                       (Exception e) { e.printStackTrace(); throw e; } }
	 */
	// New Lost password
	// lostpassword -- > /lostids0
	// Param Username -- > isdperds
	@RequestMapping(value = { "/lostids0" }, method = { RequestMethod.POST })
	public ResponseEntity<?> lostids0(@org.springframework.web.bind.annotation.RequestBody @Valid HummMXGaMouna is1)
			throws Exception {
		try {
			return this.noaths.initlostpassword2(is1.getIs1());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping(value = { "/newhumm" }, method = { RequestMethod.POST })
	@RequiresCaptcha
	public ResponseEntity<?> newhumm(@org.springframework.web.bind.annotation.RequestBody @Valid PdswiesNew igt,
			@Parameter(hidden = true) HttpServletRequest request) throws Exception {
		try {
			return noaths.chpasswd0(Utils.StringEscape(igt.getIs1()), Utils.StringEscape(igt.getNewoldis()));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	// Utils.StringEscape(datedebut), Utils.StringEscape(datefin)
	// /chpasswd --- > oknewpb
	// Username: is1
	// oldpwd -- > oldis2
	// newpwd -- > newoldis
	/**
	 * @RequestMapping(value = { "/oknewpb" }, method = { RequestMethod.POST })
	 *                       public ResponseEntity<?>
	 *                       oknewpb(@org.springframework.web.bind.annotation.RequestBody
	 *                       Pdswies lsqt) throws Exception { try { return
	 *                       (ResponseEntity<?>)
	 *                       this.noaths.chpasswd(Utils.StringEscape(lsqt.getIs1()),
	 *                       Utils.StringEscape(lsqt.getOldis2()),
	 *                       Utils.StringEscape(lsqt.getNewoldis())); } catch
	 *                       (Exception e) { e.printStackTrace(); throw e; } }
	 */
	// Update Lost Password
	// param variable
	// Utils.StringEscape(datedebut), Utils.StringEscape(datefin)
	// /chpasswd --- > oknewpb
	// Username: is1
	// oldpwd -- > oldis2
	// newpwd -- > newoldis
	@RequestMapping(value = { "/oknewpb" }, method = { RequestMethod.POST })
	@RequiresCaptcha
	public ResponseEntity<?> oknewpb(@org.springframework.web.bind.annotation.RequestBody @Valid Pdswies lsqt,
			@Parameter(hidden = true) HttpServletRequest request) throws Exception {
		try {
			/**
			 * String isValidCaptcha = service.verifyRecaptcha(request.getRemoteAddr(),
			 * request.getHeader("g-recaptcha-response"));
			 *
			 * if ( StringUtils.isNotEmpty(isValidCaptcha)) { return (ResponseEntity<?>)
			 * ResponseEntity.status(HttpStatus.OK) .body((Object) new
			 * Success(StaticValues.InvalidCaptcha,StaticValues.InvalidCaptcha_Int)); }
			 */
			if (lsqt.getOldis2().equalsIgnoreCase(lsqt.getNewoldis())) {
				return ResponseEntity.status(HttpStatus.OK)
						.body((Object) new Success(StaticValues.PasswordIdentique, StaticValues.PasswordIdentique_Int));
			}
			return this.noaths.chpasswd(Utils.StringEscape(lsqt.getIs1()), Utils.StringEscape(lsqt.getOldis2()),
					Utils.StringEscape(lsqt.getNewoldis()));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	// service /login -- > /alhumm
	// username -- > is1
	// password -- > is2
	/**
	 * @RequestMapping(value = { "/alhumm" }, method = { RequestMethod.POST })
	 *                       public ResponseEntity<?> alhumm(String is1, String
	 *                       is2, @Parameter(hidden = true)HttpServletRequest
	 *                       request,
	 * @Parameter(hidden = true)HttpServletResponse response) throws Exception { try
	 *                   { return noaths.login(Utils.StringEscape(is1),
	 *                   Utils.StringEscape(is2), request, response); } catch
	 *                   (Exception e) { e.printStackTrace(); throw e; } }
	 */

	// Service /secondhumms -- > /checkmfa
	// username -- > ihjuhys1,
	// mfacode -- > ishdfts
	@RequestMapping(value = { "/secondhumms" }, method = { RequestMethod.POST })
	public ResponseEntity<?> secondhumms(@org.springframework.web.bind.annotation.RequestBody @Valid HummMXGa fga)
			throws Exception {
		try {
			String username = Utils.StringEscape(fga.getIs1());
			String mfacode = Utils.StringEscape(fga.getIshdfts());
			User user = userR.findByUserName(username).orElse(null);
			if (Objects.isNull(user)) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new Success(StaticValues.UsernameIncorrect, StaticValues.UsernameIncorrect_Int));
			}

			if (!Objects.isNull(user.isIsconn()) && user.isIsconn()) {
				if (!mfaTokenManager.verifyTotp(mfacode, user.getSecret())) { // chekcing if the user token matching
					return ResponseEntity.status(HttpStatus.OK)
							.body(new Success(StaticValues.InCorrectCodeMFA, StaticValues.InCorrectCodeMFA_Int));
				}

				OkHttpClient client = new OkHttpClient();
				MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
				String decrypt = Encryption.decrypt(user.getNops());
				RequestBody body = RequestBody.create(mediaType,
						"username=" + username + "&password=" + decrypt + "&grant_type=password&client_id=ymaneprod");
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
				user.setFirst(false, true);
				user.setIsconn(false);
				user.setIps(HttpRequestResponseUtils.getClientIpAddress());
				userR.saveAndFlush(user);
				// final String ip = HttpRequestResponseUtils.getClientIpAddress();
				return ResponseEntity.status(HttpStatus.OK).body(responsess);

			} else {
				return ResponseEntity.status(HttpStatus.OK).body(new Success(StaticValues.ConnectedBeforecheckingMFA,
						StaticValues.ConnectedBeforecheckingMFA_Int));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

	@GetMapping({ "/status" })
	@Cacheable("status")
	public ResponseEntity<?> status() {
		return ResponseEntity.status(HttpStatus.OK)
				.body((Object) new ServiceStatus("YmaneProduction", Instant.now().toString()));
	}

}
