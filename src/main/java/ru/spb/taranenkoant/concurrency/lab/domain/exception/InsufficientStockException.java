package ru.spb.taranenkoant.concurrency.lab.domain.exception;


/**
 * {@code @author:} TaranenkoAnt
 * {@code @createDate:} 21.10.2025
 */
public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String message) {
        super(message);
    }
}
