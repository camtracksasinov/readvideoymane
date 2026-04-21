//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.camtrack.entities.GroupMembers;

public interface GroupMemberRepository extends JpaRepository<GroupMembers, Long> {
	@Query("from  GroupMembers grpm  where grpm.username =:username")
	Optional<GroupMembers> findByUserName(@Param("username") final String username);

	/**
	 * @Transactional
	 * @Modifying @Query("delete from groupgembers grpm where grpm.username
	 *            =:username") int deletegroup(@Param("username") final String
	 *            username);
	 */

	@Transactional
	@Modifying
	@Query(value = "delete from  group_members grpm  where grpm.username =:username", nativeQuery = true)
	Integer deletegroup(final String username);
}
