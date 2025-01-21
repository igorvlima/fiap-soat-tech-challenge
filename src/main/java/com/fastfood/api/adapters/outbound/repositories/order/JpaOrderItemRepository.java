package com.fastfood.api.adapters.outbound.repositories.order;

import com.fastfood.api.adapters.outbound.entities.order.JpaOrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaOrderItemRepository extends JpaRepository<JpaOrderItemEntity, Long> {
    List<JpaOrderItemEntity> findAllByOrderId(Long orderId);
}
