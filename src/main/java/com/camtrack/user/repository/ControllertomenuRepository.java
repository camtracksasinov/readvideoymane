package com.camtrack.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.camtrack.entities.Controllertomenu;

public interface ControllertomenuRepository extends JpaRepository<Controllertomenu, Integer> {
	@Query("from Controllertomenu conn where conn.controllerstring = :pages")
	List<Controllertomenu> findByPages(String pages);
}
