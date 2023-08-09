//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.dao;

import java.util.List;

import com.camtrack.user.bean.EmailConfigBean;

public interface EmailConfigDaoInterface {
	List getConfigByUserId(final int userid);

	List getUsers(final int clientid, final int affiliateid, final int transporterid);

	List Parameters();

	int updateConfig(final EmailConfigBean emailConfigBean);
}
