package com.tigeranalytics.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PricingFeed {


    @NotBlank
    private String storeId;
    @NotBlank
    private String sku;
    private String productName;
    @NotBlank
    private BigDecimal price;
    private LocalDate date;

    // getters and setters
}
