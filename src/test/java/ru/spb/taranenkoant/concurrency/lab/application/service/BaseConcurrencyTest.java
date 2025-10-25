package ru.spb.taranenkoant.concurrency.lab.application.service;

import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import ru.spb.taranenkoant.concurrency.lab.application.port.out.CartRepository;
import ru.spb.taranenkoant.concurrency.lab.application.port.out.ProductRepository;
import ru.spb.taranenkoant.concurrency.lab.domain.model.Cart;
import ru.spb.taranenkoant.concurrency.lab.domain.model.Product;
import ru.spb.taranenkoant.concurrency.lab.domain.model.User;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * {@code @author:} TaranenkoAnt
 * {@code @createDate:} 24.10.2025
 */
@DataJpaTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseConcurrencyTest {

    @Autowired
    protected TestEntityManager entityManager;

    @Autowired
    protected ProductRepository productRepository;

    @Autowired
    protected CartRepository cartRepository;

    protected Product createProduct(String name, BigDecimal price, int stock) {
        Product product = new Product(name, price, new AtomicInteger(stock));
        return productRepository.save(product);
    }

    protected Cart createCart(Long userId) {
        User user = new User(userId.toString(), new BigDecimal("1000.00"));
        Cart cart = new Cart(user);
        return cartRepository.save(cart);
    }
}
