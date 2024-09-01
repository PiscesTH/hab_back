package com.th.hab.Repository;

import com.th.hab.History.model.HistoryTotalDto;
import com.th.hab.entity.History;
import com.th.hab.entity.User;

import java.util.List;

public interface HistoryQdslRepository {
    List<HistoryTotalDto> selHistoryMonthlyTotal(User user);
    List<History> selHistoryForAWeek(User user);
    //    List<UserEntity> selUserAll(AdminSelAllUserDto dto, Pageable pageable);
//    List<UserEntity> selUserSignupStatistics(AdminSelUserSignupDto dto);
//    AdminSelAllUserVo selUserAllCount(AdminSelAllUserDto dto);
}
