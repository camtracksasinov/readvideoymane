//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.camtrack.entities.Userlogsactivity;

public interface UserlogsactivityRepository extends JpaRepository<Userlogsactivity, Long> {
	@Query("from  Userlogsactivity log where log.userid.userid in :listuserids and (log.dates>=:date1 and log.dates< :date2)")
	List<Userlogsactivity> findAllByListUserId(final List<Integer> listuserids, final Date date1, final Date date2);

	@Query("from  Userlogsactivity log where log.dates>=:date1 and log.dates< :date2")
	List<Userlogsactivity> findAllExists(final Date date1, final Date date2);

	@Transactional
	@Modifying
	@Query(value = "delete from userlogsactivity log where log.userid=:userid", nativeQuery = true)
	Integer deletealllogsusers(Integer userid);
}
