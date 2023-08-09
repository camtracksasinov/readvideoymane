//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.configuartion.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.camtrack.entities.Fatigueparameter;

public interface FatigueparameterRepository extends JpaRepository<Fatigueparameter, Integer> {
	@Query("from  Fatigueparameter obc  where obc.status = 1 and obc.names = :name")
	Optional<Fatigueparameter> findUniqueName(final String name);
}
