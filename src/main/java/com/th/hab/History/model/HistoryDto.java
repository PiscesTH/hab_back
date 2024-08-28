package com.th.hab.History.model;

import lombok.Data;

@Data
public class HistoryDto {
    private Long icategory;
    private int amount;
    private String purpose;
    private String date;
}
