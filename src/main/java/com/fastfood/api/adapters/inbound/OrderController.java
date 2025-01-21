package com.fastfood.api.adapters.inbound;

import com.fastfood.api.application.usecases.OrderUseCase;
import com.fastfood.api.domain.order.OrderDTO;
import com.fastfood.api.domain.order.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderUseCase orderUseCase;

    @PostMapping("/order")
    public OrderDTO createOrder(@RequestBody OrderDTO orderDTO) {
        return orderUseCase.createOrder(orderDTO);
    }

    @PatchMapping("/order-status/{id}")
    public OrderDTO updateOrderStatus(@PathVariable Long id) {
        return orderUseCase.updateOrderStatus(id);
    }

    @GetMapping("/order/{id}")
    public OrderDTO findOrderById(@PathVariable Long id) {
        return orderUseCase.findOrderById(id);
    }

    @GetMapping("/order-status/{status}")
    public List<OrderDTO> findOrderByStatus(@PathVariable OrderStatus status) {
        return orderUseCase.findOrderByStatus(status);
    }
}
