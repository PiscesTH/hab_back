package com.th.hab.history;

import com.th.hab.history.model.HistoryDto;
import com.th.hab.history.model.HistoryTotalDto;
import com.th.hab.history.model.HistoryTotalVo;
import com.th.hab.history.model.HistoryVo;
import com.th.hab.Repository.CategoryRepository;
import com.th.hab.Repository.HistoryRepository;
import com.th.hab.Repository.UserRepository;
import com.th.hab.entity.History;
import com.th.hab.entity.User;
import com.th.hab.response.ApiResponse;
import com.th.hab.response.ResVo;
import com.th.hab.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
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


    public ApiResponse<List<HistoryVo>> getHistory() {
        long userPk = authenticationFacade.getLoginUserPk();
        User user = userRepository.getReferenceById(userPk != 0 ? userPk : sampleUserId);
        List<History> historyList = historyRepository.findAllByUser(user);
        List<HistoryVo> resultList = historyList.stream()
                .sorted(Comparator.comparing(History::getDate).reversed())
                .map(item ->
                        HistoryVo.builder()
                                .date(item.getDate().toLocalDate().toString())
                                .purpose(item.getPurpose())
                                .amount(item.getAmount())
                                .ihistory(item.getIhistory())
                                .category(item.getCategory())
                                .build())
                .toList();
        return new ApiResponse<>(resultList);
    }

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
        log.info("monthly:{}", monthly);
        List<History> tmpWeekly = historyRepository.selHistoryForAWeek(user);
        Map<String, HistoryTotalDto> map = new HashMap<>();
        for (History history : tmpWeekly) {
            HistoryTotalDto dto = new HistoryTotalDto();
            dto.setName(history.getDate().toLocalDate().toString());
            dto.setTotal(history.getAmount());
            map.put(getKey(history.getDate()), dto);
        }
        LocalDate baseDay = LocalDate.now().minusDays(7);
        for (int i = 0; i < 7; i++) {
            String key = getKey(baseDay.atStartOfDay());
            map.computeIfAbsent(key, HistoryTotalDto::new);
            baseDay = baseDay.plusDays(1);
        }
        List<HistoryTotalDto> weekly = map.values().stream().sorted().toList();
/*        List<HistoryTotalDto> weekly = tmpWeekly.stream().map(item ->
                        HistoryTotalDto.builder()
                                .name(item.getDate().toLocalDate().toString())
                                .total(item.getAmount())
                                .build())
                .toList();*/
        log.info("weekly : {}", weekly);
        return new HistoryTotalVo(monthly, weekly);
    }

    private String getKey(LocalDateTime time) {
        return time.getYear() + "-" + String.format("%02d", time.getMonthValue()) + "-" + String.format("%02d", time.getDayOfMonth());
    }
}
