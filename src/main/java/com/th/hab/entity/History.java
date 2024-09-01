package com.th.hab.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class History extends BaseEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ihistory;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "iuser")
    private User user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "icategory")
    private Category category;

    @Column
    private int amount;

    @Column
    private String purpose;

    @Column
    private LocalDateTime date;
}
