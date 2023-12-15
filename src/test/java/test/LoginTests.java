package test;

import config.reqresConfig;
import endpoints.reqresEndPoints;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import payload.LoginPayload;


public class LoginTests extends reqresConfig {

    LoginPayload loginPayload;

    /**
     *Test case to register a new User
     */
    @Test(priority = 1,groups ={"regression"})
    public void testsuccessfulLogin(ITestContext context){

        String email="eve.holt@reqres.in";
        String password="cityslicka";



        loginPayload=new LoginPayload();

        loginPayload.setEmail(email);
        loginPayload.setPassword(password);

        logger.info("***Logging in *******");
        Response response= reqresEndPoints.Login(loginPayload,context);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(),200);

        logger.info("login successful*********");

    }

    @Test(priority = 2,groups ={"regression"})
    public void testUnsuccessfulLogin(ITestContext context){

        String email="eve.holt@reqres.in";



        loginPayload=new LoginPayload();

        loginPayload.setEmail(email);

        logger.info("***Logging in without password *******");
        Response response= reqresEndPoints.Login(loginPayload,context);
        response.then().log().all();
        JsonPath jsonPath = response.jsonPath();
        Assert.assertEquals(response.getStatusCode(),400);
        Assert.assertEquals(jsonPath.getString("error"), "Missing password");
        logger.info("login Unsuccessful******");

    }
}
