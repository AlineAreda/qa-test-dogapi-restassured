package com.ceodog.api.tests;

import com.ceodog.api.config.BaseTest;
import com.ceodog.api.specs.ResponseSpecs;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@Feature("Random Image")
@DisplayName("Testes do endpoint /breeds/image/random")
class RandomImageTest extends BaseTest {

    private static final String ENDPOINT = "/breeds/image/random";

    @Test
    @Description("""
            Verifica se a API retorna com sucesso uma imagem aleatória
            de uma raça disponível.
            """)
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Deve retornar uma imagem aleatória com sucesso")
    void deveRetornarImagemAleatoriaComSucesso() {

        given()
                .when()
                .get(ENDPOINT)
                .then()
                .spec(ResponseSpecs.successJson())
                .body("status", equalTo("success"))
                .body("message", notNullValue());
    }

    @Test
    @Description("""
            Verifica se a API retorna a imagem no formato esperado,
            contendo uma URL não vazia representada como String.
            """)
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Deve retornar a URL da imagem no formato esperado")
    void deveRetornarUrlDaImagemNoFormatoEsperado() {

        String imageUrl =
                given()
                        .when()
                        .get(ENDPOINT)
                        .then()
                        .spec(ResponseSpecs.successJson())
                        .body("status", equalTo("success"))
                        .extract()
                        .path("message");

        assertInstanceOf(
                String.class,
                imageUrl,
                "A URL da imagem deve ser representada como String."
        );

        assertFalse(
                imageUrl.isBlank(),
                "A URL da imagem não deve estar vazia."
        );

        assertTrue(
                imageUrl.startsWith("https://"),
                "A URL da imagem deve utilizar HTTPS."
        );
    }

    @Test
    @Description("""
        Verifica se chamadas consecutivas ao endpoint podem retornar
        diferentes imagens, considerando o comportamento aleatório esperado.
        """)
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Deve retornar diferentes imagens em múltiplas chamadas")
    void deveRetornarDiferentesImagensEmMultiplasChamadas() {

        List<String> images = new ArrayList<>();

        for (int i = 0; i < 5; i++) {

            String image =
                    given()
                            .when()
                            .get(ENDPOINT)
                            .then()
                            .spec(ResponseSpecs.successJson())
                            .body("status", equalTo("success"))
                            .extract()
                            .path("message");

            images.add(image);
        }

        long differentImages = images.stream()
                .distinct()
                .count();

        assertTrue(
                differentImages > 1,
                "As chamadas consecutivas deveriam retornar mais de uma imagem."
        );
    }
}