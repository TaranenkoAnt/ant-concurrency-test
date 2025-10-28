package ru.spb.taranenkoant.concurrency.lab.infrastructure.persistence.adapter;


import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.spb.taranenkoant.concurrency.lab.application.port.out.OrderRepository;
import ru.spb.taranenkoant.concurrency.lab.domain.model.Order;
import ru.spb.taranenkoant.concurrency.lab.domain.model.OrderItem;
import ru.spb.taranenkoant.concurrency.lab.infrastructure.persistence.OrderEntity;
import ru.spb.taranenkoant.concurrency.lab.infrastructure.persistence.OrderItemEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * {@code @author:} TaranenkoAnt
 * {@code @createDate:} 28.10.2025
 */

@Repository
@Primary
public class OrderRepositoryAdapter implements OrderRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Order save(Order order) {
        OrderEntity entity = toEntity(order);
        if (order.getId() == null) {
            entityManager.persist(entity);
        } else {
            entity = entityManager.merge(entity);
        }
        entityManager.flush();
        return toDomain(entity);
    }

    private Order toDomain(OrderEntity entity) {
        List<OrderItem> items = entity.getItems().stream()
                .map(itemEntity -> new OrderItem(
                        itemEntity.getProductId(),
                        itemEntity.getProductName(),
                        itemEntity.getPrice(),
                        itemEntity.getQuantity()
                ))
                .collect(Collectors.toList());

        Order order = new Order();
        order.setUserId(entity.getUserId());
        order.setItems(items);
        order.setId(entity.getId());
        // Устанавливаем статус через reflection или метод
        return order;
    }

    private OrderEntity toEntity(Order order) {
        OrderEntity entity = new OrderEntity();
        entity.setId(order.getId());
        entity.setUserId(order.getUserId());
        entity.setStatus(order.getStatus());
        entity.setTotalAmount(order.getTotalAmount());
        entity.setCreatedAt(order.getCreatedAt());

        List<OrderItemEntity> itemEntities = order.getItems().stream()
                .map(item -> {
                    OrderItemEntity itemEntity = new OrderItemEntity();
                    itemEntity.setProductId(item.getProductId());
                    itemEntity.setProductName(item.getProductName());
                    itemEntity.setPrice(item.getPrice());
                    itemEntity.setQuantity(item.getQuantity());
                    itemEntity.setSubtotal(item.getSubtotal());
                    return itemEntity;
                })
                .collect(Collectors.toList());

        entity.setItems(itemEntities);
        return entity;
    }

    @Override
    public Optional<Order> findById(Long orderId) {
        OrderEntity entity = entityManager.find(OrderEntity.class, orderId);
        return Optional.ofNullable(entity).map(this::toDomain);
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        String jpql = "SELECT o FROM OrderEntity o WHERE o.userId = :userId ORDER BY o.createdAt DESC";
        return entityManager.createQuery(jpql, OrderEntity.class)
                .setParameter("userId", userId)
                .getResultList()
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }
}
