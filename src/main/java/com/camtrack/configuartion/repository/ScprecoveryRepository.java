//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.configuartion.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.camtrack.entities.Scprecovery;

public interface ScprecoveryRepository extends JpaRepository<Scprecovery, Integer> {
	@Query("from  Scprecovery scp  where scp.status = 1 and scp.clientid = :cusid and scp.affiliateid = :affid and scp.transporterid = :transid and scp.recoveryid = :recoveryid")
	Optional<Scprecovery> findUniqueSCP(final Integer cusid, final Integer affid, final Integer transid,
			final Integer recoveryid);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "delete from scprecovery scp  where scp  where scp.clientid = :cusid and scp.affiliateid = :affid and scp.transporterid = :transid", nativeQuery = true)
	int deletescprecovery(final Integer cusid, final Integer affid, final Integer transid);
}
