package com.th.hab.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class User {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long iuser;

    @Column
    private String uid;

    @Column
    private String upw;

    @Column(length = 10)
    private String nm;

    @Column(length = 50)
    private int email;
}
