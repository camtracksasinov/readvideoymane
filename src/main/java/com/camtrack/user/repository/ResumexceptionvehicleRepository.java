//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.camtrack.entities.Resumexceptionvehicle;
import com.camtrack.entities.ResumexceptionvehiclePK;

public interface ResumexceptionvehicleRepository extends JpaRepository<Resumexceptionvehicle, ResumexceptionvehiclePK> {
	@Query(value = "select * from  resumexceptionvehicle resumeveh  where resumeveh.vehicleid in :listvehicleid and resumeveh.temporalites = 1 and (resumeveh.dates >= :dates1 and resumeveh.dates <= :dates2)", nativeQuery = true)
	List<Resumexceptionvehicle> dailyresumebyperiod(final List<Integer> listvehicleid, final Date dates1,
			final Date dates2);

	@Query(value = "select * from  resumexceptionvehicle resumeaff  where resumeaff.vehicleid = :vehicleid and resumeaff.dates = :date", nativeQuery = true)
	Resumexceptionvehicle findbydate(final Integer vehicleid, final Date date);
}
