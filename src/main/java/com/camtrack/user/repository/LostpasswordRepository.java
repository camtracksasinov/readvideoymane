//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.camtrack.entities.Lostpassword;

public interface LostpasswordRepository extends JpaRepository<Lostpassword, Long> {
	@Query(value = "select * from  lostpassword u  where u.idusers =:idusers and u.idlink = :idlink order by u.limitdate desc limit 1", nativeQuery = true)
	Optional<Lostpassword> findByUniqueId(final Integer idusers, final Short idlink);
}
