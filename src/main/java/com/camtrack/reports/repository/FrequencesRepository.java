//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.reports.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.camtrack.entities.Frequences;

public interface FrequencesRepository extends JpaRepository<Frequences, Short> {
	@Query("FROM Frequences f WHERE f.ids = :ids or f.uniqueid = :uniqueid")
	List<Frequences> findbyFrequencesOrCodes(final Short ids, final String uniqueid);
}
