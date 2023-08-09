//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.camtrack.entities.Resumexceptionclient;
import com.camtrack.entities.ResumexceptionclientPK;

public interface ResumexceptionclientRepository extends JpaRepository<Resumexceptionclient, ResumexceptionclientPK> {
	@Query(value = "select * from  resumexceptionclient resumeclient  where resumeclient.clientid in :listclientid and resumeclient.temporalites = 1 and (resumeclient.dates >= :dates1 and resumeclient.dates <= :dates2)", nativeQuery = true)
	List<Resumexceptionclient> dailyresumebyperiod(final List<Integer> listclientid, final Date dates1,
			final Date dates2);

	@Query(value = "select * from  resumexceptionclient resumeaff  where resumeaff.clientid = :clientid and resumeaff.dates = :date", nativeQuery = true)
	Resumexceptionclient findbydate(final Integer clientid, final Date date);
}
