package com.tigeranalytics.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@Getter
@Setter
public class PricingRecord {

    private Long id;
    @NotBlank
    private Integer storeId;
    @NotBlank
    private String sku;
    private String productName;
    @NotBlank
    private Float price;
    private LocalDate date;

    // getters and setters
}
