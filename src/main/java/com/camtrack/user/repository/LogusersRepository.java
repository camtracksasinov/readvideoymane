//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.camtrack.entities.Logusers;

public interface LogusersRepository extends JpaRepository<Logusers, Long> {
	@Query("from  Logusers log  where log.username in :listusername and (log.dates>=:date1 and log.dates<=:date2)")
	List<Logusers> findAllByListUsername(final List<String> listusername, final Date date1, final Date date2);

	@Query("from  Logusers log  where log.dates>=:date1 and log.dates<=:date2")
	List<Logusers> findAllExists(final Date date1, final Date date2);
}
