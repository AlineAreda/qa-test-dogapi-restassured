package com.ceodog.api.config;

import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    protected static Configuracoes config;

    @BeforeAll
    static void setUp() {

        config = ConfigFactory.create(Configuracoes.class);

        RestAssured.baseURI = config.baseUrl();
        RestAssured.port = config.port();
        RestAssured.basePath = config.basePath();
    }
}