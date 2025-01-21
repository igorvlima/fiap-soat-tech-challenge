package com.fastfood.api.adapters.outbound.repositories.order;

import com.fastfood.api.adapters.outbound.entities.order.JpaOrderEntity;
import com.fastfood.api.domain.order.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaOrderRepository extends JpaRepository<JpaOrderEntity, Long> {

    @Query("SELECT o FROM JpaOrderEntity o WHERE o.status = :status")
    List<JpaOrderEntity> findByStatus(@Param("status") OrderStatus status);
}
