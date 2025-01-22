package com.fastfood.api.application.service;

import com.fastfood.api.application.usecases.OrderUseCase;
import com.fastfood.api.domain.order.*;
import com.fastfood.api.domain.product.Product;
import com.fastfood.api.domain.product.ProductRepository;
import com.fastfood.api.exceptions.NoItemsFoundException;
import com.fastfood.api.utils.mappers.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderUseCase {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {

        List<Product> products = orderDTO.getItems().stream()
                .map(OrderItemDTO::getProductId)
                .map(id -> productRepository.findById(id)
                        .orElseThrow(() -> new NoItemsFoundException(
                                "Item com ID " + id + " não encontrado, não foi possível fechar o pedido.")))
                .collect(Collectors.toList());

        orderDTO.setTotal(calculateTotalValue(products, orderDTO.getItems()));
        orderDTO.setCreatedAt(LocalDateTime.now());
        orderDTO.setStatus(OrderStatus.RECEBIDO);

        var order =  orderRepository.save(orderMapper.DTOToDomain(orderDTO));
        return orderMapper.domainToDTO(order);
    }

    @Override
    public OrderDTO updateOrderStatus(Long id) {

        var order = orderRepository.findById(id);

        if (order.getStatus() == OrderStatus.FINALIZADO) {
            throw new IllegalStateException("O pedido já está finalizado e não pode ser atualizado novamente.");
        }

        OrderStatus nextStatus = getNextOrderStatus(order.getStatus());

        order.setStatus(nextStatus);

        return orderMapper.domainToDTO(orderRepository.save(order));
    }
    
        @Override
    public OrderStatus getNextOrderStatus(OrderStatus status) {
        OrderStatus[] statuses = OrderStatus.values();
        int currentIndex = status.ordinal();

        if (currentIndex + 1 >= statuses.length) {
            throw new IllegalStateException("Não é possível avançar o status do pedido.");
        }

        return statuses[currentIndex + 1];
    }

    @Override
    public OrderDTO findOrderById(Long orderId) {
        return orderMapper.domainToDTO(orderRepository.findById(orderId));
    }

    @Override
    public BigDecimal calculateTotalValue(List<Product> productList, List<OrderItemDTO> orderItemDTOS) {
        Map<Long, BigDecimal> productPriceMap = productList.stream()
                .collect(Collectors.toMap(Product::getId, Product::getPrice));

        return orderItemDTOS.stream()
                .map(item -> {
                    BigDecimal price = productPriceMap.getOrDefault(item.getProductId(), BigDecimal.ZERO);
                    return price.multiply(BigDecimal.valueOf(item.getQuantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public List<OrderDTO> findOrderByStatus(OrderStatus status) {
        var orders =  orderRepository.findOrderByStatus(status);
        return orderMapper.domainToDTOList(orders);
    }
}
