package com.fastfood.api.application.usecases;


import com.fastfood.api.domain.order.OrderDTO;
import com.fastfood.api.domain.order.OrderItemDTO;
import com.fastfood.api.domain.order.OrderStatus;
import com.fastfood.api.domain.product.Product;

import java.math.BigDecimal;
import java.util.List;

public interface OrderUseCase {

    OrderDTO createOrder(OrderDTO customerDTO);

    OrderDTO updateOrderStatus(Long id);

    OrderDTO findOrderById(Long id);

    BigDecimal calculateTotalValue(List<Product> productList, List<OrderItemDTO> orderItemDTOS);

    List<OrderDTO> findOrderByStatus(OrderStatus status);

    OrderStatus getNextOrderStatus(OrderStatus status);
}
