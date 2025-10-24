package ru.spb.taranenkoant.concurrency.lab.infrastructure.web;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.spb.taranenkoant.concurrency.lab.application.port.in.CartUseCase;
import ru.spb.taranenkoant.concurrency.lab.domain.model.Order;

/**
 * {@code @author:} TaranenkoAnt
 * {@code @createDate:} 24.10.2025
 */
@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartUseCase cartUseCase;

    public CartController(CartUseCase cartUseCase) {
        this.cartUseCase = cartUseCase;
    }

    @PostMapping("/{userId}/items")
    public ResponseEntity<Void> addItem(
            @PathVariable Long userId,
            @RequestBody AddItemRequest request) {

        cartUseCase.addItemToCart(userId, request.productId(), request.quantity());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{userId}/checkout")
    public ResponseEntity<OrderResponse> checkout(@PathVariable Long userId) {
        Order order = cartUseCase.checkout(userId);
        return ResponseEntity.ok(toResponse(order));
    }

    private OrderResponse toResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getUserId(),
                order.getStatus(),
                order.getTotalAmount(),
                order.getItems(),
                order.getCreatedAt()
        );
    }
}
