package config;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.DEFAULT_PATH;
import static io.restassured.RestAssured.DEFAULT_URI;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.lessThan;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class reqresConfig {
    public Logger logger; //variable for logs
@BeforeClass
public void setup() {
    logger= LogManager.getLogger(this.getClass());


    RestAssured.requestSpecification = new RequestSpecBuilder()
            .setBaseUri("https://reqres.in/")
            .setContentType("application/json")
            .addFilter(new RequestLoggingFilter())
            .addFilter(new ResponseLoggingFilter())
            .build();
    RestAssured.responseSpecification = new ResponseSpecBuilder()
            .expectHeader("CF-Cache-Status", containsString("DYNAMIC"))
            .expectHeader("Server", containsString("cloudflare"))
         //   .expectHeader("Content-Type", containsString("application/json"))

            //response time verification for all tests to be less than 10 seconds
            .expectResponseTime(lessThan(10000L))
            .build();
}

    @AfterClass
    public void cleanup() {
        RestAssured.responseSpecification = null;
        RestAssured.requestSpecification = null;
        RestAssured.baseURI=DEFAULT_URI;
        RestAssured.basePath=DEFAULT_PATH;
    }

}
