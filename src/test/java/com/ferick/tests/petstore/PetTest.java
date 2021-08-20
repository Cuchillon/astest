package com.ferick.tests.petstore;

import com.ferick.config.Configuration;
import com.ferick.endpoints.PetStoreService;
import com.ferick.model.pet.Pet;
import com.ferick.tests.AbstractTest;
import com.ferick.tools.JsonTransformer;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("pet")
@Configuration("petstore.json")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PetTest extends AbstractTest {

    private static final String PET_TEMPLATE_PATH = "pet/pet-template.txt";
    private static final String PET_SCHEMA_PATH = "schemas/pet/pet-schema.json";
    private static final String PET_ID_VAR = "petId";

    @Test
    @Order(1)
    @DisplayName("Adding a new pet to the store")
    public void addPet() {
        var body = baseMethods().getBodyFromTemplate(PET_TEMPLATE_PATH, new HashMap<>());

        var actualPet = given().spec(baseMethods().getBaseSpecification())
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post(PetStoreService.Pet.BASIC)
                .then().log().all()
                .statusCode(HttpStatus.SC_OK)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(PET_SCHEMA_PATH))
                .extract().as(Pet.class);

        context.setVariable(PET_ID_VAR, actualPet.getId());

        var expectedPet = JsonTransformer.fromJson(body, Pet.class);
        assertEquals(expectedPet, actualPet);
    }

    @Test
    @Order(2)
    @DisplayName("Removing a pet from the store")
    public void removePet() {
        var petId = context.getVariable(PET_ID_VAR)
                .orElseThrow(() -> new IllegalStateException("Pet id is missing"));

        given().spec(baseMethods().getBaseSpecification())
                .pathParam("petId", petId)
                .when()
                .delete(PetStoreService.Pet.BY_ID)
                .then().log().all()
                .statusCode(HttpStatus.SC_OK);
    }
}
