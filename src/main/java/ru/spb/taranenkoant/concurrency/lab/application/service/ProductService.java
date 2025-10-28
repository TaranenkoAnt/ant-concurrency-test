package ru.spb.taranenkoant.concurrency.lab.application.service;


import org.springframework.stereotype.Service;
import ru.spb.taranenkoant.concurrency.lab.application.port.in.ProductUseCase;
import ru.spb.taranenkoant.concurrency.lab.application.port.out.ProductRepository;
import ru.spb.taranenkoant.concurrency.lab.domain.model.Product;

import javax.transaction.Transactional;
import java.util.List;

/**
 * {@code @author:} TaranenkoAnt
 * {@code @createDate:} 23.10.2025
 */
@Service
@Transactional
public class ProductService implements ProductUseCase {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product getProduct(Long productId) {
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }

    @Override
    public boolean reverseProduct(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not founded"));

        if (product.getAvailableStock() >= quantity) {
            return product.reserve(quantity);
        }
        return false;
    }

    @Override
    public void releaseProduct(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not founded"));
        product.release(quantity);
        productRepository.save(product);
    }
}
