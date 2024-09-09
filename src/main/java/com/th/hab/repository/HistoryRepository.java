package com.th.hab.repository;

import com.th.hab.entity.History;
import com.th.hab.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long>, HistoryQdslRepository {
    long deleteByIhistoryAndUser(long ihistory, User user);
}
