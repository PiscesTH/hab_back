package com.th.hab.repository;

import com.th.hab.history.model.HistoryTotalDto;
import com.th.hab.entity.History;
import com.th.hab.entity.User;
import com.th.hab.history.model.HistoryVo;

import java.util.List;

public interface HistoryQdslRepository {
    List<HistoryTotalDto> selHistoryMonthlyTotal(User user);
    List<History> selHistoryForAWeek(User user);

    List<HistoryVo> findAllByUserOrderByIhistoryDescDateDesc(User user);
}
