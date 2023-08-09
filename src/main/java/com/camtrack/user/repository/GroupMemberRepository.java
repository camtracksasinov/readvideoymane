//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.camtrack.entities.GroupMembers;

public interface GroupMemberRepository extends JpaRepository<GroupMembers, Long> {
	@Query("from  GroupMembers grpm  where grpm.username =:username")
	Optional<GroupMembers> findByUserName(@Param("username") final String username);
}
