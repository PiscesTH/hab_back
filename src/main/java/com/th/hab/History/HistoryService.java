package com.th.hab.History;

import com.th.hab.History.model.HistoryDto;
import com.th.hab.History.model.HistoryVo;
import com.th.hab.Repository.CategoryRepository;
import com.th.hab.Repository.HistoryRepository;
import com.th.hab.Repository.UserRepository;
import com.th.hab.entity.History;
import com.th.hab.entity.User;
import com.th.hab.response.ApiResponse;
import com.th.hab.response.ResVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HistoryService {
    private final HistoryRepository historyRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    final Long tmpUserId = 1L;

    public ApiResponse<List<HistoryVo>> getHistory() {
        User user = userRepository.getReferenceById(tmpUserId);
        List<History> historyList = historyRepository.findAllByUser(user);
        List<HistoryVo> resultList = historyList.stream().map(item ->
                HistoryVo.builder()
                        .date(item.getDate())
                        .purpose(item.getPurpose())
                        .amount(item.getAmount())
                        .ihistory(item.getIhistory())
                        .category(item.getCategory())
                        .build())
                .toList();
        return new ApiResponse<>(resultList);
    }

    public ResVo postHistory(HistoryDto dto) {
        User user = userRepository.getReferenceById(tmpUserId);
        History history = new History();
        history.setAmount(dto.getAmount());
        history.setDate(dto.getDate());
        history.setUser(user);
        history.setPurpose(dto.getPurpose());
        history.setCategory(categoryRepository.getReferenceById(dto.getIcategory()));
        historyRepository.save(history);
        return new ResVo(history.getIhistory().intValue());
    }
}
