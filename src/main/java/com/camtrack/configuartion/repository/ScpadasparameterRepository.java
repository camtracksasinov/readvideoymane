//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.configuartion.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.camtrack.entities.Scpadasparameter;

public interface ScpadasparameterRepository extends JpaRepository<Scpadasparameter, Integer> {
	@Query("from  Scpadasparameter scp  where scp.status = 1 and scp.clientid = :cus and scp.affiliateid = :affid and scp.transporterid = :transid and scp.adasparamid = :adasparamid")
	Optional<Scpadasparameter> findUniqueSCP(final Integer cus, final Integer affid, final Integer transid,
			final Integer adasparamid);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "delete from scpadasparameter scp where scp.clientid = :cus and scp.affiliateid = :affid and scp.transporterid = :transid", nativeQuery = true)
	int deletescpadasparameter(Integer cus, Integer affid, Integer transid);
}
