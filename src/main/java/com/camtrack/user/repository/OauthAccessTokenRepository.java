//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.camtrack.entities.OauthAccessToken;

public interface OauthAccessTokenRepository extends JpaRepository<OauthAccessToken, String> {
	@Transactional
	@Modifying
	@Query(value = "delete from oauth_access_token where user_name = :username", nativeQuery = true)
	Integer deleteOauthAccessToken(final String username);
}
