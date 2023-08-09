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

import com.camtrack.entities.Exception;
import com.camtrack.entities.SummaryAllException;
import com.camtrack.entities.SummaryAllIntegerException;
import com.camtrack.entities.SummaryException;

public interface ExceptionRepository extends JpaRepository<Exception, Integer> {
	@Query(value = "select * from  Exception exep  where  (exep.glevel in :listlevelid or exep.level in :listlevelid) and exep.exceptiontype in :listidtypexception and (exep.status = 1 or exep.status is null) and ((exep.transporterid in :transporterid) or (exep.vehicleid  in :vehicleid) or (exep.driverid in :driverid)  or (exep.affiliateid in :affiliateid)) and exep.startdatetime >= :date1 and exep.startdatetime < :date2 and exep.exceptionid > :limitexceptionid  order by exep.exceptionid limit 1000", nativeQuery = true)
	List<Exception> alldetailsExceptionAdminLevel(final List<Integer> vehicleid, final List<Integer> driverid,
			final List<Integer> affiliateid, final List<Integer> transporterid, final Date date1, final Date date2,
			final List<Integer> listidtypexception, final List<Integer> listlevelid, final Integer limitexceptionid);

	@Query(value = "select * from  Exception exep  where  (exep.glevel in :listlevelid) and exep.exceptiontype in :listidtypexception and (exep.status = 1 or exep.status is null) and ((exep.transporterid in :transporterid) or (exep.vehicleid  in :vehicleid) or (exep.driverid in :driverid)  or (exep.affiliateid in :affiliateid)) and exep.startdatetime >= :date1 and exep.startdatetime < :date2 and exep.exceptionid > :limitexceptionid  order by exep.exceptionid limit 1000", nativeQuery = true)
	List<Exception> alldetailsExceptionGLevel(final List<Integer> vehicleid, final List<Integer> driverid,
			final List<Integer> affiliateid, final List<Integer> transporterid, final Date date1, final Date date2,
			final List<Integer> listidtypexception, final List<Integer> listlevelid, final Integer limitexceptionid);

	@Query(value = "select * from  Exception exep  where  (exep.level in :listlevelid) and exep.exceptiontype in :listidtypexception and (exep.status = 1 or exep.status is null) and ((exep.transporterid in :transporterid) or (exep.vehicleid  in :vehicleid) or (exep.driverid in :driverid)  or (exep.affiliateid in :affiliateid)) and exep.startdatetime >= :date1 and exep.startdatetime < :date2 and exep.exceptionid > :limitexceptionid  order by exep.exceptionid limit 1000", nativeQuery = true)
	List<Exception> alldetailsExceptionLevel(final List<Integer> vehicleid, final List<Integer> driverid,
			final List<Integer> affiliateid, final List<Integer> transporterid, final Date date1, final Date date2,
			final List<Integer> listidtypexception, final List<Integer> listlevelid, final Integer limitexceptionid);

	@Query("select new com.camtrack.entities.SummaryAllException(exep.affiliateid.affiliateid,exep.affiliateid.name,count(*)) from  Exception exep  where (exep.status.statusid = 1 or exep.status.statusid is null) and (exep.affiliateid.affiliateid in :listaffiliateid) and exep.startdatetime >= :date1 and exep.enddatetime<=:date2 group by exep.affiliateid.name,exep.affiliateid.affiliateid")
	List<SummaryAllException> countByAllAffiliateExeption(final List<Integer> listaffiliateid, final Date date1,
			final Date date2);

	@Query("select new com.camtrack.entities.SummaryAllIntegerException(exep.affiliateid.customerid.customerid,count(*)) from  Exception exep  where (exep.status.statusid = 1 or exep.status.statusid is null) and exep.startdatetime >= :date1 and exep.enddatetime<=:date2 group by exep.affiliateid.customerid.customerid")
	List<SummaryAllIntegerException> countByAllClientExeption(final Date date1, final Date date2);

	@Query("select new com.camtrack.entities.SummaryAllException(exep.driverid.driverid,exep.driverid.name,count(*)) from  Exception exep  where (exep.status.statusid = 1 or exep.status.statusid is null) and (exep.transporterid.transporterid in :listtransporterid) and exep.startdatetime >= :date1 and exep.enddatetime<=:date2 group by exep.driverid.name,exep.driverid.driverid")
	List<SummaryAllException> countByAllDriverExeption(final List<Integer> listtransporterid, final Date date1,
			final Date date2);

	@Query("select new com.camtrack.entities.SummaryAllException(exep.transporterid.transporterid,exep.transporterid.name,count(*)) from  Exception exep  where (exep.status.statusid = 1 or exep.status.statusid is null) and (exep.affiliateid.affiliateid in :listaffiliateid) and exep.startdatetime >= :date1 and exep.enddatetime<=:date2 group by exep.transporterid.name,exep.transporterid.transporterid")
	List<SummaryAllException> countByAllTransporterExeption(final List<Integer> listaffiliateid, final Date date1,
			final Date date2);

	@Query("select new com.camtrack.entities.SummaryAllException(exep.transporterid.transporterid,exep.transporterid.name,count(*)) from  Exception exep  where (exep.status.statusid = 1 or exep.status.statusid is null) and exep.startdatetime >= :date1 and exep.enddatetime<=:date2 and (exep.transporterid.name not like '%CAMTRACK%') group by exep.transporterid.name,exep.transporterid.transporterid")
	List<SummaryAllException> countByAllTransporterExeptionForRankingAndroid(final Date date1, final Date date2);

	@Query("select count(*) from  Exception exep  where (exep.status.statusid = 1 or exep.status.statusid is null) and (exep.vehicleid.vehicleid = :vehicleid) and exep.startdatetime >= :date1 and exep.enddatetime<=:date2")
	Long countExceptionVehicle(final Integer vehicleid, final Date date1, final Date date2);

	@Query("select new com.camtrack.entities.SummaryException(exep.exceptiontype.name,exep.transporterid.name,count(*)) from  Exception exep  where (exep.status.statusid = 1 or exep.status.statusid is null) and ((exep.transporterid.transporterid in :transporterid)  or (exep.vehicleid.vehicleid in :vehicleid)  or (exep.driverid.driverid in :driverid)  or (exep.affiliateid.affiliateid in :affiliateid)) and exep.startdatetime >= :date1 and exep.enddatetime<=:date2 group by exep.exceptiontype.name,exep.transporterid.name order by exep.exceptiontype.name")
	List<SummaryException> countlistExeption(final List<Integer> vehicleid, final List<Integer> driverid,
			final List<Integer> affiliateid, final List<Integer> transporterid, final Date date1, final Date date2);

	@Query("select new com.camtrack.entities.SummaryException(exep.exceptiontype.name,exep.transporterid.name,count(*)) from  Exception exep  where (exep.exceptiontype.parametertypeid in :listidtypexception) and (exep.status.statusid = 1 or exep.status.statusid is null) and ((exep.transporterid.transporterid in :transporterid)  or (exep.vehicleid.vehicleid in :vehicleid)  or (exep.driverid.driverid in :driverid)  or (exep.affiliateid.affiliateid in :affiliateid)) and exep.startdatetime >= :date1 and exep.enddatetime<=:date2 group by exep.exceptiontype.name,exep.transporterid.name order by exep.exceptiontype.name")
	List<SummaryException> countlistExeptionFilterTypeException(final List<Integer> vehicleid,
			final List<Integer> driverid, final List<Integer> affiliateid, final List<Integer> transporterid,
			final Date date1, final Date date2, final List<Integer> listidtypexception);

	@Transactional
	@Modifying
	@Query(value = "delete from exception ex where ex.exceptionid in :listexceptionid", nativeQuery = true)
	Integer deleteexception(final List<Integer> listexceptionid);

	@Query("from  Exception exep  where (exep.status.statusid = 1 or exep.status.statusid is null) and exep.exceptionid in :listids")
	List<Exception> findexceptiontoinvalidate(final List<Integer> listids);

	@Query(value = "select * from exception ex where ex.vehicleid = :vehicleid and ex.startdatetime = (SELECT max(exep.startdatetime) FROM exception exep where exep.vehicleid = :vehicleid)", nativeQuery = true)
	List<Exception> lastDriver(final Integer vehicleid);

	@Query("select distinct exep.driverid.driverid from  Exception exep  where (exep.status.statusid = 1 or exep.status.statusid is null) and (exep.vehicleid.vehicleid in :vehicleid) and exep.startdatetime >= :date1 and exep.enddatetime<=:date2")
	List<Integer> listactivedriverid(final List<Integer> vehicleid, final Date date1, final Date date2);

	@Query("select distinct exep.vehicleid.vehicleid from  Exception exep  where (exep.status.statusid = 1 or exep.status.statusid is null) and (exep.vehicleid.vehicleid in :vehicleid) and exep.startdatetime >= :date1 and exep.enddatetime<=:date2")
	List<Integer> listactiveVehicleId(final List<Integer> vehicleid, final Date date1, final Date date2);

	@Query("from  Exception exep  where (exep.status.statusid = 1 or exep.status.statusid is null) and ((exep.transporterid.transporterid in :transporterid)  or (exep.vehicleid.vehicleid in :vehicleid)  or (exep.driverid.driverid in :driverid)  or (exep.affiliateid.affiliateid in :affiliateid)) and exep.startdatetime >= :date1 and exep.enddatetime<=:date2")
	List<Exception> listExeption(final List<Integer> vehicleid, final List<Integer> driverid,
			final List<Integer> affiliateid, final List<Integer> transporterid, final Date date1, final Date date2);

	@Query("from  Exception exep  where (exep.exceptiontype.parametertypeid in :listidtypexception) and (exep.status.statusid = 1 or exep.status.statusid is null) and ((exep.transporterid.transporterid in :transporterid)  or (exep.vehicleid.vehicleid in :vehicleid)  or (exep.driverid.driverid in :driverid)  or (exep.affiliateid.affiliateid in :affiliateid)) and exep.startdatetime >= :date1 and exep.enddatetime<=:date2")
	List<Exception> listExeptionFilterTypeException(final List<Integer> vehicleid, final List<Integer> driverid,
			final List<Integer> affiliateid, final List<Integer> transporterid, final Date date1, final Date date2,
			final List<Integer> listidtypexception);

	@Query(value = "select * from exception ex where ex.exceptionid in :listexceptionis", nativeQuery = true)
	List<Exception> listretreiveexception(final List<Integer> listexceptionis);
}
