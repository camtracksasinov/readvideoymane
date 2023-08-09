//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.camtrack.entities.Driveractivitysummary;

public interface DriveractivitysummaryRepository extends JpaRepository<Driveractivitysummary, Long> {
	@Query(value = "select count(distinct drivsum.driverid) from driveractivitysummary drivsum where drivsum.activitydate >= :date1 and drivsum.activitydate<= :date2 and drivsum.vehicleid in :listvehicleid and drivsum.totaldistance >= :totaldistancezero", nativeQuery = true)
	Long listalldriveractif(final List<Integer> listvehicleid, final BigDecimal totaldistancezero, final Date date1,
			final Date date2);

	@Query(value = "select sum(drivsum.totaldistance) as sum1,sum(drivsum.validduration) as sum2,sum(drivsum.totaldripduration) as sum3 from driveractivitysummary drivsum where drivsum.activitydate >= :date1 and drivsum.activitydate<= :date2 and drivsum.driverid in :listdriverid", nativeQuery = true)
	List<Object[]> sumallperperiodfordriver(final List<Integer> listdriverid, final Date date1, final Date date2);

	@Query(value = "select sum(drivsum.totaldistance) from driveractivitysummary drivsum where drivsum.activitydate >= :date1 and drivsum.activitydate<= :date2 and drivsum.driverid = :driverid", nativeQuery = true)
	BigDecimal sumdistanceperperiodfordriver(final Date date1, final Date date2, final Integer driverid);
}
