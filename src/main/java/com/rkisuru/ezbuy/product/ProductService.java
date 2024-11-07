package com.rkisuru.ezbuy.product;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toProductResponse)
                .toList();
    }

    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));
        return productMapper.toProductResponse(product);
    }

    public Product saveProduct(ProductRequest request) {
        return productRepository.save(productMapper.toProduct(request));
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Product updateProduct(Long id, ProductRequest request) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Product not found"));
        if (!request.name().isBlank()) {
            existingProduct.setName(request.name());
        }
        if (!request.description().isBlank()) {
            existingProduct.setDescription(request.description());
        }
        if (request.price() != null) {
            existingProduct.setPrice(request.price());
        }
        if (request.category() != null) {
            existingProduct.setCategory(Category.valueOf(request.category()));
        }
        return productRepository.save(existingProduct);
    }

}
