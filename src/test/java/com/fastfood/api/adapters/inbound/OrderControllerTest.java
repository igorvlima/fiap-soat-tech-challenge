package com.fastfood.api.adapters.inbound;

import com.fastfood.api.application.usecases.OrderUseCase;
import com.fastfood.api.domain.order.OrderDTO;
import com.fastfood.api.domain.order.OrderStatus;
import com.fastfood.api.exceptions.OrderNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderUseCase orderUseCase;

    @InjectMocks
    private OrderController orderController;

    @Test
    void createOrder_shouldReturnCreatedOrder() {
        OrderDTO orderDTO = new OrderDTO();
        Mockito.when(orderUseCase.createOrder(orderDTO)).thenReturn(orderDTO);

        OrderDTO result = orderController.createOrder(orderDTO);

        assertEquals(orderDTO, result);
    }

    @Test
    void updateOrderStatus_shouldReturnUpdatedOrder() {
        OrderDTO orderDTO = new OrderDTO();
        Mockito.when(orderUseCase.updateOrderStatus(1L)).thenReturn(orderDTO);

        OrderDTO result = orderController.updateOrderStatus(1L);

        assertEquals(orderDTO, result);
    }

    @Test
    void findOrderById_shouldReturnOrder_whenOrderExists() {
        OrderDTO orderDTO = new OrderDTO();
        Mockito.when(orderUseCase.findOrderById(1L)).thenReturn(orderDTO);

        OrderDTO result = orderController.findOrderById(1L);

        assertEquals(orderDTO, result);
    }

    @Test
    void findOrderById_shouldThrowException_whenOrderDoesNotExist() {
        Mockito.when(orderUseCase.findOrderById(1L)).thenThrow(new OrderNotFoundException("Order not found"));

        assertThrows(OrderNotFoundException.class, () -> orderController.findOrderById(1L));
    }

    @Test
    void findOrderByStatus_shouldReturnOrders() {
        List<OrderDTO> orders = List.of(new OrderDTO());
        Mockito.when(orderUseCase.findOrderByStatus(OrderStatus.RECEBIDO)).thenReturn(orders);

        List<OrderDTO> result = orderController.findOrderByStatus(OrderStatus.RECEBIDO);

        assertEquals(orders, result);
    }
}