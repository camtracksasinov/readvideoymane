//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.camtrack.entities.Allalertlevel;

public interface AllalertlevelRepository extends JpaRepository<Allalertlevel, Integer> {
	@Query("from Allalertlevel alls where alls.affiliateid.affiliateid = :affiliateid and alls.status = 1")
	Optional<Allalertlevel> findAffiliateAlert(final Integer affiliateid);

	@Query(value = "select distinct alls.alertlevelid from allalertlevel alls where alls.affiliateid = :affiliateid and alls.status = 1", nativeQuery = true)
	List<Integer> findAllAffiliateLevelAlertId(final Integer affiliateid);

	@Query(value = "select distinct alls.alertlevelid from allalertlevel alls where alls.clientid = :customerid and alls.status = 1", nativeQuery = true)
	List<Integer> findAllCustomerLevelAlertId(final Integer customerid);

	@Query("from Allalertlevel alls where alls.clientid.customerid = :customerid and alls.status = 1")
	Optional<Allalertlevel> findCustomerLevelAlert(final Integer customerid);
}
