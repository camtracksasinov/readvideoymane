//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.camtrack.entities.Parametertype;

public interface ParametertypeRepository extends JpaRepository<Parametertype, Integer> {
	@Query("from  Parametertype paramtype  where paramtype.status.statusid = 1")
	List<Parametertype> findAllActiveParametertype();

	@Query(value = "select parametertypeid,name,shortname,eventcode,sources from parametertype paramtype  where paramtype.status = 1", nativeQuery = true)
	List<?> findAllExceptionId();

	@Query(value = "select parametertypeid from parametertype paramtype  where paramtype.status = 1", nativeQuery = true)
	List<Integer> findAllExceptionIdSuperAdmin();

	@Query(value = "select shortname from parametertype paramtype  where paramtype.status = 1 and paramtype.parametertypeid in :listparametertypeid", nativeQuery = true)
	List<String> findAllShortNameExceptionId(final List<Integer> listparametertypeid);

	// List<?> filterexception();//reelexception
	@Query(value = "select parametertypeid,name,shortname,eventcode,sources from parametertype paramtype  where paramtype.reelexception = true and paramtype.status = 1", nativeQuery = true)
	List<?> filterexception();
}
