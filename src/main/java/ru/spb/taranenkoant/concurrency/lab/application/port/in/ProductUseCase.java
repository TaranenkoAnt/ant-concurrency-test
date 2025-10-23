package ru.spb.taranenkoant.concurrency.lab.application.port.in;


import ru.spb.taranenkoant.concurrency.lab.domain.model.Product;

import java.util.List;

/**
 * {@code @author:} TaranenkoAnt
 * {@code @createDate:} 22.10.2025
 */
public interface ProductUseCase {

    Product getProduct(Long productId);
    List<Product> getAllProducts();
    boolean reverseProduct(Long productId, int quantity);
    void releaseProduct(Long productId, int quantity);
}
