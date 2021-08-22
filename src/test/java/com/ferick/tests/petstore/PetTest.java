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
import static org.junit.jupiter.api.Assertions.*;

@Tag("pet")
@Configuration("petstore.yml")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PetTest extends AbstractTest {

    private static final String PET_SCHEMA_PATH = "schemas/pet/pet-schema.json";

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
        assertTrue(checkPetExists(actualPet.getId()), "Pet was not added");
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

        assertFalse(checkPetExists((Integer) petId), "Pet was not deleted");
    }

    private Boolean checkPetExists(Integer petId) {
        int statusCode = given().spec(baseMethods().getBaseSpecification())
                .pathParam("petId", petId)
                .when()
                .get(PetStoreService.Pet.BY_ID)
                .then()
                .extract().statusCode();

        if (statusCode == HttpStatus.SC_OK) {
            return true;
        } else if (statusCode == HttpStatus.SC_NOT_FOUND) {
            return false;
        } else {
            throw new IllegalStateException("Checking pet existence returned unexpected status " + statusCode);
        }
    }
}
