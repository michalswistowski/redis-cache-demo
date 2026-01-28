package com.michalswistowski.redis_cache_demo.repository;

import com.michalswistowski.redis_cache_demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
