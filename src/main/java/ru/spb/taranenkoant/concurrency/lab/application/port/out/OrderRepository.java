package ru.spb.taranenkoant.concurrency.lab.application.port.out;


import ru.spb.taranenkoant.concurrency.lab.domain.model.Order;

import java.util.List;
import java.util.Optional;

/**
 * {@code @author:} TaranenkoAnt
 * {@code @createDate:} 22.10.2025
 */
public interface OrderRepository {
    Order save(Order order);
    Optional<Order> findById(Long orderId);
    List<Order> findByUserId(Long userId);
}
