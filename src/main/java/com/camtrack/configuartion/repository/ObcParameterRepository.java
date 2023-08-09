//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.configuartion.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.camtrack.entities.Obcparameter;

public interface ObcParameterRepository extends JpaRepository<Obcparameter, Integer> {
	@Query("from  Obcparameter obc  where obc.status = 1 and obc.names = :name")
	Optional<Obcparameter> findUniqueName(final String name);
}
