package com.rkisuru.ezbuy.product;

import lombok.Builder;
import java.math.BigDecimal;

@Builder
public class ProductResponse {

    private Long id;
    private String name;
    private String description;
    private String category;
    private Double price;
    private Integer stock;
}
