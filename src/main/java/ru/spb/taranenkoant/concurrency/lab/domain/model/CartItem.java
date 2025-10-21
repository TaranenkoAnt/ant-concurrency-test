package ru.spb.taranenkoant.concurrency.lab.domain.model;


import java.math.BigDecimal;

/**
 * {@code @author:} TaranenkoAnt
 * {@code @createDate:} 21.10.2025
 */
public record CartItem(Product product, int quantity) {

    public CartItem withIncreasedQuantity(int additionalQuantity) {
        return new CartItem(product, quantity + additionalQuantity);
    }

    public BigDecimal calculateSubtotal() {
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}
