package com.rkisuru.ezbuy.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

    private ProductMapper productMapper;

    @BeforeEach
    void setUp() {
        productMapper = new ProductMapper();
    }

    @Test
    public void toProductTest() {

        ProductRequest productRequest = new ProductRequest(
                "Test Product",
                "Test Description",
                100.0,
                "ELECTRONICS",
                10
        );

        Product product = productMapper.toProduct(productRequest);

        assertEquals(productRequest.name(), product.getName());
        assertEquals(productRequest.description(), product.getDescription());
        assertEquals(productRequest.price(), product.getPrice());
        assertEquals(productRequest.stock(), product.getStock());
        assertEquals(Category.valueOf(productRequest.category()), product.getCategory());
    }

    @Test
    public void toProductResponseTest() {

        Product product = Product.builder()
                .id(1L)
                .name("Test Product")
                .description("Test Description")
                .price(100.0)
                .stock(10)
                .category(Category.ELECTRONICS)
                .build();

        ProductResponse productResponse = productMapper.toProductResponse(product);

        assertEquals(product.getId(), productResponse.getId());
        assertEquals(product.getName(), productResponse.getName());
        assertEquals(product.getDescription(), productResponse.getDescription());
        assertEquals(product.getCategory().toString().substring(0, 1).toUpperCase() + product.getCategory().toString().substring(1).toLowerCase(), productResponse.getCategory());
        assertEquals(product.getPrice(), productResponse.getPrice());
        assertEquals(product.getStock(), productResponse.getStock());
    }
}