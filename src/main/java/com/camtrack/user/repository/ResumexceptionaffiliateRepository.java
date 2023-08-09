//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.camtrack.entities.Resumexceptionaffiliate;
import com.camtrack.entities.ResumexceptionaffiliatePK;

public interface ResumexceptionaffiliateRepository
		extends JpaRepository<Resumexceptionaffiliate, ResumexceptionaffiliatePK> {
	@Query(value = "select * from  resumexceptionaffiliate resumeaff  where resumeaff.affiliateid in :listaffid and resumeaff.temporalites = 1 and (resumeaff.dates >= :dates1 and resumeaff.dates <= :dates2)", nativeQuery = true)
	List<Resumexceptionaffiliate> dailyresumebyperiod(final List<Integer> listaffid, final Date dates1,
			final Date dates2);

	@Query(value = "select * from  resumexceptionaffiliate resumeaff  where resumeaff.affiliateid = :affiliateid and resumeaff.dates = :date", nativeQuery = true)
	Resumexceptionaffiliate findbydate(final Integer affiliateid, final Date date);
}
