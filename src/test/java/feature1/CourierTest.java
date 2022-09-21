package feature1;

import courier.Courier;
import io.qameta.allure.Allure;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class CourierTest {

    @Test
    public void courierTest() {
        Courier courier = new Courier(
                "test@example.com",
                "randomLogin",
                "P@ssw0rd",
                "Random Name"
        );

        boolean isOk = given().log().all()
                .header("Content-Type", "application/json")
                .baseUri("https://qa-scooter.praktikum-services.ru/api/v1")
                .body(courier)
                .when()
                .post("/courier")
                .then().log().all()
                .assertThat()
                .statusCode(201)
                .extract()
                .path("ok");

        String creds = "{" +
                "\"login\":\"" + courier.getLogin() + "\"," +
                "\"password\":\"" + courier.getPassword() + "\"" +
                "}";
        int id = given().log().all()
                .header("Content-Type", "application/json")
                .baseUri("https://qa-scooter.praktikum-services.ru/api/v1")
                .body(creds)
                .when()
                .post("/login")
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("id");

        assertTrue(isOk);
        assertNotEquals(0, id);
    }
}
