package com.ceodog.api.config;

import org.aeonbits.owner.Config;

@Configuracoes.Sources({
        "classpath:properties/${ENV}.properties",
        "classpath:properties/hml.properties"})

public interface Configuracoes extends Config {

    @org.aeonbits.owner.Config.Key("baseUrl")
    String baseUrl();

    @org.aeonbits.owner.Config.Key("port")
    Integer port();

    @org.aeonbits.owner.Config.Key("basePath")
    String basePath();

    @Config.Key("MAX_TIMEOUT")
    Integer maxTimeout();

}
