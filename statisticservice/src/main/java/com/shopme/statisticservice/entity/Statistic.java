package com.shopme.statisticservice.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "statistic")
@Data
public class Statistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String message;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time")
    private Date createTime;
}
