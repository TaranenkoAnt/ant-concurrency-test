package ru.spb.taranenkoant.concurrency.lab.domain.exception;


/**
 * {@code @author:} TaranenkoAnt
 * {@code @createDate:} 21.10.2025
 */
public class ConcurrentModificationException extends RuntimeException {
    public ConcurrentModificationException(String message) {
        super(message);
    }
}
