//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.camtrack.entities.Drivertrips;

public interface DrivertripsRepository extends JpaRepository<Drivertrips, Long> {
	@Query("from Drivertrips trips where trips.distancecovered > 0 and  trips.vehicleid.vehicleid  in :vehicleIds and ((trips.startdatetime <= :date1 and trips.enddatetime>=:date2) or (startdatetime <= :date1 and enddatetime<=:date2 and enddatetime>=:date1) or (trips.startdatetime >= :date1 and trips.enddatetime>=:date2 and trips.startdatetime <= :date2) or (trips.startdatetime >= :date1 and trips.enddatetime<=:date2))")
	List<Drivertrips> detailWorktime(final List<Integer> vehicleIds, final Date date1, final Date date2);

	@Query("from Drivertrips trips where  trips.vehicleid.transporterid.transporterid in :transporterid and ((trips.startdatetime <= :date1 and trips.enddatetime>=:date2) or (startdatetime <= :date1 and enddatetime<=:date2 and enddatetime>=:date1) or (trips.startdatetime >= :date1 and trips.enddatetime>=:date2 and trips.startdatetime <= :date2) or (trips.startdatetime >= :date1 and trips.enddatetime<=:date2)) order by trips.vehicleid.vehicleid,trips.startdatetime")
	List<Drivertrips> detailWorktimeTransporterList(final List<Integer> transporterid, final Date date1,
			final Date date2);

	@Query("select DISTINCT trips.driverid.driverid from Drivertrips trips where trips.distancecovered > :mindist and  trips.vehicleid.vehicleid in :vehicleIds and ((trips.startdatetime <= :date1 and trips.enddatetime>=:date2) or (startdatetime <= :date1 and enddatetime<=:date2 and enddatetime>=:date1) or (trips.startdatetime >= :date1 and trips.enddatetime>=:date2 and trips.startdatetime <= :date2) or (trips.startdatetime >= :date1 and trips.enddatetime<=:date2))")
	List<Integer> findAlldriverstrips(final List<Integer> vehicleIds, final Double mindist, final Date date1,
			final Date date2);

	@Query("select DISTINCT trips.vehicleid.vehicleid from Drivertrips trips where trips.distancecovered > :mindist and  trips.vehicleid.vehicleid in :vehicleIds and ((trips.startdatetime <= :date1 and trips.enddatetime>=:date2) or (startdatetime <= :date1 and enddatetime<=:date2 and enddatetime>=:date1) or (trips.startdatetime >= :date1 and trips.enddatetime>=:date2 and trips.startdatetime <= :date2) or (trips.startdatetime >= :date1 and trips.enddatetime<=:date2))")
	List<Integer> findAllVehicletrips(final List<Integer> vehicleIds, final Double mindist, final Date date1,
			final Date date2);

	@Query("from Drivertrips trips where trips.distancecovered > 0 and  trips.vehicleid.vehicleid  = :vehicleIds and ((trips.startdatetime <= :date1 and trips.enddatetime>=:date2) or (startdatetime <= :date1 and enddatetime<=:date2 and enddatetime>=:date1) or (trips.startdatetime >= :date1 and trips.enddatetime>=:date2 and trips.startdatetime <= :date2) or (trips.startdatetime >= :date1 and trips.enddatetime<=:date2))")
	List<Drivertrips> realworktimeTrips(final Integer vehicleIds, final Date date1, final Date date2);

	@Query("from Drivertrips trips where trips.distancecovered > :mindist and  trips.vehicleid.transporterid = :transporterid and ((trips.startdatetime <= :date1 and trips.enddatetime>=:date2) or (startdatetime <= :date1 and enddatetime<=:date2 and enddatetime>=:date1) or (trips.startdatetime >= :date1 and trips.enddatetime>=:date2 and trips.startdatetime <= :date2) or (trips.startdatetime >= :date1 and trips.enddatetime<=:date2)) order by trips.vehicleid.vehicleid")
	List<Drivertrips> worktimetransporter(final Integer transporterid, final Double mindist, final Date date1,
			final Date date2);
}
