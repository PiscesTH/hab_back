package com.th.hab.History.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HistoryDto {
    private Long icategory;
    private int amount;
    private String purpose;
    private LocalDateTime date;
}
