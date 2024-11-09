package com.rkisuru.ezbuy.product;

import java.math.BigDecimal;

public record ProductRequest(
        String name,
        String description,
        Double price,
        String category,
        Integer stock
) {
}
