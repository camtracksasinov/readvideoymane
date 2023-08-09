//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.rolemenu.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.camtrack.bean.CreateNewUserRole;
import com.camtrack.bean.Rightonmenu;
import com.camtrack.bean.RoleBean;
import com.camtrack.config.Mailer;
import com.camtrack.config.StaticValues;
import com.camtrack.config.Utils;
import com.camtrack.entities.Accessrights;
import com.camtrack.entities.Listconfigtypes;
import com.camtrack.entities.Menu;
import com.camtrack.entities.Reelroleusers;
import com.camtrack.entities.Success;
import com.camtrack.entities.SuccessWithIDCreate;
import com.camtrack.entities.User;
import com.camtrack.entities.Userlogsactivity;
import com.camtrack.entities.Userrole;
import com.camtrack.rolemenu.bean.RoleAndAccessInfos;
import com.camtrack.rolemenu.dao.RoleMenuDaoInterface;
import com.camtrack.rolemenu.repository.AccessRightRepository;
import com.camtrack.rolemenu.repository.MenuRepository;
import com.camtrack.user.repository.ListconfigtypesRepository;
import com.camtrack.user.repository.ReelroleusersRepository;
import com.camtrack.user.repository.UserlogsactivityRepository;
import com.camtrack.user.repository.UserroleRepository;

@Service("rolemenuService")
@CacheConfig(cacheNames = "rolemenucache")
public class RoleMenuServiceImpl implements RoleMenuServiceInterface {
	@Autowired
	AccessRightRepository accessrR;
	@Autowired
	ListconfigtypesRepository listconfig;
	@Autowired
	Mailer mailTemplate;
	@Autowired
	MenuRepository menuR;
	@Autowired
	ReelroleusersRepository reeluserR;
	@Autowired
	RoleMenuDaoInterface rolemenuDaoInterface;
	@Autowired
	UserlogsactivityRepository userlogRepo;
	@Autowired
	UserroleRepository userrR;

	@Override
	public ResponseEntity<?> createorupdatenewrole(final User user, final CreateNewUserRole newrole) {
		final String newname = newrole.getNewrolename();
		final Integer reelroleid = newrole.getIdrole();
		Reelroleusers reelrole = null;
		if (!Objects.isNull(reelroleid)) {
			reelrole = this.reeluserR.findById(reelroleid).orElse(null);
		}
		final Integer idtyperole = newrole.getIdtyperole();
		Userrole typerole = null;
		if (!Objects.isNull(reelrole)) {
			if (!Objects.isNull(idtyperole)) {
				if (!Objects.isNull(idtyperole)) {
					typerole = this.userrR.findById(idtyperole).orElse(null);
					if (!Objects.isNull(typerole)) {
						reelrole.setIdtyperole(typerole);
					}
				}
				if (!Objects.isNull(newname)) {
					reelrole.setRolenames(newname);
				}
				reelrole = this.reeluserR.saveAndFlush(reelrole);
			}
		} else {
			typerole = this.userrR.findById(idtyperole).orElse(null);
			if (Objects.isNull(typerole)) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new Success(StaticValues.Typeroledontexists, StaticValues.Typeroledontexists_Int));
			}
			reelrole = new Reelroleusers();
			reelrole.setIdtyperole(typerole);
			reelrole.setRolenames(newname);
			reelrole.setStatus(1);
			reelrole = this.reeluserR.saveAndFlush(reelrole);
		}
		final List<Rightonmenu> listmenuright = newrole.getListmenuwithright();
		final Date date = new Date();
		final Listconfigtypes paramconfigs = this.listconfig.findById(Utils.UserAccessTypeconfig()).orElse(null);
		if (!Objects.isNull(listmenuright) && !listmenuright.isEmpty()) {
			for (final Rightonmenu rightonmenu : listmenuright) {
				final Integer menuid = rightonmenu.getMenuid();
				if (!Objects.isNull(menuid)) {
					final Menu newmenu = this.menuR.findById(menuid).orElse(null);
					if (Objects.isNull(newmenu)) {
						continue;
					}
					Accessrights right = this.accessrR.findAccessRight(reelrole.getIds(), menuid).orElse(null);
					if (!Objects.isNull(right)) {
						final Boolean createornot = false;
						Accessrights.init(createornot, user);
						right.setAdd(rightonmenu.isAdd(), createornot);
						right.setEdit(rightonmenu.isUpdate(), createornot);
						right.setDelete(rightonmenu.isDelete(), createornot);
						right.setView(rightonmenu.isView(), createornot);
						right.setUpdatedby(user);
						right.setUpdatedon(date);
					} else {
						final Boolean createornot = true;
						right = new Accessrights();
						Accessrights.init(createornot, user);
						right.setAdd(rightonmenu.isAdd(), createornot);
						right.setEdit(rightonmenu.isUpdate(), createornot);
						right.setDelete(rightonmenu.isDelete(), createornot);
						right.setView(rightonmenu.isView(), createornot);
						right.setCreatedon(date);
						right.setMenuid(newmenu, createornot);
						right.setReelroleusers(reelrole, createornot);
						right.setUpdatedon(date);
						right.setUpdatedby(user);
						right.setCreatedby(user);
					}
					this.accessrR.saveAndFlush(right);
					if (Utils.countnumberligne(Accessrights.log) <= 1) {
						continue;
					}
					final Date datelog = new Date();
					final Userlogsactivity loguser = new Userlogsactivity(paramconfigs);
					loguser.setDates(datelog);
					loguser.setUserid(user);
					loguser.setLogsinfos(Accessrights.log);
					this.userlogRepo.saveAndFlush(loguser);
				}
			}
		}
		String ID = "-1";
		if (!Objects.isNull(reelrole)) {
			ID = "" + reelrole.getIds();
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(new SuccessWithIDCreate(StaticValues.success, StaticValues.success_Int, ID));
	}

	@Override
	public int deactivateRoleMenu(final String userRoleId) {
		return this.rolemenuDaoInterface.deactivateRoleMenu(userRoleId);
	}

	@Override
	@Cacheable("fetchallmenus")
	public ResponseEntity<?> fetchALLMenus() {
		return ResponseEntity.status(HttpStatus.OK).body(this.menuR.findListMenuActive());
	}

	@Override
	public ResponseEntity<?> fetchMenus(final int userRoleId) {
		final Reelroleusers role = this.reeluserR.findById(userRoleId).orElse(null);
		if (Objects.isNull(role)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Success(StaticValues.UserroledoesntExists, StaticValues.UserroledoesntExists_Int));
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(Utils.roletorightsmallrolemenu(role, this.accessrR, this.menuR));
	}

	@Override
	public ResponseEntity<?> fetchUserRoles(final User user) {
		final List<RoleBean> result = this.reeluserR
				.findAllHierarchical(user.getReelrole().getIdtyperole().getUserroleid());
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@Override
	public ResponseEntity<?> listallrolewithaccess(final User user) {
		final Reelroleusers role = user.getReelrole();
		List<RoleAndAccessInfos> listroleaccess = new ArrayList<>();
		if (Objects.isNull(role)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Success(StaticValues.Userroleislimits, StaticValues.Userroleislimits_Int));
		}
		final Integer typerole = role.getIdtyperole().getUserroleid();
		final List<RoleBean> listrole = this.reeluserR.findAllHierarchicalRoleNativeQuery(typerole);
		listroleaccess = Utils.roletorightmenu(listrole, this.accessrR);
		return ResponseEntity.status(HttpStatus.OK).body(listroleaccess);
	}

	@Override
	@Cacheable("listtyperole")
	public ResponseEntity<?> listtyperole() {
		return ResponseEntity.status(HttpStatus.OK).body(this.userrR.findAllActiveRole());
	}
}
