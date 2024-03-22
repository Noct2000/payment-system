package com.example.data.api.config;

import org.testcontainers.containers.PostgreSQLContainer;

public class DataApiPostgresContainer extends PostgreSQLContainer<DataApiPostgresContainer> {

    public DataApiPostgresContainer() {
        super("postgres:15-alpine");
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("spring.datasource.url", this.getJdbcUrl());
        System.setProperty("spring.datasource.username", this.getUsername());
        System.setProperty("spring.datasource.password", this.getPassword());
    }
}
