package com.th.hab.history.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoryTotalDto implements Comparable<HistoryTotalDto> {
    private String name;
    private int total;

    public HistoryTotalDto(String name) {
        this.name = name;
        total = 0;
    }

    @Override
    public int compareTo(HistoryTotalDto o) {
        return LocalDate.parse(name, DateTimeFormatter.ISO_LOCAL_DATE).isBefore(
                        LocalDate.parse(o.name, DateTimeFormatter.ISO_LOCAL_DATE)) ? -1 : 1;
    }
}
