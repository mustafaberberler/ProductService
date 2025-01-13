package com.ege.microservices.product.services;

import com.ege.microservices.product.services.dtos.ProductDto;
import com.ege.microservices.product.services.dtos.ProductRequestDto;
import com.ege.microservices.product.services.dtos.rabbitdtos.ProductRabbitDTO;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    // Create product
    ProductDto createProduct(ProductRequestDto productRequestDto);

    // Delete product
    String deleteProduct(String productId);

    // Update product by id
    ProductDto updateProduct(String productId, ProductRequestDto productRequestDto);

    // Find product by id
    ProductDto getProductById(String productId);

    // Find products by description
    List<ProductDto> getProductsByDescription(String description);

    // Find products by name
    List<ProductDto> getProductsByName(String productName);

    // Find products by price interval
    List<ProductDto> getProductsByPrice(BigDecimal minPrice, BigDecimal maxPrice);

    // Find products by category name
    List<ProductDto> getProductsByCategoryName(String categoryName);

    // Find all products
    List<ProductDto> getAllProducts();

    void decreaseStock(List<ProductRabbitDTO> products);

    void increaseStock(List<ProductRabbitDTO> products);

}
