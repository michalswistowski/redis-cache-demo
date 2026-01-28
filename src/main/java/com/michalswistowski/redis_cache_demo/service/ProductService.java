package com.michalswistowski.redis_cache_demo.service;

import com.michalswistowski.redis_cache_demo.dto.ProductDto;
import com.michalswistowski.redis_cache_demo.entity.Product;
import com.michalswistowski.redis_cache_demo.repository.ProductRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    public static final String PRODUCT_CACHE = "products";
    private final ProductRepository productRepository;
//    private final CacheManager cacheManager;

//    public ProductService(ProductRepository productRepository, CacheManager cacheManager) {
//        this.productRepository = productRepository;
//        this.cacheManager = cacheManager;
//    }

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @CachePut(value = PRODUCT_CACHE, key = "#result.id()")
    public ProductDto createProduct(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.name());
        product.setPrice(productDto.price());

        Product savedProduct = productRepository.save(product);

//      it will try to get cache with name PRODUCT_CACHE
//      and if it does not exist, it will automatically create a cache with this name
//        Cache productCache = cacheManager.getCache("PRODUCT_CACHE");
//        productCache.put(savedProduct.getId(), savedProduct);

        return new ProductDto(savedProduct.getId(), savedProduct.getName(), savedProduct.getPrice());
    }

    @Cacheable(value = PRODUCT_CACHE, key = "#productId")
    public ProductDto getProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find product with id: " + productId));
        return new ProductDto(product.getId(), product.getName(), product.getPrice());
    }

    @CachePut(value = PRODUCT_CACHE, key = "#result.id()")
    public ProductDto updateProduct(ProductDto productDto) {
        Product product = productRepository.findById(productDto.id())
                .orElseThrow(() -> new IllegalArgumentException("Cannot find product with id: " + productDto.id()));

        product.setName(productDto.name());
        product.setPrice(productDto.price());

        Product updatedProduct = productRepository.save(product);
        return new ProductDto(updatedProduct.getId(), updatedProduct.getName(), updatedProduct.getPrice());
    }

    @CacheEvict(value = PRODUCT_CACHE, key = "#productId")
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}
