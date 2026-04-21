//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import com.camtrack.entities.User;
import com.camtrack.user.repository.CustomerRepository;
import com.camtrack.user.repository.OauthAccessTokenRepository;
import com.camtrack.user.repository.UserightsRepository;
import com.camtrack.user.repository.UsersRepository;

@Configuration
public class CustomTokenConverter implements TokenEnhancer {
	@Autowired
	CustomLoginSuccessHandler CustomLoginSuccessHandler;
	@Autowired
	UsersRepository userRepository;
	@Autowired
	CustomerRepository customR;
	@Autowired
	UserightsRepository rightR;
	@Autowired
	OauthAccessTokenRepository accesstokenR;
	@Value("${img.baselink}")
	private String baselink;

	@Value("${userrrole.clientid}")
	private Integer clientroleid;

	@Value("${userrrole.affiliateid}")
	private Integer affiliateroleid;

	@Value("${userrrole.transporterid}")
	private Integer transporteroleid;

	@Override
	public OAuth2AccessToken enhance(final OAuth2AccessToken accessToken, final OAuth2Authentication authentication) {
		Map<String, Object> additionalInfo = new HashMap<>();
		User user = this.userRepository.findByUserName(authentication.getName()).orElse(null);
		Integer[] allroletypeid = { clientroleid, affiliateroleid, transporteroleid };
		additionalInfo.put("userinfos",
				null);
		// initialise connexion user

		// CustomLoginSuccessHandler.onAuthenticationSuccess(user.getUsername());
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
		//System.out.println("TEst Alanic DAta");
		user.setAccountNonLocked(true);
		user.setFailedAttempt(Short.valueOf("0"));
		user.setLockTime(null);
		accesstokenR.deleteOauthAccessToken(user.getUsername());
		user = userRepository.saveAndFlush(user);
		return accessToken;
	}
}
