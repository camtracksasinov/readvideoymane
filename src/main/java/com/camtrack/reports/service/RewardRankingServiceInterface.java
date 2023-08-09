//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.reports.service;

import java.util.List;

import com.camtrack.entities.User;
import com.camtrack.reports.bean.RawstatisticsBean;
import com.camtrack.reports.bean.RewardRanking;

public interface RewardRankingServiceInterface {
	List<RewardRanking> getRewardranking(final RawstatisticsBean rawstat, User user);
}
