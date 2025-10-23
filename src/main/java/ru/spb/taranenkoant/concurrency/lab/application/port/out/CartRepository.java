package ru.spb.taranenkoant.concurrency.lab.application.port.out;


import ru.spb.taranenkoant.concurrency.lab.domain.model.Cart;

import java.util.Optional;

/**
 * {@code @author:} TaranenkoAnt
 * {@code @createDate:} 22.10.2025
 */
public interface CartRepository {
    Optional<Cart> findByUserId(Long userId);
    Cart save (Cart cart);
    void delete(Cart cart);
}
