package com.alyona.Sber2;

import javax.sql.DataSource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceTestConfiguration {

    @Bean
    public CommandLineRunner testDataSource(DataSource dataSource) {
        return args -> {
            try (var connection = dataSource.getConnection()) {
                System.out.println("Successfully connected to the database!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}
