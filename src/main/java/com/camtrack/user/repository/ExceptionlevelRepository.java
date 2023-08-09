//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.camtrack.entities.Exceptionlevel;

public interface ExceptionlevelRepository extends JpaRepository<Exceptionlevel, Integer> {
	@Query(value = "select exceptionlevelid as id,name as nm from  exceptionlevel excepts  where excepts.exceptionlevelid <> 0", nativeQuery = true)
	List<?> findAllActiveParametertype();
}
