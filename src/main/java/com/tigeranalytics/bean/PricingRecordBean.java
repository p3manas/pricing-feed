package com.tigeranalytics.bean;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "pricing_records")
@Getter
@Setter
public class PricingRecordBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer storeId;
    private String sku;
    private String productName;
    private Float price;
    private LocalDate date;

    // getters and setters
}
