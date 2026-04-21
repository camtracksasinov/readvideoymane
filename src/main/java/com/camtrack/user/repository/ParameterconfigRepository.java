//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.camtrack.entities.Parameterconfig;
import com.camtrack.entities.Parametertype;

public interface ParameterconfigRepository extends JpaRepository<Parameterconfig, Long> {
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "update parameterconfig set createdby=:userid where createdby=:useridinit", nativeQuery = true)
	int createAccess(Integer userid,Integer useridinit);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "update parameterconfig set updatedby=:userid where updatedby=:useridinit", nativeQuery = true)
	int updateAccess(Integer userid,Integer useridinit);
	
	@Transactional
	@Modifying
	@Query(value = "update parameterconfig set status =0 where id = :ids", nativeQuery = true)
	Integer desactivateconfig(final Long ids);

	@Transactional
	@Modifying
	@Query(value = "delete from parameterconfig where id = :ids", nativeQuery = true)
	Integer deleteconfig(final Long ids);

	@Query("from  Parameterconfig params where params.status.statusid=1")
	List<Parameterconfig> findAllParamconfig();

	@Query("from  Parameterconfig params where params.status.statusid=1 and params.customerid.customerid=:customerid and params.parametertypeid.parametertypeid=:typeparameterid and  params.clientaffiliateid.affiliateid=:affiliateid order by params.id desc")
	List<Parameterconfig> findAllParamconfig(final Integer customerid, final Integer affiliateid,
			final Integer typeparameterid);

	@Query("from  Parameterconfig params where params.status.statusid=1 and params.clientaffiliateid.affiliateid in :listaff")
	List<Parameterconfig> findAllParamconfigOfAffiliate(final List<Integer> listaff);

	@Query("from  Parameterconfig params where params.status.statusid=1 and params.customerid.customerid in :listcustomer and params.clientaffiliateid is null")
	List<Parameterconfig> findAllParamconfigOfCustomer(final List<Integer> listcustomer);

	@Query("from  Parameterconfig params where params.status.statusid=1 and params.driverid in :listdriverid")
	List<Parameterconfig> findAllParamconfigOfDriver(final List<Integer> listdriverid);

	@Query("from  Parameterconfig params where params.status.statusid=1 and params.transporter in :listtransporter")
	List<Parameterconfig> findAllParamconfigOfTransporter(final List<Integer> listtransporter);

	@Query("from  Parameterconfig params where params.status.statusid=1 and params.vehicleid in :listvehicleid")
	List<Parameterconfig> findAllParamconfigOfVehicle(final List<Integer> listvehicleid);

	@Query(value = "select distinct parametertypeid from  Parameterconfig params where params.status=1 and params.clientaffiliateid =:affiliate", nativeQuery = true)
	List<Integer> findAllParameteridForAffiliate(final Integer affiliate);

	@Query(value = "select distinct parametertypeid from  Parameterconfig params where params.status=1 and params.customerid =:customerid and params.clientaffiliateid is null", nativeQuery = true)
	List<Integer> findAllParameteridForcustomer(final Integer customerid);

	@Query(value = "select * from  parameterconfig params where params.clientaffiliateid = :aff and params.status=1 and params.parametertypeid = :paramtype and ((params.frommonth > :frommonth and params.tomonth > :tomonth and params.frommonth < :tomonth)"
			+ "or (params.frommonth > :frommonth and params.tomonth < :tomonth)"
			+ "or (params.frommonth < :frommonth and params.tomonth > :tomonth)"
			+ "or(params.frommonth < :frommonth and params.tomonth < :tomonth and params.tomonth > :frommonth)) order by params.parametertypeid desc", nativeQuery = true)
	List<Parameterconfig> findByAffiliateConfigChauvechement(final Integer aff, final Integer paramtype,
			final Integer frommonth, final Integer tomonth);

	@Query(value = "select * from  parameterconfig params where params.clientaffiliateid = :aff and params.status=1 and params.parametertypeid = :paramtype and params.frommonth = :frommonth and params.tomonth = :tomonth order by params.parametertypeid desc", nativeQuery = true)
	List<Parameterconfig> findByAffiliateConfig(final Integer aff, final Integer paramtype, final Integer frommonth,
			final Integer tomonth);

	@Query(value = "select * from parameterconfig params where params.customerid = :cus and params.status=1 and params.parametertypeid = :paramtype and ((params.frommonth > :frommonth and params.tomonth > :tomonth and params.frommonth < :tomonth)"
			+ "or (params.frommonth > :frommonth and params.tomonth < :tomonth)"
			+ "or (params.frommonth < :frommonth and params.tomonth > :tomonth)"
			+ "or(params.frommonth < :frommonth and params.tomonth < :tomonth and params.tomonth > :frommonth)) order by params.parametertypeid desc", nativeQuery = true)
	List<Parameterconfig> findByCustomerConfigChauvechement(final Integer cus, final Integer paramtype,
			final Integer frommonth, final Integer tomonth);

	@Query(value = "select * from parameterconfig params where params.customerid = :cus  and params.status=1 and  params.parametertypeid = :paramtype and params.frommonth = :frommonth and params.tomonth = :tomonth order by params.parametertypeid desc", nativeQuery = true)
	List<Parameterconfig> findByCustomerConfig(final Integer cus, final Integer paramtype, final Integer frommonth,
			final Integer tomonth);

	@Query("from  Parameterconfig params where params.status.statusid=1 and params.driverid = :driv and params.parametertypeid = :paramtype and params.frommonth = :frommonth and params.tomonth = :tomonth order by params.parametertypeid desc")
	List<Parameterconfig> findByDriverConfig(final Integer driv, final Parametertype paramtype, final Integer frommonth,
			final Integer tomonth);

	@Query("from  Parameterconfig params where params.status.statusid=1 and params.driverid = :driv and params.parametertypeid = :paramtype and ((params.frommonth > :frommonth and params.tomonth > :tomonth and params.frommonth < :tomonth) "
			+ "or (params.frommonth > :frommonth and params.tomonth < :tomonth) "
			+ "or (params.frommonth < :frommonth and params.tomonth > :tomonth) "
			+ "or(params.frommonth < :frommonth and params.tomonth < :tomonth and params.tomonth > :frommonth)) order by params.parametertypeid desc")
	List<Parameterconfig> findByDriverConfigChauvechement(final Integer driv, final Parametertype paramtype,
			final Integer frommonth, final Integer tomonth);

	@Query("from  Parameterconfig params where params.status.statusid=1 and params.transporter = :trans and params.parametertypeid = :paramtype and ((params.frommonth > :frommonth and params.tomonth > :tomonth and params.frommonth < :tomonth)"
			+ "or (params.frommonth > :frommonth and params.tomonth < :tomonth)"
			+ "or (params.frommonth < :frommonth and params.tomonth > :tomonth)"
			+ "or(params.frommonth < :frommonth and params.tomonth < :tomonth and params.tomonth > :frommonth)) order by params.parametertypeid desc")
	List<Parameterconfig> findByTransporterConfigChauvechement(final Integer trans, final Parametertype paramtype,
			final Integer frommonth, final Integer tomonth);

	@Query("from  Parameterconfig params where params.status.statusid=1 and params.transporter = :trans and params.parametertypeid = :paramtype and params.frommonth = :frommonth and params.tomonth = :tomonth order by params.parametertypeid desc")
	List<Parameterconfig> findByTransporterConfig(final Integer trans, final Parametertype paramtype,
			final Integer frommonth, final Integer tomonth);

	@Query("from  Parameterconfig params where params.status.statusid=1 and params.vehicleid = :veh and params.parametertypeid = :paramtype and ((params.frommonth > :frommonth and params.tomonth > :tomonth and params.frommonth < :tomonth)"
			+ "or (params.frommonth > :frommonth and params.tomonth < :tomonth)"
			+ "or (params.frommonth < :frommonth and params.tomonth > :tomonth)"
			+ "or(params.frommonth < :frommonth and params.tomonth < :tomonth and params.tomonth > :frommonth)) order by params.parametertypeid desc")
	List<Parameterconfig> findByVehicleConfigChauvechement(final Integer veh, final Parametertype paramtype,
			final Integer frommonth, final Integer tomonth);

	@Query("from  Parameterconfig params where params.status.statusid=1 and params.vehicleid = :veh and params.parametertypeid = :paramtype and params.frommonth = :frommonth and params.tomonth = :tomonth order by params.parametertypeid desc")
	List<Parameterconfig> findByVehicleConfig(final Integer veh, final Parametertype paramtype, final Integer frommonth,
			final Integer tomonth);

	@Query("from  Parameterconfig params where params.status.statusid=1 and params.customerid.customerid=:customerid and   params.parametertypeid.parametertypeid=:typeparameterid and params.clientaffiliateid.affiliateid=:affiliateid")
	Optional<Parameterconfig> findParamconfigForTrajet(final Integer customerid, final Integer affiliateid,
			final Integer typeparameterid);

	@Query("from  Parameterconfig params where params.id = :paramid and params.status.statusid=1 and params.driverid in :listdriverid")
	List<Parameterconfig> findAllParamconfigOfDriverNight(final List<Integer> listdriverid, Integer paramid);

	@Query("from  Parameterconfig params where params.id = :paramid and params.status.statusid=1 and params.vehicleid in :listvehicleid")
	List<Parameterconfig> findAllParamconfigOfVehicleNight(final List<Integer> listvehicleid, Integer paramid);

	@Query("from  Parameterconfig params where params.id = :paramid and params.status.statusid=1 and params.transporter in :listtransporter")
	List<Parameterconfig> findAllParamconfigOfTransporterNight(final List<Integer> listtransporter, Integer paramid);

	@Query("from  Parameterconfig params where params.id = :paramid and params.status.statusid=1 and params.clientaffiliateid.affiliateid in :listaff")
	List<Parameterconfig> findAllParamconfigOfAffiliateNight(final List<Integer> listaff, Integer paramid);

	@Query("from  Parameterconfig params where params.id = :paramid and params.status.statusid=1 and params.customerid.customerid in :listcustomer and params.clientaffiliateid is null")
	List<Parameterconfig> findAllParamconfigOfCustomerNight(final List<Integer> listcustomer, Integer paramid);
}
