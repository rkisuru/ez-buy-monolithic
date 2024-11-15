package com.rkisuru.ezbuy.product;

import org.springframework.stereotype.Service;

@Service
public class ProductMapper {

    public Product toProduct(ProductRequest productRequest) {
        return Product.builder()
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price())
                .stock(productRequest.stock())
                .category(Category.valueOf(productRequest.category()))
                .build();
    }

    public ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .category(product.getCategory().toString().substring(0, 1).toUpperCase() + product.getCategory().toString().substring(1).toLowerCase())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();
    }
}
