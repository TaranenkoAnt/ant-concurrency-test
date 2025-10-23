package ru.spb.taranenkoant.concurrency.lab.application.port.out;


import java.util.Optional;

/**
 * {@code @author:} TaranenkoAnt
 * {@code @createDate:} 22.10.2025
 */
public interface orderRepository {
    Order save(Order order);
    Optional<Order> findById(Long orderId);
}
