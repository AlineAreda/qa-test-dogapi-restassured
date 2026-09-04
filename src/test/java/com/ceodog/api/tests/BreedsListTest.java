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
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@Feature("Breed List")
@DisplayName("Testes do endpoint /breeds/list/all")
class BreedsListTest extends BaseTest {

    private static final String ENDPOINT = "/breeds/list/all";

    @Test
    @Description("""
            Verifica se o endpoint está disponível e retorna uma resposta válida,
            contendo status de sucesso e a lista de raças em formato JSON.
            """)
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Deve retornar todas as raças com sucesso")
    void deveRetornarTodasAsRacasComSucesso() {

        given()
        .when()
                .get(ENDPOINT)
        .then()
                .spec(ResponseSpecs.successJson())
                .body("status", equalTo("success"))
                .body("message", notNullValue());
    }

    @Test
    @Description("Verifica se a API retorna pelo menos uma raça disponível para consulta.")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Deve retornar uma lista de raças não vazia")
    void deveRetornarListaDeRacasNaoVazia() {

        given()
        .when()
                .get(ENDPOINT)
        .then()
                .spec(ResponseSpecs.successJson())
                .body("message.size()", greaterThan(0));
    }

    @Test
    @Description("""
            Verifica se cada raça retornada possui uma lista de sub-raças.
            A lista pode ser vazia quando a raça não possui sub-raças.
            """)
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Deve retornar estrutura válida para todas as raças")
    void deveRetornarEstruturaValidaParaTodasAsRacas() {

        Map<String, Object> breeds =
                given()
                .when()
                        .get(ENDPOINT)
                .then()
                        .spec(ResponseSpecs.successJson())
                        .extract()
                        .path("message");

        assertFalse(
                breeds.isEmpty(),
                "A lista de raças não deve estar vazia."
        );

        breeds.forEach((breed, subBreeds) -> {

            assertNotNull(
                    breed,
                    "O nome da raça não deve ser nulo."
            );

            assertFalse(
                    breed.isBlank(),
                    "O nome da raça não deve estar vazio."
            );

            assertNotNull(
                    subBreeds,
                    "A lista de sub-raças não deve ser nula."
            );

            assertInstanceOf(
                    List.class,
                    subBreeds,
                    "As sub-raças devem ser retornadas como uma lista."
            );

            List<?> subBreedList = (List<?>) subBreeds;

            subBreedList.forEach(subBreed ->
                    assertInstanceOf(
                            String.class,
                            subBreed,
                            "Cada sub-raça deve ser representada como String."
                    )
            );
        });
    }

    @ParameterizedTest(name = "Deve conter a raça {0}")
    @ValueSource(strings = {
            "labrador",
            "poodle",
            "bulldog",
            "beagle"
    })
    @Description("Verifica se raças conhecidas estão disponíveis na lista retornada pela API.")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Deve conter raças conhecidas na lista")
    void deveConterRacasConhecidasNaLista(String breed) {

        given()
        .when()
                .get(ENDPOINT)
        .then()
                .spec(ResponseSpecs.successJson())
                .body("message." + breed, notNullValue());
    }
}