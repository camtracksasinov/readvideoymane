//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.reports.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.camtrack.entities.Listreports;

public interface ListreportsRepository extends JpaRepository<Listreports, Integer> {
	@Query("FROM Listreports f WHERE f.ids = :ids or f.codereports = :codereports")
	List<Listreports> findbyReportId(final Integer ids, final Integer codereports);
}
