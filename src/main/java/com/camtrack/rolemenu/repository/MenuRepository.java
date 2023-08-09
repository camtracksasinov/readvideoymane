//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.rolemenu.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.camtrack.entities.Menu;

public interface MenuRepository extends JpaRepository<Menu, Integer> {
	@Query(value = "select * from  menu menu  where menu.menustatusid =1 and menu.menuid = :menuids", nativeQuery = true)
	Menu findByIDs(final Integer menuids);

	@Query("from  Menu menu  where menu.menustatusid.statusid =1 and menu.description = :description")
	Optional<Menu> findByMenuName(final String description);

	@Query("from  Menu menu  where menu.menustatusid.statusid =1")
	List<Menu> findListMenuActive();
}
