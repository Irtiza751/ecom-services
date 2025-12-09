package com.aspire.product.repositories;

import com.aspire.product.models.Product;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;

@NullMarked
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsBySlug(String slug);
}
