package com.tigeranalytics.bean;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "pricing_feeds")
@Getter
@Setter
public class PricingFeedBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer storeId;
    private String sku;
    private String productName;
    private Float price;
    private LocalDate date;

}
