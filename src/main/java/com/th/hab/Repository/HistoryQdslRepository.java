package com.th.hab.Repository;

import com.th.hab.History.model.HistoryTotalVo;

import java.util.List;

public interface HistoryQdslRepository {
    List<HistoryTotalVo> selHistoryGroupTotal();
    //    List<UserEntity> selUserAll(AdminSelAllUserDto dto, Pageable pageable);
//    List<UserEntity> selUserSignupStatistics(AdminSelUserSignupDto dto);
//    AdminSelAllUserVo selUserAllCount(AdminSelAllUserDto dto);
}
