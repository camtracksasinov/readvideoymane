//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.reports.dao;

import java.util.List;

public interface RewardRankingDaoInterface {
	List getRewardRanking(final Integer[] clientid, final Integer[] affiliateid, final Integer[] transporterid,
			final Integer[] driverid, final int startyear, final int endyear, final int startmonth, final int endmonth);

	List getRewardRankingByDriver(final int clientid, final int affiliateid, final int transporterid,
			final int driverid, final int startyear, final int endyear, final int startmonth, final int endmonth);
}
