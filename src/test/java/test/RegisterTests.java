package test;

import com.github.javafaker.Faker;
import config.reqresConfig;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import payload.RegisterPayload;
import endpoints.reqresEndPoints;

public class RegisterTests extends reqresConfig {
    RegisterPayload register;
    private final Faker faker = new Faker();


    /**
     *Test case to register a new User
     */
    @Test(priority = 1,groups ={"regression"})
    public void testsuccessfulRegister(ITestContext context){

String email="eve.holt@reqres.in";
String password=faker.number().digits(10);



        register=new RegisterPayload();

        register.setEmail(email);
        register.setPassword(password);

        logger.info("***register new user *******");
        Response response= reqresEndPoints.Register(register,context);
        response.then().log().all();
       // Parse JSON response
        JsonPath jsonPath = response.jsonPath();
        SoftAssert sa=new SoftAssert();
        Assert.assertEquals(response.getStatusCode(),200);
        // Validate that "id" and "token" are not null
        sa.assertNotNull(jsonPath.getString("id"), "ID should not be null");
        sa.assertNotNull(jsonPath.getString("token"), "Token should not be null");
        logger.info("***new user registration successfully completed*******");

    }

    /**
     *Test case to register a new User
     */
    @Test(priority = 2,groups ={"regression"})
    public void testUnsuccessfulRegister(ITestContext context){

        String email="sydney@fife";




        register=new RegisterPayload();

        register.setEmail(email);


        logger.info("***register new user without passwors *******");
        Response response= reqresEndPoints.Register(register,context);
        response.then().log().all();
        // Parse JSON response
        JsonPath jsonPath = response.jsonPath();
        Assert.assertEquals(response.getStatusCode(),400);
        Assert.assertEquals(jsonPath.getString("error"), "Missing password");
        logger.info("***new user registration failed*******");

    }
}
