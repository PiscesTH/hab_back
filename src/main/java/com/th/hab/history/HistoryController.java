package com.th.hab.history;


import com.th.hab.history.model.*;
import com.th.hab.response.ApiResponse;
import com.th.hab.response.ResVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/history")
public class HistoryController {
    private final HistoryService service;

    @GetMapping
    public ApiResponse<List<HistoryVo>> getHistory() {
        return service.getHistory();
    }

    @PostMapping
    public ApiResponse<ResVo> postHistory(@RequestBody HistoryDto dto) {
        return new ApiResponse<>(service.postHistory(dto));
    }

    @GetMapping("/statistics")
    public ApiResponse<HistoryTotalVo> getHistoryStatistics() {
        ApiResponse<HistoryTotalVo> result = new ApiResponse<>(service.getHistoryStatistics());
        return result;
    }

    @DeleteMapping("/{ihistory}")
    public ApiResponse<ResVo> delHistory(@PathVariable long ihistory) {
        return new ApiResponse<>(service.delHistory(ihistory));
    }
}
