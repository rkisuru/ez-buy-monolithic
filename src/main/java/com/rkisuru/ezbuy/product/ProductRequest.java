package com.rkisuru.ezbuy.product;

public record ProductRequest(
        String name,
        String description,
        Double price,
        String category,
        Integer stock
) {
}
