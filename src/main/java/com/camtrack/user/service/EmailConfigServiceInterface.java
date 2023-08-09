//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.camtrack.entities.User;
import com.camtrack.user.bean.EmailConfigBean;
import com.camtrack.user.bean.UpdateEmailConfigBean;

public interface EmailConfigServiceInterface {
	ResponseEntity<?> fetchlistuseremailparam(final User user);

	EmailConfigBean getConfigByUserId(final Integer userid);

	List getUsers(final int clientid, final int affiliateid, final int transporterid);

	ResponseEntity<?> saveconfig(final User user, final EmailConfigBean emailConfigBean);

	int updateConfig(final EmailConfigBean emailConfigBean);

	ResponseEntity<?> updateemailparam(final User user, final UpdateEmailConfigBean updatemailparam);
}
