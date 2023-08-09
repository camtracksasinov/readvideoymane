//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.reports.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.camtrack.entities.Formatexports;

public interface FormatexportsRepository extends JpaRepository<Formatexports, Short> {
	@Query("FROM Formatexports f WHERE f.ids = :ids or f.uniqueid = :uniqueid")
	List<Formatexports> findbyFormatExports(final Short ids, final String uniqueid);
}
