package com.th.hab.history.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
public class HistoryTotalVo {
    private List<HistoryTotalDto> monthly;
    private List<HistoryTotalDto> weekly;

    @Override
    public String toString() {
        return "HistoryTotalVo{" +
                "monthly=" + Arrays.toString(monthly.toArray()) +
                ", weekly=" + Arrays.toString(weekly.toArray()) +
                '}';
    }
}
