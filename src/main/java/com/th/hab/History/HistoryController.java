package com.th.hab.History;

import com.th.hab.History.model.HistoryDto;
import com.th.hab.History.model.HistoryVo;
import com.th.hab.response.ApiResponse;
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
        return new ApiResponse<>(service.getHistory());
    }

    @PostMapping
    public ApiResponse<?> postHistory(@RequestBody HistoryDto dto) {

        return null;
    }
}
