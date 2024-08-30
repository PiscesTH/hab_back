package com.th.hab.History.model;

import com.th.hab.entity.Category;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HistoryVo {
    private Long ihistory;
    private String date;
    private int amount;
    private Category category;
    private String purpose;
}
