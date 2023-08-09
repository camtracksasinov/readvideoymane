//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.configuartion.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.camtrack.entities.Adasparameter;

public interface AdasparameterRepository extends JpaRepository<Adasparameter, Integer> {
	@Query("from  Adasparameter obc  where obc.status = 1 and obc.names = :name")
	Optional<Adasparameter> findUniqueName(final String name);
}
