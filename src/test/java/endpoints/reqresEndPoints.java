package endpoints;

import io.restassured.response.Response;
import org.testng.ITestContext;
import payload.CreateUserPayload;
import payload.LoginPayload;
import payload.RegisterPayload;

import java.util.ResourceBundle;

import static io.restassured.RestAssured.given;

public class reqresEndPoints {

    /**
     *Method created for getting URL's from properties file
     */
    static ResourceBundle getURL(){

        ResourceBundle routes=ResourceBundle.getBundle("ReqresApi");//Load Properties file
        return routes;
    }


    /**
     * this method will handle  Register   test cases
     */
    public static Response Register(RegisterPayload registerPayload, ITestContext context){
        String post_Register=   getURL().getString("post_Register");

        Response response=
                given()
                        .contentType("application/json")
                        .body(registerPayload)
                        .when()
                        .post(post_Register);


        return response;
    }

    /**
     * this method will handle  Login   test cases
     */
    public static Response Login(LoginPayload loginPayload, ITestContext context){
        String post_Login=   getURL().getString("post_Login");

        Response response=
                given()
                        .contentType("application/json")
                        .body(loginPayload)
                        .when()
                        .post(post_Login);


        return response;
    }

    /**
     * this method will handle  Create user   test cases
     */
    public static Response CreateUser(CreateUserPayload createUserPayload, ITestContext context){
        String post_Create=   getURL().getString("post_Create");

        Response response=
                given()
                        .contentType("application/json")
                        .body(createUserPayload)
                        .when()
                        .post(post_Create);

        //setting id as a global Variable
     String   id = response.jsonPath().get("id");

        if (id != null) {
            System.out.println("Setting ID: " + id);
            context.getSuite().setAttribute("ID",id);
        }

        return response;
    }

    public static Response UpdateUser(CreateUserPayload updateUserPayload, String userid){
        String put_Update=   getURL().getString("put_Update");

        Response response=
                given()
                        .contentType("application/json")
                        .pathParam("id",userid)
                        .body(updateUserPayload)
                        .when()
                        .put(put_Update);


        return response;
    }

    public static Response DeleteUser(String userid){
        String deleteUser=   getURL().getString("deleteUser");

        Response response=
                given()
                        .pathParam("id",userid)
                        .when()
                        .delete(deleteUser);


        return response;
    }
}
