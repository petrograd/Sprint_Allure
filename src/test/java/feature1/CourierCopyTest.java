package feature1;

import courier.Courier;
import courier.CourierClient;
import courier.CourierCredentials;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CourierCopyTest {

    Courier courier;
    CourierClient courierClient;
    private int courierId;

    @Before
    public void setup() {
        courier = Courier.getRandomCourier();
        courierClient = new CourierClient();
    }

    @After
    public void teardown() {
        courierClient.delete(courierId);
    }
    
    @Test
    public void courierTest() {
        boolean isOk = courierClient.create(courier)
                .extract().path("ok");
        
        CourierCredentials creds = CourierCredentials.from(courier);
        courierId = courierClient.login(creds)
                .extract().path("id");

        assertTrue(isOk);
        assertNotEquals(0, courierId);
    }

    @Test
    public void createWithoutPassword() {
        courier = Courier.getWithoutPassword();

        boolean isOk = courierClient.createFailed(courier)
                .extract().path("ok");

        assertFalse(isOk);
    }

    @Test
    public void createWithoutPasswordOnlyRequest() {
        courier = Courier.getWithoutPassword();

        boolean isOk = courierClient.create(courier)
                .statusCode(201)
                .extract().path("ok");

        assertFalse(isOk);
    }
}
