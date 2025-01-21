package com.fastfood.api.application.service;

import com.fastfood.api.domain.order.*;
import com.fastfood.api.domain.product.Product;
import com.fastfood.api.domain.product.ProductRepository;
import com.fastfood.api.exceptions.NoItemsFoundException;
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
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void createOrder_shouldReturnCreatedOrder() {
        OrderDTO orderDTO = new OrderDTO(1L, 1L, BigDecimal.valueOf(10), OrderStatus.RECEBIDO, 10L, List.of(new OrderItemDTO(1L, 2L, 1L, 2, LocalDateTime.now())), LocalDateTime.now());
        Order order = new Order(1L, 1L, BigDecimal.valueOf(10), OrderStatus.RECEBIDO, 10L, null, LocalDateTime.now());
        Product product = new Product(1L, "Product 1", BigDecimal.valueOf(10), "Description", null, true, null, LocalDateTime.now(), LocalDateTime.now());
        orderDTO.setItems(List.of(new OrderItemDTO(1L, 2L, 1L, 2, LocalDateTime.now())));

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        Mockito.when(orderMapper.DTOToDomain(orderDTO)).thenReturn(order);
        Mockito.when(orderRepository.save(order)).thenReturn(order);
        Mockito.when(orderMapper.domainToDTO(order)).thenReturn(orderDTO);

        OrderDTO result = orderService.createOrder(orderDTO);

        assertEquals(orderDTO, result);
    }

    @Test
    void createOrder_shouldThrowException_whenProductNotFound() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setItems(List.of(new OrderItemDTO(1L, 2L, 1L, 2, LocalDateTime.now())));

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoItemsFoundException.class, () -> orderService.createOrder(orderDTO));
    }

    @Test
    void updateOrderStatus_shouldReturnUpdatedOrder() {
        Order order = new Order();
        order.setStatus(OrderStatus.RECEBIDO);
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setStatus(OrderStatus.PREPARACAO);

        Mockito.when(orderRepository.findById(1L)).thenReturn(order);
        Mockito.when(orderRepository.save(order)).thenReturn(order);
        Mockito.when(orderMapper.domainToDTO(order)).thenReturn(orderDTO);

        OrderDTO result = orderService.updateOrderStatus(1L);

        assertEquals(orderDTO, result);
    }

    @Test
    void updateOrderStatus_shouldThrowException_whenOrderIsFinalized() {
        Order order = new Order();
        order.setStatus(OrderStatus.FINALIZADO);

        Mockito.when(orderRepository.findById(1L)).thenReturn(order);

        assertThrows(IllegalStateException.class, () -> orderService.updateOrderStatus(1L));
    }

    @Test
    void findOrderById_shouldReturnOrder() {
        Order order = new Order();
        OrderDTO orderDTO = new OrderDTO();

        Mockito.when(orderRepository.findById(1L)).thenReturn(order);
        Mockito.when(orderMapper.domainToDTO(order)).thenReturn(orderDTO);

        OrderDTO result = orderService.findOrderById(1L);

        assertEquals(orderDTO, result);
    }

    @Test
    void calculateTotalValue_shouldReturnCorrectTotal() {
        List<Product> products = List.of(new Product(1L, "Product 1", BigDecimal.valueOf(10), "Description", null, true, null, LocalDateTime.now(), LocalDateTime.now()));
        List<OrderItemDTO> items = List.of(new OrderItemDTO(1L, 2L, 1L, 2, LocalDateTime.now()));

        BigDecimal result = orderService.calculateTotalValue(products, items);

        assertEquals(BigDecimal.valueOf(20), result);
    }

    @Test
    void findOrderByStatus_shouldReturnOrders() {
        List<Order> orders = List.of(new Order());
        List<OrderDTO> orderDTOs = List.of(new OrderDTO());

        Mockito.when(orderRepository.findOrderByStatus(OrderStatus.RECEBIDO)).thenReturn(orders);
        Mockito.when(orderMapper.domainToDTOList(orders)).thenReturn(orderDTOs);

        List<OrderDTO> result = orderService.findOrderByStatus(OrderStatus.RECEBIDO);

        assertEquals(orderDTOs, result);
    }
}