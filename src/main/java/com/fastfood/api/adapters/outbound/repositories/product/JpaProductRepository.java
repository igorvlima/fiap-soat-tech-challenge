package com.fastfood.api.adapters.outbound.repositories.product;

import com.fastfood.api.adapters.outbound.entities.product.JpaProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface JpaProductRepository extends JpaRepository<JpaProductEntity, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE JpaProductEntity p SET p.active = false, p.updatedAt = CURRENT_TIMESTAMP WHERE p.id = :id")
    void disableProductById(@Param("id") Long id);

    @Query("SELECT p FROM JpaProductEntity p WHERE p.category = :category AND p.active = true")
    List<JpaProductEntity> findProductByCategory(@Param("category") String category);
}
