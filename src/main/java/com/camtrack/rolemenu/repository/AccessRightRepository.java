//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.rolemenu.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.camtrack.entities.Accessrights;

public interface AccessRightRepository extends JpaRepository<Accessrights, Integer> {
	@Query("from  Accessrights acc where acc.menuid.menustatusid.statusid=1 and acc.menuid.menuid=:menuid and acc.reelroleusers.ids = :userroleid")
	Optional<Accessrights> findAccessRight(final Integer userroleid, final Integer menuid);

	@Query("from  Accessrights acc where acc.menuid.menustatusid.statusid=1")
	List<Accessrights> findAllActiveMenu();

	@Query("from  Accessrights acc where acc.menuid.menustatusid.statusid=1 and acc.reelroleusers.ids = :userroleid")
	List<Accessrights> findByRole(final Integer userroleid);
}
