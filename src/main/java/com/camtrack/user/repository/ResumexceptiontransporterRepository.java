//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.camtrack.entities.Resumexceptiontransporter;
import com.camtrack.entities.ResumexceptiontransporterPK;

public interface ResumexceptiontransporterRepository
		extends JpaRepository<Resumexceptiontransporter, ResumexceptiontransporterPK> {
	@Query(value = "select * from  resumexceptiontransporter resumetrans  where resumetrans.transporterid in :listtransporterid and resumetrans.temporalites = 1 and (resumetrans.dates >= :dates1 and resumetrans.dates <= :dates2)", nativeQuery = true)
	List<Resumexceptiontransporter> dailyresumebyperiod(final List<Integer> listtransporterid, final Date dates1,
			final Date dates2);

	@Query(value = "select * from  resumexceptiontransporter resumeaff  where resumeaff.transporterid = :transporterid and resumeaff.dates = :date", nativeQuery = true)
	Resumexceptiontransporter findbydate(final Integer transporterid, final Date date);
}
