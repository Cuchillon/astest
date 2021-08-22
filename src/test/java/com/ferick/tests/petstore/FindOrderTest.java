package com.ferick.tests.petstore;

import com.ferick.config.Configuration;
import com.ferick.endpoints.PetStoreService;
import com.ferick.model.store.Order;
import com.ferick.tests.AbstractTest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("findOrder")
@Configuration("petstore.json")
public class FindOrderTest extends AbstractTest {

    private static final Integer BIG_ORDER_ID = 11;

    @Test
    @DisplayName("Finding order by id")
    public void findOrderById() {
        var actualOrder = given().spec(baseMethods().getBaseSpecification())
                .pathParam("orderId", BIG_ORDER_ID)
                .when()
                .get(PetStoreService.Store.ORDER_BY_ID)
                .then().log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(Order.class);

        assertEquals(BIG_ORDER_ID, actualOrder.getId());
    }
}
