//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.camtrack.entities.Emailconfig;

public interface EmailconfigRepository extends JpaRepository<Emailconfig, Long> {
	@Query(value = "select distinct userr.affiliateid from emailconfig userr", nativeQuery = true)
	List<Integer> findAllListAffiliateForSuperAdmin();

	@Query(value = "select distinct userr.affiliateid from emailconfig userr where userr.configid in :listallconfigid", nativeQuery = true)
	List<Integer> findAllListAffiliateForSuperAdminByListID(final List<Long> listallconfigid);

	@Query(value = "select distinct userr.clientid from emailconfig userr", nativeQuery = true)
	List<Integer> findAllListCustomerForSuperAdmin();

	@Query(value = "select distinct userr.clientid from emailconfig userr where userr.configid in :listallconfigid", nativeQuery = true)
	List<Integer> findAllListCustomerForSuperAdminByListID(final List<Long> listallconfigid);

	@Query(value = "select distinct listotheremailreceiver from emailconfig", nativeQuery = true)
	List<String> findAllListNativeOnEmailconfig();

	@Query(value = "select distinct listotheremailreceiver from emailconfig where configid in :listallconfigid", nativeQuery = true)
	List<String> findAllListNativeOnEmailconfigByListID(final List<Long> listallconfigid);

	@Query(value = "select distinct userr.transporterid from emailconfig userr", nativeQuery = true)
	List<Integer> findAllListTransporterForSuperAdmin();

	@Query(value = "select distinct userr.transporterid from emailconfig userr where userr.configid in :listallconfigid", nativeQuery = true)
	List<Integer> findAllListTransporterForSuperAdminByListID(final List<Long> listallconfigid);

	@Query(value = "select distinct userr.vehicleid from emailconfig userr", nativeQuery = true)
	List<Integer> findAllListVehicleForSuperAdmin();

	@Query(value = "select distinct userr.vehicleid from emailconfig userr where userr.configid in :listallconfigid", nativeQuery = true)
	List<Integer> findAllListVehicleForSuperAdminByListID(final List<Long> listallconfigid);

	@Query("from Emailconfig userr where userr.listotheremailreceiver = :email and userr.affiliateid.affiliateid = :affiliateid and userr.paramtypeid.parametertypeid = :parametertypeid")
	Optional<Emailconfig> findEmailconfigForAffiliate(final String email, final Integer affiliateid,
			final Integer parametertypeid);

	@Query("from Emailconfig userr where userr.listotheremailreceiver = :email and userr.clientid.customerid = :customerid and userr.paramtypeid.parametertypeid = :parametertypeid")
	Optional<Emailconfig> findEmailconfigForCustomer(final String email, final Integer customerid,
			final Integer parametertypeid);

	@Query("from Emailconfig userr where userr.listotheremailreceiver = :email and userr.transporterid.transporterid = :transporterid and userr.paramtypeid.parametertypeid = :parametertypeid")
	Optional<Emailconfig> findEmailconfigForTransporter(final String email, final Integer transporterid,
			final Integer parametertypeid);

	@Query("from Emailconfig userr where userr.listotheremailreceiver = :email and userr.vehicleid.vehicleid = :vehicleid and userr.paramtypeid.parametertypeid = :parametertypeid")
	Optional<Emailconfig> findEmailconfigForVehicle(final String email, final Integer vehicleid,
			final Integer parametertypeid);

	@Query("from Emailconfig userr where userr.listotheremailreceiver = :email")
	List<Emailconfig> findListEmailconfigByMailReceiver(final String email);

	@Query("from Emailconfig userr where userr.clientid is null and userr.affiliateid is not null and userr.transporterid is null and userr.vehicleid is null order by userr.userid.userid,userr.affiliateid.affiliateid")
	List<Emailconfig> findListEmailconfigForAffiliateLevel();

	@Query("from Emailconfig userr where userr.clientid is not null and userr.affiliateid is null and userr.transporterid is null and userr.vehicleid is null order by userr.userid.userid,userr.clientid.customerid")
	List<Emailconfig> findListEmailconfigForCustomerLevel();

	@Query("from Emailconfig userr where userr.clientid is null and userr.affiliateid is null and userr.transporterid is not null and userr.vehicleid is null order by userr.userid.userid,userr.transporterid.transporterid")
	List<Emailconfig> findListEmailconfigForTransporterLevel();

	@Query("from Emailconfig userr where userr.clientid is null and userr.affiliateid is null and userr.transporterid is null and userr.vehicleid is not null order by userr.userid.userid,userr.vehicleid.vehicleid")
	List<Emailconfig> findListEmailconfigForVehicleLevel();

	@Query(value = "select configid,userid,paramtypeid,recordstatus,alertstatus,alarmstatus,affiliateid,listotheremailreceiver from emailconfig userr where userr.clientid is null and userr.affiliateid is not null and userr.transporterid is null and userr.vehicleid is null order by userr.userid,userr.affiliateid", nativeQuery = true)
	List<Object[]> findListNativeEmailconfigForAffiliateLevel();

	@Query(value = "select configid,userid,paramtypeid,recordstatus,alertstatus,alarmstatus,clientid,listotheremailreceiver from emailconfig userr where userr.clientid is not null and userr.affiliateid is null and userr.transporterid is null and userr.vehicleid is null order by userr.userid,userr.clientid", nativeQuery = true)
	List<Object[]> findListNativeEmailconfigForCustomerLevel();

	@Query(value = "select configid,userid,paramtypeid,recordstatus,alertstatus,alarmstatus,transporterid,listotheremailreceiver from emailconfig userr where userr.clientid is null and userr.affiliateid is null and userr.transporterid is not null and userr.vehicleid is null order by userr.userid,userr.transporterid", nativeQuery = true)
	List<Object[]> findListNativeEmailconfigForTransporterLevel();

	@Query(value = "select configid,userid,paramtypeid,recordstatus,alertstatus,alarmstatus,vehicleid,listotheremailreceiver from emailconfig userr where userr.clientid is null and userr.affiliateid is null and userr.transporterid is null and userr.vehicleid is not null order by userr.userid,userr.vehicleid", nativeQuery = true)
	List<Object[]> findListNativeEmailconfigForVehicleLevel();
}
