//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.configuartion.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.camtrack.entities.Scpfatigueparameter;

public interface ScpfatigueparameterRepository extends JpaRepository<Scpfatigueparameter, Integer> {
	@Query(value = "select * from  scpfatigueparameter scp  where scp.status = 1 and scp.clientid = :cus and scp.affiliateid = :affid and scp.transporterid = :transid and scp.fatigparamid = :fatigparamid", nativeQuery = true)
	Optional<Scpfatigueparameter> findUniqueSCP(final Integer cus, final Integer affid, final Integer transid,
			final Integer fatigparamid);
}
