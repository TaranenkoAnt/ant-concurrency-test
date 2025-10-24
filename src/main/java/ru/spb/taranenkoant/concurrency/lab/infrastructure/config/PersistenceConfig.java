package ru.spb.taranenkoant.concurrency.lab.infrastructure.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.spb.taranenkoant.concurrency.lab.application.port.out.ProductRepository;
import ru.spb.taranenkoant.concurrency.lab.infrastructure.persistence.adapter.ProductRepositoryAdapter;

/**
 * {@code @author:} TaranenkoAnt
 * {@code @createDate:} 24.10.2025
 */

@Configuration
@EnableJpaRepositories(basePackages = "ru.spb.taranenkoant.concurrency.lab.infrastructure.persistence")
public class PersistenceConfig {

    @Bean
    @Primary
    public ProductRepository productRepository(ProductRepositoryAdapter adapter) {
        return adapter;
    }
}
