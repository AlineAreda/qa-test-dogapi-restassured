package com.ceodog.api.tests;

import com.ceodog.api.config.BaseTest;
import com.ceodog.api.specs.ResponseSpecs;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@Feature("Breed Images")
@DisplayName("Testes do endpoint /breed/{breed}/images")
class BreedImagesTest extends BaseTest {

    private static final String ENDPOINT = "/breed/{breed}/images";

    @Test
    @Description("""
            Verifica se a API retorna com sucesso as imagens
            associadas a uma raça válida.
            """)
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Deve retornar imagens de uma raça válida")
    void deveRetornarImagensDeUmaRacaValida() {

        given()
                .pathParam("breed", "beagle")
                .when()
                .get(ENDPOINT)
                .then()
                .spec(ResponseSpecs.successJson())
                .body("status", equalTo("success"))
                .body("message", notNullValue());
    }

    @Test
    @Description("Verifica se uma raça válida retorna pelo menos uma imagem.")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Deve retornar uma lista de imagens não vazia")
    void deveRetornarListaDeImagensNaoVazia() {

        given()
                .pathParam("breed", "beagle")
                .when()
                .get(ENDPOINT)
                .then()
                .spec(ResponseSpecs.successJson())
                .body("message", instanceOf(List.class))
                .body("message.size()", greaterThan(0));
    }

    @Test
    @Description("""
            Verifica se todas as imagens retornadas pela API
            são representadas por URLs em formato String.
            """)
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Deve retornar imagens no formato esperado")
    void deveRetornarImagensNoFormatoEsperado() {

        List<?> images =
                given()
                        .pathParam("breed", "beagle")
                        .when()
                        .get(ENDPOINT)
                        .then()
                        .spec(ResponseSpecs.successJson())
                        .extract()
                        .path("message");

        assertFalse(
                images.isEmpty(),
                "A lista de imagens não deve estar vazia."
        );

        images.forEach(image -> {
            assertNotNull(
                    image,
                    "A URL da imagem não deve ser nula."
            );

            assertInstanceOf(
                    String.class,
                    image,
                    "Cada imagem deve ser representada como String."
            );

            assertFalse(
                    ((String) image).isBlank(),
                    "A URL da imagem não deve estar vazia."
            );
        });
    }

    @ParameterizedTest(name = "Deve retornar imagens para a raça {0}")
    @ValueSource(strings = {
            "labrador",
            "poodle",
            "bulldog",
            "beagle"
    })
    @Description("Verifica se diferentes raças válidas podem ser consultadas.")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Deve retornar imagens para diferentes raças válidas")
    void deveRetornarImagensParaDiferentesRacas(String breed) {

        given()
                .pathParam("breed", breed)
                .when()
                .get(ENDPOINT)
                .then()
                .spec(ResponseSpecs.successJson())
                .body("status", equalTo("success"))
                .body("message", notNullValue())
                .body("message.size()", greaterThan(0));
    }

    @Test
    @Description("""
            Verifica se a API trata corretamente uma raça inexistente,
            retornando erro 404 e a mensagem correspondente.
            """)
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Deve retornar erro ao consultar uma raça inexistente")
    void deveRetornarErroParaRacaInexistente() {

        given()
                .pathParam("breed", "invalido")
                .when()
                .get(ENDPOINT)
                .then()
                .statusCode(404)
                .contentType("application/json")
                .body("status", equalTo("error"))
                .body("message", equalTo("Breed not found (main breed does not exist)"))
                .body("code", equalTo(404));
    }
}