package ru.spb.taranenkoant.concurrency.lab.application.service;


import org.springframework.stereotype.Service;
import ru.spb.taranenkoant.concurrency.lab.application.port.in.CartUseCase;
import ru.spb.taranenkoant.concurrency.lab.application.port.in.ProductUseCase;
import ru.spb.taranenkoant.concurrency.lab.application.port.out.CartRepository;
import ru.spb.taranenkoant.concurrency.lab.application.port.out.ProductRepository;
import ru.spb.taranenkoant.concurrency.lab.domain.exception.InsufficientStockException;
import ru.spb.taranenkoant.concurrency.lab.domain.model.*;

import javax.transaction.Transactional;
import java.math.BigDecimal;

/**
 * {@code @author:} TaranenkoAnt
 * {@code @createDate:} 23.10.2025
 */
@Service
@Transactional
public class CartService implements CartUseCase {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final ProductUseCase productUseCase;

    public CartService(CartRepository cartRepository,
                       ProductRepository productRepository,
                       OrderRepository orderRepository,
                       ProductUseCase productUseCase) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.productUseCase = productUseCase;
    }

    @Override
    public Cart getCart(Long userId) {
        return null;
    }

    // PROBLEM 1: Race condition в добавлении товара
    @Override
    public void addItemToCart(Long userId, Long productId, int quantity) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> createNewCart(userId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        cart.addItem(product, quantity);
        cartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(Long userId, Long productId) {

    }

    // PROBLEM 2: Lost update при параллельном checkout
    @Override
    public Order checkout(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        for (CartItem item : cart.getItems()) {
            if (!productUseCase.reverseProduct(item.product().getId(), item.quantity())) {
                throw new InsufficientStockException("Product not available");
            }
        }

        Order order = new Order(userId, cart.getItems());
        Order savedOrder = orderRepository.save(order);

        cart.getItems().clear();
        cartRepository.save(cart);
        return savedOrder;
    }

    private Cart createNewCart(Long userId) {
        User user = new User(userId.toString(), new BigDecimal("1000.00"));
        return new Cart(user);
    }
}
