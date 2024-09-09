package com.th.hab.history;

import com.th.hab.entity.History;
import com.th.hab.entity.User;
import com.th.hab.history.model.HistoryDto;
import com.th.hab.history.model.HistoryTotalDto;
import com.th.hab.history.model.HistoryTotalVo;
import com.th.hab.history.model.HistoryVo;
import com.th.hab.repository.CategoryRepository;
import com.th.hab.repository.HistoryRepository;
import com.th.hab.repository.UserRepository;
import com.th.hab.response.ApiResponse;
import com.th.hab.response.ResVo;
import com.th.hab.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class HistoryService {
    private final HistoryRepository historyRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final AuthenticationFacade authenticationFacade;
    final Long sampleUserId = 1L;

    @Transactional
    public ApiResponse<List<HistoryVo>> getHistory() {
        long userPk = authenticationFacade.getLoginUserPk();
        User user = userRepository.getReferenceById(userPk != 0 ? userPk : sampleUserId);
        List<HistoryVo> historyList = historyRepository.findAllByUserOrderByIhistoryDescDateDesc(user);
        historyList.forEach(historyVo -> historyVo.setDate(historyVo.getOriginDate().toLocalDate().toString()));
        return new ApiResponse<>(historyList);
    }

    @Transactional
    public ResVo postHistory(HistoryDto dto) {
        long userPk = authenticationFacade.getLoginUserPk();
        User user = userRepository.getReferenceById(userPk != 0 ? userPk : sampleUserId);
        History history = new History();
        history.setAmount(dto.getAmount());
        history.setDate(dto.getDate().atStartOfDay());
        history.setUser(user);
        history.setPurpose(dto.getPurpose());
        history.setCategory(categoryRepository.getReferenceById(dto.getIcategory()));
        historyRepository.save(history);
        return new ResVo(history.getIhistory().intValue());
    }

    @Transactional
    public HistoryTotalVo getHistoryStatistics() {
        long userPk = authenticationFacade.getLoginUserPk();
        User user = userRepository.getReferenceById(userPk != 0 ? userPk : sampleUserId);
        List<HistoryTotalDto> monthly = historyRepository.selHistoryMonthlyTotal(user);
        List<History> tmpWeekly = historyRepository.selHistoryForAWeek(user);
        Map<String, HistoryTotalDto> map = new HashMap<>();
        for (History history : tmpWeekly) {
            HistoryTotalDto dto = new HistoryTotalDto();
            dto.setName(history.getDate().toLocalDate().toString());
            dto.setTotal(history.getAmount());
            map.put(getKey(history.getDate()), dto);
        }
        LocalDate baseDay = LocalDate.now().minusDays(7);
        for (int i = 1; i < 8; i++) {
            String key = getKey(baseDay.atStartOfDay());
            map.computeIfAbsent(key, HistoryTotalDto::new);
            baseDay = baseDay.plusDays(1);
        }
        List<HistoryTotalDto> weekly = map.values().stream().sorted().toList();
        return new HistoryTotalVo(monthly, weekly);
    }

    private String getKey(LocalDateTime time) {
        return time.getYear() + "-" + String.format("%02d", time.getMonthValue()) + "-" + String.format("%02d", time.getDayOfMonth());
    }

    @Transactional
    public ResVo delHistory(long ihistory) {
        long userPk = authenticationFacade.getLoginUserPk();
        User user = userRepository.getReferenceById(userPk);
        historyRepository.deleteByIhistoryAndUser(ihistory, user);
        return new ResVo((int)ihistory);
    }
}
