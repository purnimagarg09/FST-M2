package activities;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Activity3{

    RequestSpecification reqSpec;
    ResponseSpecification resSpec;
    int petId;

    @BeforeClass
    public void setUp(){
        //Request Specification
        reqSpec = new RequestSpecBuilder()
                .setBaseUri("https://petstore.swagger.io/v2/pet")
                .setContentType("application/json")
                .build();

        //Response Specification
        resSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType("application/json")
                .expectBody("status",equalTo("alive"))
                .build();
    }

    @Test(priority = 1)
    public void addPet(){
        //Request body as String
        String pet1 = "{"
                + "\"id\": 77211,"
                + "\"name\": \"PetOne\","
                + " \"status\": \"alive\""
                + "}";

        //Generate Response
        Response response = given().spec(reqSpec).body(pet1)
                .when().post();

        //Request body as map
        Map<String, Object> pet2 = new HashMap<>();
        pet2.put("id", 77212);
        pet2.put("name", "PetTwo");
        pet2.put("status", "alive");

        //Generate Response
        response = given().spec(reqSpec).body(pet2)
                .when().post();

        // Assertions
        response.then().spec(resSpec);
    }

    @DataProvider
    public Object[][] petInfo() {
        Object[][] petData = new Object[][]{
                {77211, "PetOne", "alive"},
                {77212, "PetTwo", "alive"}
        };
        return petData;
    }

    @Test(dataProvider = "petInfo",priority=2)
    public void getPet(int id,String name,String status) {
            Response response = given().spec(reqSpec) // Use requestSpec
                    .pathParam("petId", id) // Add path parameter  https://petstore.swagger.io/v2/pet/{petId}
                    .when().get("/{petId}"); // Send GET request

            // Print response
            System.out.println(response.asPrettyString());
            // Assertions
            response.then()
                    .spec(resSpec) // Use responseSpec
                    .body("name", equalTo(name)); // Additional Assertion
    }

    @Test(dataProvider = "petInfo", priority=3)
    public void deletePet(int id, String name, String status) {
        Response response = given().spec(reqSpec) // Use requestSpec
                .pathParam("petId", id) // Add path parameter       https://petstore.swagger.io/v2/pet/{petId}
                .when().delete("/{petId}"); // Send GET request

        // Assertions
        response.then().body("code", equalTo(200));
    }
}
