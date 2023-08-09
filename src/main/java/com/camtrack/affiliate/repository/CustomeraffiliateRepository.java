//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.affiliate.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.camtrack.entities.Customeraffiliate;

public interface CustomeraffiliateRepository extends JpaRepository<Customeraffiliate, Integer> {
	@Query("select distinct cusaff.affiliateid from Customeraffiliate cusaff  where cusaff.status = 1")
	List<Integer> findAllActiveAffiliateId();

	@Query("from  Customeraffiliate cusaff  where cusaff.status = 1 and cusaff.customerid.customerid in :listcostomerid")
	List<Customeraffiliate> findAllActiveCustomer(final List<Integer> listcostomerid);

	@Query("select distinct cusaff.affiliateid from Customeraffiliate cusaff  where cusaff.status = 1 and cusaff.customerid.customerid = :listcostomerid")
	List<Integer> findAllActiveCustomerAffID(final Integer listcostomerid);

	@Query("select distinct cusaff.affiliateid from Customeraffiliate cusaff  where cusaff.status = 1 and cusaff.customerid.customerid in :listcostomerid")
	List<Integer> findAllActiveCustomerAffID(final List<Integer> listcostomerid);

	@Query("from  Customeraffiliate cusaff  where cusaff.status = 1")
	List<Customeraffiliate> findAllActiveCustomerAffiliate();

	@Query(value = "select cusaff.affiliateid,cusaff.name from  customeraffiliate cusaff  where cusaff.status = 1", nativeQuery = true)
	List<Object[]> findNativelyAllActiveCustomerAffiliateId();

	@Query(value = "select cuss.name from customeraffiliate cuss  where cuss.affiliateid = :affiliateid", nativeQuery = true)
	String nameobject(final Integer affiliateid);

	@Transactional
	@Modifying
	@Query(value = "update customeraffiliate set status = 0 where affiliateid = :affiliateid", nativeQuery = true)
	Integer updatestatusaffiliate(final Integer affiliateid);
}
