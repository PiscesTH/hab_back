package com.th.hab.history.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.th.hab.category.model.CategoryVo;
import com.th.hab.entity.Category;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
public class HistoryVo {
    private Long ihistory;
    private String date;
    private int amount;
    private long icategory;
    private String category;
    private String purpose;
    @JsonIgnore
    private LocalDateTime originDate;
}
