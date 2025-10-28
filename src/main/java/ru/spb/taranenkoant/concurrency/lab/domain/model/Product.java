package ru.spb.taranenkoant.concurrency.lab.domain.model;


import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * {@code @author:} TaranenkoAnt
 * {@code @createDate:} 21.10.2025
 */
public class Product {

    private Long id;
    private String name;
    private BigDecimal price;
    private AtomicInteger stockQuantity;

    public Product(String name, BigDecimal price, AtomicInteger stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public boolean reserve(int quantity) {
        while (true) {
            int current = stockQuantity.get();
            if (current < quantity) {
                return false;
            }

            if (stockQuantity.compareAndSet(current, current - quantity)) {
                return true;
            }
        }
    }

    public void increaseStock(int quantity) {
        stockQuantity.addAndGet(quantity);
    }

    public void release(int quantity) {
        stockQuantity.addAndGet(quantity);
    }

    public int getAvailableStock() {
        return stockQuantity.get();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public AtomicInteger getStockQuantity() {
        return stockQuantity;
    }
}
