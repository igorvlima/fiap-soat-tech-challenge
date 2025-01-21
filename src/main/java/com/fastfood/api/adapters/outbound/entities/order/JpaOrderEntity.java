package com.fastfood.api.adapters.outbound.entities.order;

import com.fastfood.api.domain.order.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Table(name = "\"order\"")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "order_seq_gen", sequenceName = "order_id_seq", allocationSize = 1)
public class JpaOrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq_gen")
    private Long id;
    private Long customerId;
    private BigDecimal total;
    private OrderStatus status;
    private Long waitingTimeInMinutes;
    private LocalDateTime createdAt;
}
