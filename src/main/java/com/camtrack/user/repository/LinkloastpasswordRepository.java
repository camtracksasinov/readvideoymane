//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.camtrack.entities.Linkloastpassword;

public interface LinkloastpasswordRepository extends JpaRepository<Linkloastpassword, Short> {
	@Query("from  Linkloastpassword u  where u.uniquevalues =:uniquevalues")
	Optional<Linkloastpassword> findByUniqueId(@Param("uniquevalues") final Short username);
}
