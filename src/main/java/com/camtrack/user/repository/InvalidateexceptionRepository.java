//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.camtrack.entities.Invalidateexception;

public interface InvalidateexceptionRepository extends JpaRepository<Invalidateexception, Integer> {
	@Query(value = "select * from  invalidateexception exep  where  (exep.glevel in :listlevelid or exep.level in :listlevelid) and exep.exceptiontype in :listidtypexception and (exep.status = 1 or exep.status is null) and ((exep.transporterid in :transporterid) or (exep.vehicleid  in :vehicleid) or (exep.driverid in :driverid)  or (exep.affiliateid in :affiliateid)) and exep.startdatetime >= :date1 and exep.startdatetime < :date2 and exep.exceptionid > :limitexceptionid  order by exep.exceptionid limit 1000", nativeQuery = true)
	List<Invalidateexception> alldetailsExceptionAdminLevel(final List<Integer> vehicleid, final List<Integer> driverid,
			final List<Integer> affiliateid, final List<Integer> transporterid, final Date date1, final Date date2,
			final List<Integer> listidtypexception, final List<Integer> listlevelid, final Integer limitexceptionid);

	@Query(value = "select * from  invalidateexception exep  where  (exep.glevel in :listlevelid) and exep.exceptiontype in :listidtypexception and (exep.status = 1 or exep.status is null) and ((exep.transporterid in :transporterid) or (exep.vehicleid  in :vehicleid) or (exep.driverid in :driverid)  or (exep.affiliateid in :affiliateid)) and exep.startdatetime >= :date1 and exep.startdatetime < :date2 and exep.exceptionid > :limitexceptionid  order by exep.exceptionid limit 1000", nativeQuery = true)
	List<Invalidateexception> alldetailsExceptionGLevel(final List<Integer> vehicleid, final List<Integer> driverid,
			final List<Integer> affiliateid, final List<Integer> transporterid, final Date date1, final Date date2,
			final List<Integer> listidtypexception, final List<Integer> listlevelid, final Integer limitexceptionid);

	@Query(value = "select * from  invalidateexception exep  where  (exep.level in :listlevelid) and exep.exceptiontype in :listidtypexception and (exep.status = 1 or exep.status is null) and ((exep.transporterid in :transporterid) or (exep.vehicleid  in :vehicleid) or (exep.driverid in :driverid)  or (exep.affiliateid in :affiliateid)) and exep.startdatetime >= :date1 and exep.startdatetime < :date2 and exep.exceptionid > :limitexceptionid  order by exep.exceptionid limit 1000", nativeQuery = true)
	List<Invalidateexception> alldetailsExceptionLevel(final List<Integer> vehicleid, final List<Integer> driverid,
			final List<Integer> affiliateid, final List<Integer> transporterid, final Date date1, final Date date2,
			final List<Integer> listidtypexception, final List<Integer> listlevelid, final Integer limitexceptionid);

	@Query(value = "select count(*),exep.comments,exep.exceptiontype.parametertypeid,exep.affiliateid.affiliateid from  invalidateexception exep  where  (exep.level in :listlevelid) and exep.exceptiontype in :listidtypexception and (exep.status = 1 or exep.status is null) and (exep.affiliateid.affiliateid in :affiliateid) and exep.startdatetime >= :date1 and exep.startdatetime < :date2  group by exep.comments,exep.exceptiontype.parametertypeid,exep.affiliateid.affiliateid", nativeQuery = true)
	Object[][] countbyAffiliatecomments(final List<Integer> affiliateid, final Date date1, final Date date2,
			final List<Integer> listidtypexception, final List<Integer> listlevelid);

	@Query("select count(*),exep.comments,exep.exceptiontype.parametertypeid,exep.affiliateid.customerid.customerid from  Invalidateexception exep  where  (exep.level.exceptionlevelid in :listlevelid) and exep.exceptiontype.parametertypeid in :listidtypexception and (exep.status.statusid = 1 or exep.status is null) and (exep.affiliateid.customerid.customerid in :listcustomerid) and exep.startdatetime >= :date1 and exep.startdatetime < :date2 group  by exep.comments,exep.exceptiontype.parametertypeid,exep.affiliateid.customerid.customerid")
	Object[][] countbyAffiliateOfClientcomments(final List<Integer> listcustomerid, final Date date1, final Date date2,
			final List<Integer> listidtypexception, final List<Integer> listlevelid);

	@Query(value = "select count(comments),exep.comments from  invalidateexception exep  where  (exep.level in :listlevelid) and exep.exceptiontype in :listidtypexception and (exep.status = 1 or exep.status is null) and ((exep.transporterid in :transporterid) or (exep.vehicleid  in :vehicleid) or (exep.driverid in :driverid)  or (exep.affiliateid in :affiliateid)) and exep.startdatetime >= :date1 and exep.startdatetime < :date2  group by exep.comments", nativeQuery = true)
	Object[][] countbycomments(final List<Integer> vehicleid, final List<Integer> driverid,
			final List<Integer> affiliateid, final List<Integer> transporterid, final Date date1, final Date date2,
			final List<Integer> listidtypexception, final List<Integer> listlevelid);

	@Query(value = "select count(*),exep.comments,exep.exceptiontype.parametertypeid,exep.driverid from  invalidateexception exep  where  (exep.level in :listlevelid) and exep.exceptiontype in :listidtypexception and (exep.status = 1 or exep.status is null) and (exep.driverid in :driverid) and exep.startdatetime >= :date1 and exep.startdatetime < :date2  group by exep.comments,exep.exceptiontype.parametertypeid,exep.driverid", nativeQuery = true)
	Object[][] countbydrivercomments(final List<Integer> driverid, final Date date1, final Date date2,
			final List<Integer> listidtypexception, final List<Integer> listlevelid);

	@Query(value = "select count(*),exep.comments,exep.exceptiontype.parametertypeid,exep.transporterid from  invalidateexception exep  where  (exep.level in :listlevelid) and exep.exceptiontype in :listidtypexception and (exep.status = 1 or exep.status is null) and (exep.transporterid in :transporterid) and exep.startdatetime >= :date1 and exep.startdatetime < :date2  group by exep.comments,exep.exceptiontype.parametertypeid,exep.transporterid", nativeQuery = true)
	Object[][] countbytransportercomments(final List<Integer> transporterid, final Date date1, final Date date2,
			final List<Integer> listidtypexception, final List<Integer> listlevelid);

	@Query(value = "select count(*),exep.comments,exep.exceptiontype.parametertypeid,exep.vehicleid from  invalidateexception exep  where  (exep.level in :listlevelid) and exep.exceptiontype in :listidtypexception and (exep.status = 1 or exep.status is null) and (exep.vehicleid in :vehicleid) and exep.startdatetime >= :date1 and exep.startdatetime < :date2  group by exep.comments,exep.exceptiontype.parametertypeid,exep.vehicleid", nativeQuery = true)
	Object[][] countbyvehiclecomments(final List<Integer> vehicleid, final Date date1, final Date date2,
			final List<Integer> listidtypexception, final List<Integer> listlevelid);

	@Transactional
	@Modifying
	@Query(value = "delete from invalidateexception ex where ex.exceptionid in :listexceptionid", nativeQuery = true)
	Integer deleteexception(final List<Integer> listexceptionid);

	@Query(value = "select * from invalidateexception ex where ex.exceptionid in :listexceptionis", nativeQuery = true)
	List<Invalidateexception> listretreiveexception(final List<Integer> listexceptionis);
}
