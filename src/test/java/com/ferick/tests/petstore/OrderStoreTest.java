package com.ferick.tests.petstore;

import com.ferick.config.Configuration;
import com.ferick.endpoints.PetStoreService;
import com.ferick.enums.OrderStatus;
import com.ferick.model.pet.Pet;
import com.ferick.model.store.Order;
import com.ferick.tests.AbstractTest;
import com.ferick.tools.Utilities;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("order")
@Configuration("petstore.json")
public class OrderStoreTest extends AbstractTest {

    protected static final String ORDER_TEMPLATE_PATH = "store/order-template.txt";

    @BeforeEach
    public void setupPet() {
        var body = baseMethods().getBodyFromTemplate(PET_TEMPLATE_PATH, new HashMap<>());

        var petAdded = given().spec(baseMethods().getBaseSpecification())
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post(PetStoreService.Pet.BASIC)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(Pet.class);

        context.setVariable(PET_ID_VAR, petAdded.getId());
    }

    @Test
    @DisplayName("Placing an order to buy a pet")
    public void placeOrder() {
        var petId = context.getStringVariable(PET_ID_VAR);
        var body = baseMethods().getBodyFromTemplate(ORDER_TEMPLATE_PATH, Utilities.asMap(PET_ID_VAR, petId));

        var actualOrder = given().spec(baseMethods().getBaseSpecification())
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post(PetStoreService.Pet.BASIC)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(Order.class);

        assertEquals(OrderStatus.PLACED.getStatus(), actualOrder.getStatus());
    }
}
