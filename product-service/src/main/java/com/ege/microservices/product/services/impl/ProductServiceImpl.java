package com.ege.microservices.product.services.impl;

import com.ege.microservices.product.convert.DTOConverters.ProductDTOConverter;
import com.ege.microservices.product.entities.CategoryEntity;
import com.ege.microservices.product.entities.ProductEntity;
import com.ege.microservices.product.repositories.CategoryRepository;
import com.ege.microservices.product.repositories.ProductRepository;
import com.ege.microservices.product.services.ProductService;
import com.ege.microservices.product.services.dtos.ProductDto;
import com.ege.microservices.product.services.dtos.ProductRequestDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final ProductDTOConverter productDTOConverter;

    private final RestTemplate restTemplate;

    @Override
    public ProductDto createProduct(ProductRequestDto productRequestDto) {

        CategoryEntity categoryEntity = categoryRepository.findCategoryEntityByCategoryName(productRequestDto.getCategory().getCategoryName());

        if (categoryEntity == null){
            log.info("Related category with this product not found in createProduct() method: {}", productRequestDto.getCategory().getCategoryName());
            throw new ResourceNotFoundException("Related category with this product not found: " + productRequestDto.getCategory().getCategoryName());
        }

        log.info("Creating product: {}", productRequestDto.getProductName());
        ProductEntity productEntity = productDTOConverter.convertProductRequestDtoToProductEntity(productRequestDto);

        productEntity.setCategory(categoryEntity);

        productEntity.setCreatedAt(LocalDateTime.now());

        ProductEntity savedProduct = productRepository.save(productEntity);

        log.info("Product created with ID: {}", savedProduct.getProductId());

        return productDTOConverter.convertProductEntityToProductDto(savedProduct);
    }

    @Override
    public String deleteProduct(String productId) {

        log.info("Deleting product with ID: {}", productId);

        ProductEntity productEntity = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found: " + productId));


        productRepository.delete(productEntity);

        log.info("Product with ID {} deleted.", productId);

        return "Product with productId: " + productId + " has been deleted successfully.";
    }

    @Override
    public ProductDto updateProduct(String productId, ProductRequestDto productRequestDto) {

        CategoryEntity categoryEntity = categoryRepository.findCategoryEntityByCategoryName(productRequestDto.getCategory().getCategoryName());

        if (categoryEntity == null){
            log.info("Related category with this product not found in updateProduct() method: {}", productRequestDto.getCategory().getCategoryName());
            throw new ResourceNotFoundException("Related category '{}' with this product not found." + productRequestDto.getCategory().getCategoryName());
        }

        log.info("Updating product with ID: {}", productId);

        ProductEntity productEntity = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found: " + productId));

        productEntity.setProductName(productRequestDto.getProductName());
        productEntity.setDescription(productRequestDto.getDescription());
        productEntity.setQuantity(productRequestDto.getQuantity());
        productEntity.setPrice(productRequestDto.getPrice());
        productEntity.setCategory(categoryEntity);
        productEntity.setUpdatedAt(LocalDateTime.now());

        ProductEntity updatedProduct = productRepository.save(productEntity);

        log.info("Product with ID {} updated.", productId);

        return productDTOConverter.convertProductEntityToProductDto(updatedProduct);
    }

    @Override
    public ProductDto getProductById(String productId) {

        String url = "http://localhost:8085/api/product/get/id/" + productId;

        ResponseEntity<ProductDto> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ProductDto>() {}
        );

        return response.getBody();
    }

    @Override
    public List<ProductDto> getAllProducts() {

        String url = "http://localhost:8085/api/product/getAll";

        ResponseEntity<List<ProductDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProductDto>>() {}
        );

        return response.getBody();
    }

    @Override
    public List<ProductDto> getProductsByName(String productName) {

        String url = "http://localhost:8085/api/product/get/name/" + productName;

        ResponseEntity<List<ProductDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProductDto>>() {}
        );

        return response.getBody();

    }

    @Override
    public List<ProductDto> getProductsByDescription(String description) {

        String url = "http://localhost:8085/api/product/get/description/" + description;

        ResponseEntity<List<ProductDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProductDto>>() {}
        );

        return response.getBody();

    }

    @Override
    public List<ProductDto> getProductsByPrice(BigDecimal minPrice, BigDecimal maxPrice) {

        String url = "http://localhost:8085/api/product/get/price?minPrice=" + minPrice + "&maxPrice=" + maxPrice;

        ResponseEntity<List<ProductDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProductDto>>() {}
        );

        return response.getBody();

    }
}
