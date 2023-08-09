//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.rolemenu.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.camtrack.config.Utils;
import com.camtrack.rolemenu.bean.RoleMenuBean;

@Repository("rolemenuDao")
@PropertySource({ "classpath:sqlqueries.properties" })
public class RoleMenuDaoImpl implements RoleMenuDaoInterface {
	@Autowired
	private Environment environment;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int deactivateRoleMenu(final String userRoleId) {
		final String query = this.environment.getRequiredProperty("rolemenu.delete");
		return this.jdbcTemplate.update(query, new Object[] { userRoleId });
	}

	@Override
	public List fetchAllMenus() {
		final List menuDetails = new ArrayList();
		final String parentquery = this.environment.getRequiredProperty("rolemenu.parentmenus");
		final String childquery = this.environment.getRequiredProperty("rolemenu.allchildmenus");
		final List<Map<String, Object>> parentMenuList = this.jdbcTemplate.queryForList(parentquery);
		for (Map<String, Object> parentMenu : parentMenuList) {
			final List childMenuList = this.jdbcTemplate.queryForList(childquery,
					new Object[] { parentMenu.get("menuid") });
			if (Objects.isNull(childMenuList) || childMenuList.isEmpty()) {
				if (Utils.castIntegerObject(parentMenu.get("menuhome")) != 1) {
					continue;
				}
				final String qry1 = "select distinct * from accessrights join  menu on menu.menuid=accessrights.menuid where menu.menuid="
						+ parentMenu.get("menuid") + " and menu.menustatusid=1";
				final List<Map<String, Object>> ls = this.jdbcTemplate.queryForList(qry1);
				if (ls.size() <= 0) {
					continue;
				}
				parentMenu = ls.get(0);
				menuDetails.add(parentMenu);
			} else {
				menuDetails.add(parentMenu);
				menuDetails.addAll(childMenuList);
			}
		}
		return menuDetails;
	}

	@Override
	public List fetchMenus(final int userRoleId) {
		final List menuDetails = new ArrayList();
		final String parentquery = this.environment.getRequiredProperty("rolemenu.parentmenus");
		final String childquery = this.environment.getRequiredProperty("rolemenu.childmenus");
		final List<Map<String, Object>> parentMenuList = this.jdbcTemplate.queryForList(parentquery);
		for (Map<String, Object> parentMenu : parentMenuList) {
			final List childMenuList = this.jdbcTemplate.queryForList(childquery,
					new Object[] { userRoleId, parentMenu.get("menuid") });
			if (Objects.isNull(childMenuList) || childMenuList.isEmpty()) {
				if (Utils.castIntegerObject(parentMenu.get("menuhome")) != 1) {
					continue;
				}
				final String qry1 = "select distinct * from accessrights join  menu on menu.menuid=accessrights.menuid where accessrights.userroleid="
						+ userRoleId + " and menu.menuid=" + parentMenu.get("menuid") + " and menu.menustatusid=1";
				final List<Map<String, Object>> ls = this.jdbcTemplate.queryForList(qry1);
				if (ls.size() <= 0) {
					continue;
				}
				parentMenu = ls.get(0);
				menuDetails.add(parentMenu);
			} else {
				menuDetails.add(parentMenu);
				menuDetails.addAll(childMenuList);
			}
		}
		return menuDetails;
	}

	@Override
	public List fetchUserRoles() {
		final String query = this.environment.getRequiredProperty("userrole.map");
		final List userRoles = this.jdbcTemplate.queryForList(query);
		return userRoles;
	}

	@Override
	public int RoleMenumap(final RoleMenuBean rolemenuBean) {
		try {
			final String insertquery = this.environment.getRequiredProperty("userrolemenu.insert");
			final String userquery = this.environment.getRequiredProperty("userrolemenu.update");
			for (int i = 0; i < rolemenuBean.getMenuId().length; ++i) {
				final int j = this.jdbcTemplate.update(userquery,
						new Object[] { rolemenuBean.getAddselect()[i], rolemenuBean.getViewselect()[i],
								rolemenuBean.getEditselect()[i], rolemenuBean.getDeleteselect()[i],
								rolemenuBean.getUpdatedBy(), new Date(), Integer.parseInt(rolemenuBean.getUserRoleId()),
								rolemenuBean.getMenuId()[i] });
				if (j == 0) {
					this.jdbcTemplate.update(userquery,
							new Object[] { rolemenuBean.getMenuId()[i], Integer.parseInt(rolemenuBean.getUserRoleId()),
									rolemenuBean.getAddselect()[i], rolemenuBean.getViewselect()[i],
									rolemenuBean.getEditselect()[i], rolemenuBean.getDeleteselect()[i], new Date(),
									rolemenuBean.getCreatedBy(), new Date(), rolemenuBean.getUpdatedBy() });
				}
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
