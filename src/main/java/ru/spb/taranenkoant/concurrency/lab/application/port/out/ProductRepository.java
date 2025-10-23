package ru.spb.taranenkoant.concurrency.lab.application.port.out;


import ru.spb.taranenkoant.concurrency.lab.domain.model.Product;

import java.util.List;
import java.util.Optional;

/**
 * {@code @author:} TaranenkoAnt
 * {@code @createDate:} 22.10.2025
 */
public interface ProductRepository {
    Optional<Product> findById(Long productId);
    Product save (Product product);
    List<Product> findAll();
}
