package test;

import config.reqresConfig;
import endpoints.reqresEndPoints;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;


public class DeleteUserTests extends reqresConfig {
    @Test(priority = 1,groups ={"regression"},dependsOnMethods = "testCreateUser")
    public void testdeleteUser(ITestContext context){


     String userID=(String) context.getSuite().getAttribute("ID");

        logger.info("***Deleting user*******");
        Response response= reqresEndPoints.DeleteUser(userID);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(),204);

        logger.info("user deleted successful******");

    }
}
