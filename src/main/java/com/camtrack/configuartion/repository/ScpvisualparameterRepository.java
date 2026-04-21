//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.configuartion.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.camtrack.entities.Scpvisualparameter;

public interface ScpvisualparameterRepository extends JpaRepository<Scpvisualparameter, Integer> {
	@Query("from  Scpvisualparameter scp  where scp.status = 1 and scp.clientid = :cus and scp.affiliateid = :affid and scp.transporterid = :transid and scp.visualparamid = :visualparamid")
	Optional<Scpvisualparameter> findUniqueSCP(final Integer cus, final Integer affid, final Integer transid,
			final Integer visualparamid);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "delete from scpvisualparameter scp  where scp  where scp.clientid = :cus and scp.affiliateid = :affid and scp.transporterid = :transid", nativeQuery = true)
	int deletescpvisualparameter(final Integer cus, final Integer affid, final Integer transid);
}
