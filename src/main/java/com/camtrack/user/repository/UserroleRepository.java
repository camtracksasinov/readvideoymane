//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.camtrack.entities.Userrole;

public interface UserroleRepository extends JpaRepository<Userrole, Integer> {
	@Query("from  Userrole u  where u.status =1")
	List<Userrole> findAllActiveRole();

	@Query("from  Userrole u  where u.status =1 and u.name = :rolename")
	Optional<Userrole> findByRoleName(final String rolename);
}
