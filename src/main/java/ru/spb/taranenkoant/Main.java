package ru.spb.taranenkoant;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import ru.spb.taranenkoant.concurrency.lab.application.port.out.ProductRepository;
import ru.spb.taranenkoant.concurrency.lab.domain.model.Product;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);

    }

    @Bean
    @Profile("dev")
    public CommandLineRunner demoData(ProductRepository productRepository) {
        return args -> {
            productRepository.save(new Product("Laptop", new BigDecimal("999.99"), new AtomicInteger(10)));
            productRepository.save(new Product("Mouse", new BigDecimal("29.99"), new AtomicInteger(50)));
            productRepository.save(new Product("Keyboard", new BigDecimal("79.99"), new AtomicInteger(30)));
        };
    }
}