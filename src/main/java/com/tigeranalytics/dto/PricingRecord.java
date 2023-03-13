package com.tigeranalytics.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@Getter
@Setter
public class PricingRecord {

    private Long id;
    @NotNull
    private Integer storeId;
    @NotNull
    private String sku;
    private String productName;
    private Float price;
    private LocalDate date;

    // getters and setters
}
