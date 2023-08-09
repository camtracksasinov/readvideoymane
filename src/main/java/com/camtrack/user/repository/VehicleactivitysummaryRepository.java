//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.camtrack.entities.Vehicleactivitysummary;

public interface VehicleactivitysummaryRepository extends JpaRepository<Vehicleactivitysummary, Long> {
	@Query(value = "select count(distinct drivsum.vehicleid) from vehicleactivitysummary drivsum where drivsum.activitydate >= :date1 and drivsum.activitydate<= :date2 and drivsum.vehicleid in :listvehicleid and drivsum.totaldistance >= :totaldistancezero", nativeQuery = true)
	Long listalldriveractif(final List<Integer> listvehicleid, final BigDecimal totaldistancezero, final Date date1,
			final Date date2);

	@Query(value = "select count(distinct drivsum.driverid) from vehicleactivitysummary drivsum where drivsum.activitydate >= :date1 and drivsum.activitydate<= :date2 and drivsum.driverid in :listdriverid and drivsum.totaldistance >= :totaldistancezero", nativeQuery = true)
	Long listalldriveractiffromdriver(final List<Integer> listdriverid, final BigDecimal totaldistancezero,
			final Date date1, final Date date2);

	@Query(value = "select count(distinct drivsum.driverid) from vehicleactivitysummary drivsum where drivsum.activitydate >= :date1 and drivsum.activitydate<= :date2 and drivsum.vehicleid in :listvehid and drivsum.totaldistance >= :totaldistancezero", nativeQuery = true)
	Long listalldriveractiffromdriver2(final List<Integer> listvehid, final BigDecimal totaldistancezero,
			final Date date1, final Date date2);

	@Query(value = "select count(distinct drivsum.vehicleid) from vehicleactivitysummary drivsum where drivsum.activitydate >= :date1 and drivsum.activitydate<= :date2 and drivsum.driverid in :listdriverid and drivsum.totaldistance >= :totaldistancezero", nativeQuery = true)
	Long listallvehicleactiffromdriver(final List<Integer> listdriverid, final BigDecimal totaldistancezero,
			final Date date1, final Date date2);

	@Query(value = "select count(distinct drivsum.vehicleid) from vehicleactivitysummary drivsum where drivsum.activitydate >= :date1 and drivsum.activitydate<= :date2 and drivsum.vehicleid in :listvehid and drivsum.totaldistance >= :totaldistancezero", nativeQuery = true)
	Long listallvehicleactiffromdriver2(final List<Integer> listvehid, final BigDecimal totaldistancezero,
			final Date date1, final Date date2);

	@Query(value = "select sum(drivsum.totaldistance) as sum1,sum(drivsum.validduration) as sum2,sum(drivsum.totaldripduration) as sum3,sum(drivsum.totaldripduration - drivsum.validduration) as sum4,sum(EXTRACT(EPOCH FROM (lastenddatetime  -  firststartdatetime))) as hours from vehicleactivitysummary drivsum where drivsum.activitydate >= :date1 and drivsum.activitydate<= :date2 and drivsum.affiliateid in :affiliateid", nativeQuery = true)
	List<Object[]> sumalldistanceperperiodforaffiliate(final Date date1, final Date date2,
			final List<Integer> affiliateid);

	@Query(value = "select drivsum.affiliateid as ids,sum(drivsum.totaldistance) as totd,sum(drivsum.validduration) as vdur,sum(drivsum.totaldripduration) as totrdur,sum(drivsum.totaldripduration - drivsum.validduration) as sum4,sum(EXTRACT(EPOCH FROM (lastenddatetime  -  firststartdatetime))) as ts from vehicleactivitysummary drivsum where drivsum.activitydate >= :date1 and drivsum.activitydate<= :date2 and drivsum.affiliateid in :affiliateid group by drivsum.affiliateid", nativeQuery = true)
	List<Object[]> sumalldistanceperperiodforaffiliategroupby(final Date date1, final Date date2,
			final List<Integer> affiliateid);

	@Query(value = "select sum(drivsum.totaldistance) as sum1,sum(drivsum.validduration) as sum2,sum(drivsum.totaldripduration) as sum3,sum(drivsum.totaldripduration - drivsum.validduration) as sum4,sum(EXTRACT(EPOCH FROM (lastenddatetime  -  firststartdatetime))) as hours from vehicleactivitysummary drivsum where drivsum.activitydate >= :date1 and drivsum.activitydate<= :date2 and drivsum.clientid in :clientid", nativeQuery = true)
	List<Object[]> sumalldistanceperperiodforclient(final Date date1, final Date date2, final List<Integer> clientid);

	@Query(value = "select drivsum.clientid as ids,sum(drivsum.totaldistance) as totd,sum(drivsum.validduration) as vdur,sum(drivsum.totaldripduration) as totrdur,sum(drivsum.totaldripduration - drivsum.validduration) as sum4,sum(EXTRACT(EPOCH FROM (lastenddatetime  -  firststartdatetime))) as ts from vehicleactivitysummary drivsum where drivsum.activitydate >= :date1 and drivsum.activitydate<= :date2 and drivsum.clientid in :clientid group by drivsum.clientid", nativeQuery = true)
	List<Object[]> sumalldistanceperperiodforclientgroupby(final Date date1, final Date date2,
			final List<Integer> clientid);

	@Query(value = "select sum(drivsum.totaldistance) as sum1,sum(drivsum.validduration) as sum2,sum(drivsum.totaldripduration) as sum3,sum(drivsum.totaldripduration - drivsum.validduration) as sum4,sum(EXTRACT(EPOCH FROM (lastenddatetime  -  firststartdatetime))) as hours from vehicleactivitysummary drivsum where drivsum.activitydate >= :date1 and drivsum.activitydate<= :date2 and drivsum.driverid in :driverid", nativeQuery = true)
	List<Object[]> sumalldistanceperperiodfordriver(final Date date1, final Date date2, final List<Integer> driverid);

	@Query(value = "select drivsum.driverid as ids,sum(drivsum.totaldistance) as totd,sum(drivsum.validduration) as vdur,sum(drivsum.totaldripduration) as totrdur,sum(drivsum.totaldripduration - drivsum.validduration) as sum4,sum(EXTRACT(EPOCH FROM (lastenddatetime  -  firststartdatetime))) as ts from vehicleactivitysummary drivsum where drivsum.activitydate >= :date1 and drivsum.activitydate<= :date2 and drivsum.driverid in :driverid group by drivsum.driverid", nativeQuery = true)
	List<Object[]> sumalldistanceperperiodfordrivergroupby(final Date date1, final Date date2,
			final List<Integer> driverid);

	@Query(value = "select sum(drivsum.totaldistance) as sum1,sum(drivsum.validduration) as sum2,sum(drivsum.totaldripduration) as sum3,sum(drivsum.totaldripduration - drivsum.validduration) as sum4,sum(EXTRACT(EPOCH FROM (lastenddatetime  -  firststartdatetime))) as hours from vehicleactivitysummary drivsum where drivsum.activitydate >= :date1 and drivsum.activitydate<= :date2 and drivsum.transporterid in :transporterid", nativeQuery = true)
	List<Object[]> sumalldistanceperperiodfortransporter(final Date date1, final Date date2,
			final List<Integer> transporterid);

	@Query(value = "select drivsum.transporterid as ids,sum(drivsum.totaldistance) as totd,sum(drivsum.validduration) as vdur,sum(drivsum.totaldripduration) as totrdur,sum(drivsum.totaldripduration - drivsum.validduration) as sum4,sum(EXTRACT(EPOCH FROM (lastenddatetime  -  firststartdatetime))) as ts from vehicleactivitysummary drivsum where drivsum.activitydate >= :date1 and drivsum.activitydate<= :date2 and drivsum.transporterid in :transporterid group by drivsum.transporterid", nativeQuery = true)
	List<Object[]> sumalldistanceperperiodfortransportergroupby(final Date date1, final Date date2,
			final List<Integer> transporterid);

	@Query(value = "select sum(drivsum.totaldistance) as sum1,sum(drivsum.validduration) as sum2,sum(drivsum.totaldripduration) as sum3,sum(drivsum.totaldripduration - drivsum.validduration) as sum4,sum(EXTRACT(EPOCH FROM (lastenddatetime  -  firststartdatetime))) as hours from vehicleactivitysummary drivsum where drivsum.activitydate >= :date1 and drivsum.activitydate<= :date2 and drivsum.vehicleid in :vehicleid", nativeQuery = true)
	List<Object[]> sumalldistanceperperiodforvehicle(final Date date1, final Date date2, final List<Integer> vehicleid);

	@Query(value = "select drivsum.vehicleid as ids,sum(drivsum.totaldistance) as totd,sum(drivsum.validduration) as vdur,sum(drivsum.totaldripduration) as totrdur,sum(drivsum.totaldripduration - drivsum.validduration) as sum4,sum(EXTRACT(EPOCH FROM (lastenddatetime  -  firststartdatetime))) as ts from vehicleactivitysummary drivsum where drivsum.activitydate >= :date1 and drivsum.activitydate<= :date2 and drivsum.vehicleid in :vehicleid group by drivsum.vehicleid", nativeQuery = true)
	List<Object[]> sumalldistanceperperiodforvehiclegroupby(final Date date1, final Date date2,
			final List<Integer> vehicleid);

	@Query(value = "select sum(drivsum.totaldistance) from vehicleactivitysummary drivsum where drivsum.activitydate >= :date1 and drivsum.activitydate<= :date2 and drivsum.affiliateid = :affiliateid", nativeQuery = true)
	BigDecimal sumdistanceperperiodforaffiliate(final Date date1, final Date date2, final Integer affiliateid);

	@Query(value = "select sum(drivsum.totaldistance) from vehicleactivitysummary drivsum where drivsum.activitydate >= :date1 and drivsum.activitydate<= :date2 and drivsum.affiliateid in :listaffiliateid", nativeQuery = true)
	BigDecimal sumdistanceperperiodforclient(final Date date1, final Date date2, final List<Integer> listaffiliateid);

	@Query(value = "select sum(drivsum.totaldistance) from vehicleactivitysummary drivsum where drivsum.activitydate >= :date1 and drivsum.activitydate<= :date2 and drivsum.transporterid = :transporterid", nativeQuery = true)
	BigDecimal sumdistanceperperiodfortransporter(final Date date1, final Date date2, final Integer transporterid);

	@Query(value = "select sum(drivsum.totaldistance) from vehicleactivitysummary drivsum where drivsum.activitydate >= :date1 and drivsum.activitydate<= :date2 and drivsum.vehicleid = :vehicleid", nativeQuery = true)
	BigDecimal sumdistanceperperiodforvehicle(final Date date1, final Date date2, final Integer vehicleid);

	/**
	 * @Query(value = "select sum(drivsum.totaldistance) from vehicleactivitysummary
	 *              drivsum where drivsum.activitydate >= :date1 and
	 *              drivsum.activitydate<= :date2 and drivsum.driverid = :driverid",
	 *              nativeQuery = true) BigDecimal
	 *              sumdistanceperperiodfordriver(final Date date1, final Date
	 *              date2, final Integer driverid);
	 */

	@Query(value = "select sum(drivsum.totaldistance) from vehicleactivitysummary drivsum where drivsum.activitydate >= :date1 and drivsum.activitydate< :date2 and drivsum.driverid = :driverid", nativeQuery = true)
	BigDecimal sumdistanceperperiodfordriver2(final Date date1, final Date date2, final Integer driverid);
}
