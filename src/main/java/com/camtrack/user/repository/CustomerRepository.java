//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.camtrack.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	@Query("from  Customer cus  where cus.status = 1")
	List<Customer> findAllActiveCustomer();

	@Query("select cus.customerid from  Customer cus  where cus.status = 1")
	List<Integer> findAllActiveCustomerId();

	@Query(value = "select cus.customerid,cus.name from  customer cus  where cus.status = 1", nativeQuery = true)
	List<Object[]> findNativelyAllActiveCustomerId();

	@Query(value = "select custom.name from customer custom  where custom.customerid = :customerid", nativeQuery = true)
	String nameobject(final Integer customerid);

	@Transactional
	@Modifying
	@Query(value = "update customer set status = 0 where customerid = :customerid", nativeQuery = true)
	Integer updatestatuscustomer(final Integer customerid);
}
