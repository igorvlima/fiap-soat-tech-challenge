package com.fastfood.api.adapters.outbound.repositories.order;

import com.fastfood.api.adapters.outbound.entities.order.JpaOrderEntity;
import com.fastfood.api.adapters.outbound.entities.order.JpaOrderItemEntity;
import com.fastfood.api.domain.order.Order;
import com.fastfood.api.domain.order.OrderItem;
import com.fastfood.api.domain.order.OrderStatus;
import com.fastfood.api.exceptions.OrderNotFoundException;
import com.fastfood.api.utils.mappers.OrderMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderRepositoryImplTest {

    @Mock
    private JpaOrderRepository jpaOrderRepository;

    @Mock
    private JpaOrderItemRepository jpaOrderItemRepository;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderRepositoryImpl orderRepository;

    @Test
    void save_shouldReturnSavedOrder() {
        Order order = new Order(1L, 123L, BigDecimal.TEN, OrderStatus.RECEBIDO, 60L, List.of(new OrderItem(1L, 1L, 1L, 1, LocalDateTime.now())), LocalDateTime.now());
        JpaOrderEntity orderEntity = new JpaOrderEntity(1L, 123L, BigDecimal.TEN, OrderStatus.RECEBIDO, 60L, LocalDateTime.now());
        List<JpaOrderItemEntity> orderItemEntities = List.of(new JpaOrderItemEntity());

        Mockito.doReturn(orderEntity).when(jpaOrderRepository).save(Mockito.any());
        Mockito.doReturn(orderItemEntities).when(jpaOrderItemRepository).saveAll(Mockito.anyList());
        Mockito.doReturn(order).when(orderMapper).jpaToDomain(orderEntity, orderItemEntities);

        Order result = orderRepository.save(order);

        assertEquals(order, result);
    }

    @Test
    void findById_shouldReturnOrder_whenOrderExists() {
        Order order = new Order();
        JpaOrderEntity orderEntity = new JpaOrderEntity();
        List<JpaOrderItemEntity> orderItemEntities = List.of(new JpaOrderItemEntity());

        Mockito.when(jpaOrderRepository.findById(1L)).thenReturn(Optional.of(orderEntity));
        Mockito.when(jpaOrderItemRepository.findAllByOrderId(1L)).thenReturn(orderItemEntities);
        Mockito.when(orderMapper.jpaToDomain(orderEntity, orderItemEntities)).thenReturn(order);

        Order result = orderRepository.findById(1L);

        assertEquals(order, result);
    }

    @Test
    void findById_shouldThrowException_whenOrderDoesNotExist() {
        Mockito.when(jpaOrderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderRepository.findById(1L));
    }

    @Test
    void findOrderByStatus_shouldReturnOrders() {
        List<JpaOrderEntity> orderEntities = List.of(new JpaOrderEntity());
        List<JpaOrderItemEntity> orderItemEntities = List.of(new JpaOrderItemEntity());
        List<Order> orders = List.of(new Order());

        Mockito.when(jpaOrderRepository.findByStatus(OrderStatus.RECEBIDO)).thenReturn(orderEntities);
        Mockito.when(jpaOrderItemRepository.findAllByOrderId(Mockito.any())).thenReturn(orderItemEntities);
        Mockito.when(orderMapper.jpaToDomainList(orderEntities, orderItemEntities)).thenReturn(orders);

        List<Order> result = orderRepository.findOrderByStatus(OrderStatus.RECEBIDO);

        assertEquals(orders, result);
    }
}