package com.fastfood.api.utils.mappers;

import com.fastfood.api.adapters.outbound.entities.order.JpaOrderEntity;
import com.fastfood.api.adapters.outbound.entities.order.JpaOrderItemEntity;
import com.fastfood.api.domain.order.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public Order DTOToDomain(OrderDTO dto){
        Order order = new Order();
        order.setCustomerId(dto.getCustomerId());
        order.setTotal(dto.getTotal());
        order.setStatus(dto.getStatus());
        order.setWaitingTimeInMinutes(dto.getWaitingTimeInMinutes());
        order.setItems(dto.getItems().stream()
                .map(itemDTO -> new OrderItem(itemDTO.getId(), itemDTO.getOrderId(), itemDTO.getProductId(), itemDTO.getQuantity(), itemDTO.getCreatedAt()))
                .collect(Collectors.toList()));
        order.setCreatedAt(dto.getCreatedAt());
        return order;
    }

    public OrderDTO domainToDTO(Order order){
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setCustomerId(order.getCustomerId());
        dto.setTotal(order.getTotal());
        dto.setStatus(order.getStatus());
        dto.setWaitingTimeInMinutes(order.getWaitingTimeInMinutes());
        dto.setItems(order.getItems().stream()
                .map(item -> new OrderItemDTO(item.getId(), item.getOrderId(), item.getProductId(), item.getQuantity(), item.getCreatedAt()))
                .collect(Collectors.toList()));
        dto.setCreatedAt(order.getCreatedAt());
        return dto;
    }

    public JpaOrderEntity domainToJpa(Order order) {
        JpaOrderEntity jpaOrderEntity = new JpaOrderEntity();
        jpaOrderEntity.setId(order.getId());
        jpaOrderEntity.setCustomerId(order.getCustomerId());
        jpaOrderEntity.setTotal(order.getTotal());
        jpaOrderEntity.setStatus(order.getStatus());
        jpaOrderEntity.setWaitingTimeInMinutes(order.getWaitingTimeInMinutes());
        jpaOrderEntity.setCreatedAt(order.getCreatedAt());
        return jpaOrderEntity;
    }

    public Order jpaToDomain(JpaOrderEntity jpaOrderEntity, List<JpaOrderItemEntity> jpaOrderItemEntities) {
        Order order = new Order();
        order.setId(jpaOrderEntity.getId());
        order.setCustomerId(jpaOrderEntity.getCustomerId());
        order.setTotal(jpaOrderEntity.getTotal());
        order.setStatus(jpaOrderEntity.getStatus());
        order.setWaitingTimeInMinutes(jpaOrderEntity.getWaitingTimeInMinutes());
        order.setCreatedAt(jpaOrderEntity.getCreatedAt());

        List<JpaOrderItemEntity> filteredItems = jpaOrderItemEntities.stream()
                .filter(item -> item.getOrderId().equals(jpaOrderEntity.getId()))
                .toList();

        order.setItems(filteredItems.stream()
                .map(item -> new OrderItem(item.getId(), item.getOrderId(), item.getProductId(), item.getQuantity(), item.getCreatedAt()))
                .toList());

        return order;
    }


    public List<Order> jpaToDomainList(List<JpaOrderEntity> jpaOrderEntities, List<JpaOrderItemEntity> jpaOrderItemEntities) {
        return jpaOrderEntities.stream()
                .map(jpaOrderEntity -> jpaToDomain(jpaOrderEntity, jpaOrderItemEntities))
                .toList();
    }

    public List<OrderDTO> domainToDTOList(List<Order> orders) {
        return orders.stream()
                .map(this::domainToDTO)
                .collect(Collectors.toList());
    }
}
