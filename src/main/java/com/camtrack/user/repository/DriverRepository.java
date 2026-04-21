//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.camtrack.entities.Driver;
import com.camtrack.entities.Transporter;

public interface DriverRepository extends JpaRepository<Driver, Integer> {
	@Query(value = "select driv.driverid from  driver driv  where  driv.status = 1", nativeQuery = true)
	List<Integer> AllActivesDriver();

	@Query("select driv.transporterid from  Driver driv  where  driv.status = 1 and driv.driverid in :driver")
	List<Transporter> AllActivesTransporterOfTriver(final List<Integer> driver);

	@Query(value = "select driv.driverid from  driver driv  where  driv.status = 1 and driv.transporterid in :transporterid and driv.driverid is not null", nativeQuery = true)
	List<Integer> AlldriverIdOfTransporter(final List<Integer> transporterid);

	@Query(value = "select driv.driverid,driv.name,driv.transporterid,driv.driverkeycode,driv.vehicleid,driv.status from  driver driv  where  driv.status = 1 and driv.transporterid in :transporterid", nativeQuery = true)
	List<Object[]> alldriverinfos(final List<Integer> transporterid);

	@Query(value = "select driv.driverid,driv.name from  driver driv  where  driv.status = 1 and driv.transporterid = :transporterid", nativeQuery = true)
	List<Object[]> alldriverinfosWithId(Integer transporterid);

	@Query("from  Driver driv  where  driv.status = 1 and driv.transporterid.transporterid in :transporterid")
	List<Driver> AlldriverTransporter(final List<Integer> transporterid);

	@Query(value = "select driv.driverid from  Driver driv  where  driv.status = 1 and driv.transporterid in (select distinct vl.transporterid from vehicle vl where vl.vehicleid in :listvehicleid)", nativeQuery = true)
	List<Integer> alldriverUser(final List<Integer> listvehicleid);

	@Query("from  Driver driv  where  driv.status = 1order by transporterid.transporterid")
	List<Driver> AllListdriverTransporter();

	@Query("select driv.driverid from  Driver driv  where  driv.status = 1 and driv.transporterid.affiliateid.affiliateid in :affiliateid")
	List<Integer> driverAffiliate(final List<Integer> affiliateid);

	@Query(value = "select driv.driverid from  driver driv  where  driv.status = 1 and driv.transporterid in :transporterid", nativeQuery = true)
	List<Integer> driverTransporter(final List<Integer> transporterid);

	@Query(value = "select drive.name from driver drive  where drive.driverid = :driverid", nativeQuery = true)
	String nameobject(final Integer driverid);
}
