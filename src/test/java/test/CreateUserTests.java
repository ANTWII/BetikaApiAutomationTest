package test;

import com.github.javafaker.Faker;
import config.reqresConfig;
import endpoints.reqresEndPoints;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import payload.CreateUserPayload;
import payload.LoginPayload;

public class CreateUserTests extends reqresConfig {
    CreateUserPayload createUserPayload;
    private final Faker faker = new Faker();

    /**
     *Test case to register a new User
     */
    @Test(priority = 1,groups ={"regression"})
    public void testCreateUser(ITestContext context){

        String name=faker.name().lastName();
        String job=faker.job().title();



        createUserPayload=new CreateUserPayload();

        createUserPayload.setName(name);
        createUserPayload.setJob(job);

        logger.info("***creating user*******");
        Response response= reqresEndPoints.CreateUser(createUserPayload,context);
        response.then().log().all();
// Parse JSON response
        JsonPath jsonPath = response.jsonPath();

        Assert.assertEquals(response.getStatusCode(),201);
// Check user details
        Assert.assertEquals(jsonPath.getString("name"), name);
        Assert.assertEquals(jsonPath.getString("job"), job);

        logger.info("user creation successful******");

    }
}
