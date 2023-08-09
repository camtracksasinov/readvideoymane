//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.camtrack.entities.Transporter;
import com.camtrack.entities.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
	@Query(value = "select driv.vehicleid,driv.vehicledesc from  vehicle driv  where  driv.status = 1 and driv.transporterid = :transporterid", nativeQuery = true)
	List<Object[]> alldriverinfosWithId(Integer transporterid);

	@Query("select distinct transveh.vehicleid from  Vehicle transveh  where transveh.status = 1 and transveh.transporterid.transporterid in :listtrans")
	List<Integer> findAllActiveIdVehicleAff(final List<Integer> listtrans);

	@Query("select distinct transveh.vehicleid from  Vehicle transveh  where transveh.status = 1 and transveh.transporterid.transporterid in :listtrans and transveh.vehicleid is not null")
	List<Integer> findAllActiveIdVehicleTransporter(final List<Integer> listtrans);

	@Query("select distinct transveh.vehicleid from  Vehicle transveh  where transveh.status = 1 and transveh.transporterid.transporterid = :transid")
	List<Integer> findAllActiveIdVehicleTransporterId(final Integer transid);

	@Query("from  Vehicle transveh  where transveh.status = 1")
	List<Vehicle> findAllActiveVehicle();

	@Query("from  Vehicle transveh  where transveh.status = 1 and transveh.transporterid.transporterid = :transid")
	List<Vehicle> findAllActiveVehicleOfTransporter(final Integer transid);

	@Query("from  Vehicle transveh  where transveh.status = 1 and transveh.transporterid.transporterid in :listtrans")
	List<Vehicle> findAllActiveVehicleTransporter(final List<Integer> listtrans);

	@Query("select distinct transveh.transporterid from Vehicle transveh  where transveh.status = 1 and transveh.vehicleid in :listvehicleid")
	List<Transporter> findAllIdActiveTransporter(final List<Integer> listvehicleid);

	@Query(value = "select transveh.vehicleid from  Vehicle transveh  where transveh.status = 1", nativeQuery = true)
	List<Integer> findAllIdActiveVehicle();

	@Query("from  Vehicle transveh  where transveh.status = 1")
	List<Integer> findAllTransporterId();

	@Query(value = "select * from  vehicle transveh  where transveh.status = 1 and REPLACE(UPPER(transveh.vehicledesc), ' ', '') like %:vehiclename%", nativeQuery = true)
	List<Vehicle> findListVehicleByName(final String vehiclename);

	@Query(value = "select * from  vehicle transveh  where transveh.status = 1 and transveh.vehicleid in :listvehId", nativeQuery = true)
	List<Vehicle> findListVehicleId(final List<Integer> listvehId);

	@Query("select count(distinct transveh.vehicleid) from  Vehicle transveh  where transveh.status = 1 and transveh.transporterid.transporterid = :transid")
	Long listactifveh(final Integer transid);

	@Query(value = "select veh.vehicledesc from vehicle veh  where veh.vehicleid = :vehicleid", nativeQuery = true)
	String nameobject(final Integer vehicleid);
}
