package com.fastfood.api.adapters.outbound.repositories.order;

import com.fastfood.api.adapters.outbound.entities.order.JpaOrderEntity;
import com.fastfood.api.adapters.outbound.entities.order.JpaOrderItemEntity;
import com.fastfood.api.domain.order.Order;
import com.fastfood.api.domain.order.OrderRepository;
import com.fastfood.api.domain.order.OrderStatus;
import com.fastfood.api.exceptions.OrderNotFoundException;
import com.fastfood.api.utils.mappers.OrderMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private final JpaOrderRepository jpaOrderRepository;
    private final JpaOrderItemRepository jpaOrderItemRepository;
    private final OrderMapper orderMapper;

    public OrderRepositoryImpl(JpaOrderRepository jpaOrderRepository, JpaOrderItemRepository jpaOrderItemRepository, OrderMapper orderMapper) {
        this.jpaOrderRepository = jpaOrderRepository;
        this.jpaOrderItemRepository = jpaOrderItemRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public Order save(Order order) {
        var orderEntity = jpaOrderRepository.save(orderMapper.domainToJpa(order));
        var orderItems = order.getItems().stream()
                .map(orderItem -> new JpaOrderItemEntity(orderItem.getId(), orderEntity.getId(), orderItem.getProductId(), orderItem.getQuantity(), LocalDateTime.now()))
                .toList();

        var orderItemEntity = jpaOrderItemRepository.saveAll(orderItems);

        return orderMapper.jpaToDomain(orderEntity, orderItemEntity);
    }

    @Override
    public Order findById(Long orderId) {
        return jpaOrderRepository.findById(orderId)
                .map(orderEntity -> {
                    var orderItemEntities = jpaOrderItemRepository.findAllByOrderId(orderId);
                    return orderMapper.jpaToDomain(orderEntity, orderItemEntities);
                })
                .orElseThrow(() -> new OrderNotFoundException("Pedido com ID " + orderId + " não encontrado."));
    }

    @Override
    public List<Order> findOrderByStatus(OrderStatus orderStatus) {
        List<JpaOrderEntity> orders = jpaOrderRepository.findByStatus(orderStatus);

        List<JpaOrderItemEntity> orderItems = orders.stream()
                .flatMap(order -> jpaOrderItemRepository.findAllByOrderId(order.getId()).stream())
                .toList();

        return orderMapper.jpaToDomainList(orders, orderItems);
    }
}
