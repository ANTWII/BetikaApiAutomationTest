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

public class UpdateUserTests extends reqresConfig {
    CreateUserPayload UpdateUserPayload;
    private final Faker faker = new Faker();

    /**
     *Test case to register a new User
     */
    @Test(priority = 1,groups ={"regression"},dependsOnMethods = "testCreateUser")
    public void testUpdateUser(ITestContext context){
        String userID=(String) context.getSuite().getAttribute("ID");
        String name=faker.name().lastName();
        String job=faker.job().title();



        UpdateUserPayload=new CreateUserPayload();

        UpdateUserPayload.setName(name);
        UpdateUserPayload.setJob(job);

        logger.info("***creating user*******");
        Response response= reqresEndPoints.UpdateUser(UpdateUserPayload,userID);
        response.then().log().all();
// Parse JSON response
        JsonPath jsonPath = response.jsonPath();

        Assert.assertEquals(response.getStatusCode(),200);
// Check user details
        Assert.assertEquals(jsonPath.getString("name"), name);
        Assert.assertEquals(jsonPath.getString("job"), job);
        logger.info("user creation successful******");

    }
}
