package ru.spb.taranenkoant.concurrency.lab.application.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.spb.taranenkoant.concurrency.lab.application.port.in.CartUseCase;
import ru.spb.taranenkoant.concurrency.lab.domain.model.Cart;
import ru.spb.taranenkoant.concurrency.lab.domain.model.Product;

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class CartServiceConcurrencyTest extends BaseConcurrencyTest {

    @Autowired
    private CartUseCase cartService;

    @Test
    void testAddItemRaceCondition() throws InterruptedException {
        Long userId = 1L;
        Product product = createProduct("Test Product", new BigDecimal("10.00"), 1000);
        createCart(userId);

        int threadCount = 10;
        int iterations = 100;
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                try {
                    startLatch.await();
                    for (int j = 0; j < iterations; j++) {
                        cartService.addItemToCart(userId, product.getId(), 1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    endLatch.countDown();
                }
            }).start();
        }

        startLatch.countDown();
        boolean completed = endLatch.await(10, TimeUnit.SECONDS);

        assertTrue(completed, "Test didn't complete in time");
        Cart cart = cartRepository.findByUserId(userId).orElseThrow();

        //ДОЛЖНО упасть из-за race condition!
        assertEquals(threadCount * iterations,
                cart.getItems().get(0).quantity(),
                "Race condition detected - quantities don't match");
    }

}