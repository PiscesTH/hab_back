package com.th.hab.history.model;

import com.th.hab.entity.Category;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class HistoryVo {
    private Long ihistory;
    private String date;
    private int amount;
    private Category category;
    private String purpose;
}
