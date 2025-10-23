package ru.spb.taranenkoant.concurrency.lab.application.port.in;


import ru.spb.taranenkoant.concurrency.lab.domain.model.Cart;
import ru.spb.taranenkoant.concurrency.lab.domain.model.Order;

/**
 * {@code @author:} TaranenkoAnt
 * {@code @createDate:} 22.10.2025
 */
public interface CartUseCase {

    Cart getCart(Long userId);
    void addItemToCart(Long userId, Long productId, int quantity);
    void removeItemFromCart(Long userId, Long productId);
    Order checkout(Long userId);
}
