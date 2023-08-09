//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.camtrack.entities.Visitor;

public interface VisitorRepository extends JpaRepository<Visitor, Long> {
	@Query(value = "select *  from  visitor log  where log.username in :listusername and (log.dates>=:date1 and log.dates<=:date2)", nativeQuery = true)
	List<Visitor> findAllByListUsername(final List<String> listusername, final Date date1, final Date date2);

	@Query(value = "select * from  visitor visit  where visit.loggedtime >= :date1 and visit.loggedtime <= :date2", nativeQuery = true)
	List<Visitor> findAllExists(final Date date1, final Date date2);
}
