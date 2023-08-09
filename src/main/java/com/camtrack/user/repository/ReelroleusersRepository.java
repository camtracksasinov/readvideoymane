//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.camtrack.bean.RoleBean;
import com.camtrack.entities.Reelroleusers;

public interface ReelroleusersRepository extends JpaRepository<Reelroleusers, Integer> {
	@Query("select new com.camtrack.bean.RoleBean(u.ids,u.rolenames,u.idtyperole.userroleid) from Reelroleusers u  where u.status =1 and u.idtyperole.userroleid >= :typeroleid")
	List<RoleBean> findAllHierarchical(final Integer typeroleid);

	@Query("from  Reelroleusers u  where u.status =1 and u.idtyperole.userroleid >= :typeroleid")
	List<Reelroleusers> findAllHierarchicalRole(final Integer typeroleid);

	@Query("select new com.camtrack.bean.RoleBean(u.ids,u.rolenames,u.idtyperole.userroleid) from  Reelroleusers u  where u.status =1 and u.idtyperole.userroleid >= :typeroleid")
	List<RoleBean> findAllHierarchicalRoleNativeQuery(final Integer typeroleid);

	@Query("from  Reelroleusers u  where u.status =1 and u.rolenames = :rolenames")
	Optional<Reelroleusers> findByRoleName(final String rolenames);
}
