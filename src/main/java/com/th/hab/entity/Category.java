package com.th.hab.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Category extends CreatedAtEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long icategory;

    @Column String category;
}
