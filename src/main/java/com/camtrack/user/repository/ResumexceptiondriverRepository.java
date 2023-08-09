//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.camtrack.entities.Resumexceptiondriver;
import com.camtrack.entities.ResumexceptiondriverPK;

public interface ResumexceptiondriverRepository extends JpaRepository<Resumexceptiondriver, ResumexceptiondriverPK> {
	@Query(value = "select * from  resumexceptiondriver resumedriver  where resumedriver.driverid in :listdriverid and resumedriver.temporalites = 1 and (resumedriver.dates >= :dates1 and resumedriver.dates <= :dates2)", nativeQuery = true)
	List<Resumexceptiondriver> dailyresumebyperiod(final List<Integer> listdriverid, final Date dates1,
			final Date dates2);

	@Query(value = "select * from  resumexceptiondriver resumeaff  where resumeaff.driverid = :driverid and resumeaff.dates = :date", nativeQuery = true)
	Resumexceptiondriver findbydate(final Integer driverid, final Date date);
}
