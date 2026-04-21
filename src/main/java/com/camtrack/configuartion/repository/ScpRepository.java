//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.configuartion.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.camtrack.entities.Scp;

public interface ScpRepository extends JpaRepository<Scp, Integer> {
	@Query("from  Scp scp  where scp.status = 1 and scp.clientid = :cus and scp.affiliateid = :affid and scp.transporterid = :transid")
	Optional<Scp> findUniqueSCP(final Integer cus, final Integer affid, final Integer transid);

}
