package com.th.hab.history.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class HistoryDto {
    private Long icategory;
    private int amount;
    private String purpose;
    private LocalDate date;
}
