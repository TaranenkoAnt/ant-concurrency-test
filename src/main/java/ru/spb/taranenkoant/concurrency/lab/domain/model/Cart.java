package ru.spb.taranenkoant.concurrency.lab.domain.model;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@code @author:} TaranenkoAnt
 * {@code @createDate:} 21.10.2025
 */
public class Cart {

    private Long id;
    private User user;
    private Map<Long, CartItem> items = new ConcurrentHashMap<>();

    public void addItem(Product product, int quantity) {
        items.compute(product.getId(), (productId, existingItem) -> {
            if (existingItem == null) {
                return new CartItem(product, quantity);
            }
            return existingItem.withIncreasedQuantity(quantity);
        });
    }

    public void removeItem(Long productId) {
        items.remove(productId);
    }

    private BigDecimal calculateTotal() {
        return items.values().stream()
                .map(CartItem::calculateSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<CartItem> getItems() {
        return new ArrayList<>(items.values());
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }
}
