package com.ege.microservices.product.services.impl;

import com.ege.microservices.product.convert.DTOConverters.ProductDTOConverter;
import com.ege.microservices.product.entities.CategoryEntity;
import com.ege.microservices.product.entities.ProductEntity;
import com.ege.microservices.product.repositories.CategoryRepository;
import com.ege.microservices.product.repositories.ProductRepository;
import com.ege.microservices.product.services.LogService;
import com.ege.microservices.product.services.ProductService;
import com.ege.microservices.product.services.dtos.ProductDto;
import com.ege.microservices.product.services.dtos.ProductRequestDto;
import com.ege.microservices.product.services.dtos.rabbitdtos.ProductRabbitDTO;
import com.ege.microservices.product.utils.rabbitutils.RabbitMQClient;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private final LogService logService;

    /// 03.02.2025
    private final ObjectMapper objectMapper;
    private static final String LOG_QUEUE = "log_service_queue";
    private final RabbitMQClient rabbitMQClient;

    @Override
    public ProductDto createProduct(ProductRequestDto productRequestDto) {

        CategoryEntity categoryEntity = categoryRepository.findCategoryEntityByCategoryName(productRequestDto.getCategory().getCategoryName());

        if (categoryEntity == null) {

            log.info("Related category with this product not found in createProduct() method: {}", productRequestDto.getCategory().getCategoryName());
            throw new ResourceNotFoundException("Related category with this product not found: " + productRequestDto.getCategory().getCategoryName());

        }

        ProductEntity existingProduct = productRepository.findByProductName(productRequestDto.getProductName());

        // If there is already a product with the same name, instead of creating a new row with the same name, just increase the quantity of this product
        if (existingProduct != null) {

            log.info("Product already exists with name {}. Increasing quantity by {}", productRequestDto.getProductName(), productRequestDto.getQuantity());

            existingProduct.setQuantity(existingProduct.getQuantity() + productRequestDto.getQuantity());

            existingProduct.setUpdatedAt(LocalDateTime.now());

            ProductEntity updatedProduct = productRepository.save(existingProduct);

            log.info("Updated product quantity. New quantity: {}", updatedProduct.getQuantity());

            /// 03.02.2025
            logToService("INFO", "Product already exists. Updated product quantity. New quantity: " + updatedProduct.getQuantity());

            return productDTOConverter.convertProductEntityToProductDto(updatedProduct);

        }

        log.info("Creating product: {}", productRequestDto.getProductName());

        ProductEntity productEntity = productDTOConverter.convertProductRequestDtoToProductEntity(productRequestDto);

        productEntity.setCategory(categoryEntity);

        productEntity.setCreatedAt(LocalDateTime.now());

        productEntity.setUpdatedAt(LocalDateTime.now());

        ProductEntity savedProduct = productRepository.save(productEntity);

        log.info("Product created with ID: {}", savedProduct.getProductId());

        // for Logging Service
      //  logService.sendLog("INFO", "Creating product with ID: " + savedProduct.getProductId(), "Product Service");

        /// 03.02.2025
        logToService("INFO", "Creating product with ID: " + savedProduct.getProductId());

        return productDTOConverter.convertProductEntityToProductDto(savedProduct);
    }

    @Override
    public String deleteProduct(String productId) {

        log.info("Deleting product with ID: {}", productId);

        ProductEntity productEntity = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found: " + productId));

        productRepository.delete(productEntity);

        log.info("Product with ID {} deleted.", productId);

        // for Logging Service
       // logService.sendLog("INFO", "Deleting product with ID: " + productId, "Product Service");

        /// 03.02.2025
        logToService("INFO", "Deleting product with ID: " + productId);

        return "Product with productId: " + productId + " has been deleted successfully.";
    }

    @Override
    public ProductDto updateProduct(String productId, ProductRequestDto productRequestDto) {

        CategoryEntity categoryEntity = categoryRepository.findCategoryEntityByCategoryName(productRequestDto.getCategory().getCategoryName());

        if (categoryEntity == null) {
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

        // for Logging Service
       // logService.sendLog("INFO", "Updating product with ID: " + updatedProduct.getProductId(), "Product Service");

        /// 03.02.2025
        logToService("INFO", "Updating product with ID: " + updatedProduct.getProductId());

        return productDTOConverter.convertProductEntityToProductDto(updatedProduct);
    }


    @Override
    @Transactional
    public void decreaseStock(List<ProductRabbitDTO> products) {

        for (ProductRabbitDTO product : products){
            ProductEntity dbProduct = productRepository.findById(product.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product not found: " + product.getProductId()));

            if (dbProduct.getQuantity() < product.getQuantity()){
                throw new RuntimeException("Insufficient stock for product ID: " + product.getProductId());
            }

            dbProduct.setQuantity(dbProduct.getQuantity() - product.getQuantity());
            dbProduct.setUpdatedAt(LocalDateTime.now());
            productRepository.save(dbProduct);
            log.info("Decreased stock for product ID: {}", product.getProductId());

            // for Logging Service
           // logService.sendLog("INFO", "Decreasing stock of product with ID: " + dbProduct.getProductId() + "by " + product.getQuantity(), "Product Service");

            /// 03.02.2025
            logToService("INFO", "Decreasing stock of product with ID: " + dbProduct.getProductId() + "by " + product.getQuantity());


        }
    }

    @Override
    public void increaseStock(List<ProductRabbitDTO> products) {

        for (ProductRabbitDTO product : products){
            ProductEntity dbProduct = productRepository.findById(product.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product not found: " + product.getProductId()));

            dbProduct.setQuantity(dbProduct.getQuantity() + product.getQuantity());
            dbProduct.setUpdatedAt(LocalDateTime.now());
            productRepository.save(dbProduct);
            log.info("Increased stock for product ID: {}", product.getProductId());

            // for Logging Service
           // logService.sendLog("INFO", "Increasing stock of product with ID: " + dbProduct.getProductId() + "by " + product.getQuantity(), "Product Service");

            /// 03.02.2025
            logToService("INFO", "Increasing stock of product with ID: " + dbProduct.getProductId() + "by " + product.getQuantity());

        }

    }

    @Override
    public ProductDto getProductById(String productId) {

        String url = "http://product-search:8085/api/product/get/id/" + productId;

        ResponseEntity<ProductDto> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ProductDto>() {
                }
        );

        return response.getBody();
    }

    @Override
    public List<ProductDto> getProductsByName(String productName) {

        String url = "http://product-search:8085/api/product/get/name/" + productName;

        ResponseEntity<List<ProductDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProductDto>>() {
                }
        );

        return response.getBody();

    }

    @Override
    public List<ProductDto> getProductsByDescription(String description) {

        String url = "http://product-search:8085/api/product/get/description/" + description;

        ResponseEntity<List<ProductDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProductDto>>() {
                }
        );

        return response.getBody();

    }

    @Override
    public List<ProductDto> getProductsByPrice(BigDecimal minPrice, BigDecimal maxPrice) {

        String url = "http://product-search:8085/api/product/get/price?minPrice=" + minPrice + "&maxPrice=" + maxPrice;

        ResponseEntity<List<ProductDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProductDto>>() {
                }
        );

        return response.getBody();

    }

    @Override
    public List<ProductDto> getProductsByCategoryName(String categoryName) {

        String url = "http://product-search:8085/api/product/get/category/" + categoryName;

        ResponseEntity<List<ProductDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProductDto>>() {
                }
        );

        return response.getBody();

    }

    @Override
    public List<ProductDto> getAllProducts() {

        String url = "http://product-search:8085/api/product/getAll";

        ResponseEntity<List<ProductDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProductDto>>() {
                }
        );

        return response.getBody();
    }



    ///  03.02.2025
    private void logToService(String level, String message) {
        try {
            String logPayload = objectMapper.writeValueAsString(new LogRequest("product-service", level, message));
            rabbitMQClient.sendAndReceive(LOG_QUEUE, logPayload);
        } catch (Exception e) {
            System.err.println("Failed to send log to RabbitMQ: " + message);
        }
    }

    /// 03.02.2025
    private static class LogRequest {
        private String service;
        private Content content;

        public LogRequest(String service, String level, String message) {
            this.service = service;
            this.content = new Content(level, message);
        }

        private static class Content {
            private String level;
            private String message;

            public Content(String level, String message) {
                this.level = level;
                this.message = message;
            }
        }
    }


}
