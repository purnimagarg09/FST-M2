package LiveProject;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.oauth2;

public class GitHubProjectTest {
    RequestSpecification reqSpec;

    String key;
    int id;

    @BeforeClass
    public void setUp(){
        reqSpec=new RequestSpecBuilder()
                .setContentType("application/json")     //Content type set to JSON
                .setAuth(oauth2("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"))        //Authorization header
                // passing token (in place of username and password)
                .setBaseUri("https://api.github.com")
                .build();
    }

    @Test(priority = 1)
    public void postSSHTest(){
        Map<String, String> reqBody = new HashMap<>();
        reqBody.put("title", "TestAPIKey");
        reqBody.put("key", "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCgoU4zoWqwA0lqQHrr9GSRma9G0MQ4AC/v/nyC5wQveGSTHg1SAwmq8U0jxod9u0lXaGeGxtJEMDauxeXV+v9O78reFYVK/7AIhc9DAqfayFZaDAZu4e4YAZ3my9h/7/5DO0YM2igoxOyQLCe7Ed8/KQxx6UxdCtPNrvorZTvkswvzh8hCmtIqBvlofYjAaNeWDneKMUtvaQgOhpMEr9mwNkDBxrY8Y3W6+tqK7dqAoLNhpkKZ95VkPp4T56tFk/BEIqVazft4FdPcuQ1OmnE6lo6CpFQDos7kXuj7kJ0tOJG1eTCN/+ywimIpWVL+A2mjHN8rwkTjWjav/Oi5Dwc3");
        //SSH key to ensure we are interacting with reliable source

        //Send the POST request to the URI resource /user/keys and save the response in a variable of type Response.
        Response response = given().spec(reqSpec).body(reqBody)
                .when().post("/user/keys");

        System.out.println("POST REQUEST");
        System.out.println(response.getBody().asPrettyString());

        //From the response, extract the id of the SSH key that was just added and save it in the integer variable
        id = response.then().extract().path("id");

        //Assertion
        response.then().statusCode(201);
    }

    @Test(priority = 2)
    public void getSSHTest(){
        Response response = given().spec(reqSpec)
                .pathParam("keyId",id)
                .when().get("/user/keys/{keyId}");

        System.out.println("GET REQUEST");
        System.out.println(response.getBody().asPrettyString());

        //Assertion
        response.then().statusCode(200);
    }

    @Test(priority = 3)
    public void deleteSSHTest(){
        Response response = given().spec(reqSpec)
                .pathParam("keyId",id)
                .when().delete("/user/keys/{keyId}");

        System.out.println("DELETE REQUEST");
        System.out.println(response.getBody().asPrettyString());

        //Assertion
        response.then().statusCode(204);
    }
}
