package ru.spb.taranenkoant.concurrency.lab.infrastructure.persistence.adapter;


import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.spb.taranenkoant.concurrency.lab.application.port.out.ProductRepository;
import ru.spb.taranenkoant.concurrency.lab.domain.model.Product;
import ru.spb.taranenkoant.concurrency.lab.infrastructure.persistence.ProductEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * {@code @author:} TaranenkoAnt
 * {@code @createDate:} 24.10.2025
 */

@Repository
@Primary
public class ProductRepositoryAdapter implements ProductRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Product> findById(Long productId) {
        ProductEntity entity = entityManager.find(ProductEntity.class, productId);
        return Optional.ofNullable(entity).map(this::toDomain);
    }

    @Override
    public Product save(Product product) {
        ProductEntity entity = toEntity(product);
        if (product.getId() == null) {
            entityManager.persist(entity);
        } else {
            entity = entityManager.merge(entity);
        }
        return toDomain(entity);
    }

    private Product toDomain(ProductEntity entity) {
        Product product = new Product(entity.getName(), entity.getPrice(), new AtomicInteger(entity.getStockQuantity()));
        // reflection для установки ID
        return product;
    }

    private ProductEntity toEntity(Product product) {
        ProductEntity entity = new ProductEntity();
        entity.setId(product.getId());
        entity.setName(product.getName());
        entity.setPrice(product.getPrice());
        entity.setStockQuantity(product.getAvailableStock());
        return entity;
    }

    @Override
    public List<Product> findAll() {
        return List.of();
    }
}
