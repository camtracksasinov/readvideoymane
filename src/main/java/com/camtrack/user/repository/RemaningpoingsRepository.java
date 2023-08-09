//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.camtrack.entities.Remaningpoings;

public interface RemaningpoingsRepository extends JpaRepository<Remaningpoings, Long> {
	@Query(value = "select * from  remaningpoings u  where u.driverid =:driverid and u.startyear = :startyear and u.endyear = :endyear and u.startmonth = :startmonth and u.endmonth = :endmonth", nativeQuery = true)
	Optional<Remaningpoings> findbyDriverMonth(final Integer driverid, final Integer startyear, final Integer endyear,
			final Short startmonth, final Short endmonth);

	@Query(value = "select * from  remaningpoings u  where u.driverid =:driverid and u.startyear = :startyear and u.endyear = :endyear", nativeQuery = true)
	List<Remaningpoings> findbyDriverYear(final Integer driverid, final Integer startyear, final Integer endyear);

	@Query(value = "select min(remainpoints) from  remaningpoings u  where u.driverid =:driverid and u.startyear = :startyear and u.endyear = :endyear", nativeQuery = true)
	Double findminremainpointsDriverOverYear(final Integer driverid, final Integer startyear, final Integer endyear);
}
