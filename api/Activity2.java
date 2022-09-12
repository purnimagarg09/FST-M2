package activities;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Activity2 {
    //Base URI
    String baseURI = "https://petstore.swagger.io/v2/user";

    @Test(priority = 1)
    public void addUser() throws FileNotFoundException,IOException {
        // Import JSON file
        File file = new File("src/test/resources/Activity2.json");
        FileInputStream inputJSON = new FileInputStream(file);

        // Read JSON file as String
        String req = new String(inputJSON.readAllBytes());

        Response response = given().contentType(ContentType.JSON) // Set headers
                .body(req) // Add request body
                .when().post(baseURI); //POST Request

        //Assertions
        response.then().body("code", equalTo(200));
        response.then().body("message", equalTo("9999"));
    }

    @Test(priority=2)
    public void getUser() {

        File outputJSON = new File("src/test/resources/Activity2Get.json");
        Response response =
                given().contentType(ContentType.JSON) // Set headers
                        .when().pathParam("username", "testusr") // Set path parameter
                        .get(baseURI + "/{username}"); // Send GET request

        // Get response body
        String resBody = response.getBody().asPrettyString();

        try {
            // Create JSON file
            outputJSON.createNewFile();
            // Write response body to external file
            FileWriter writer = new FileWriter(outputJSON.getPath());
            writer.write(resBody);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Assertion
        response.then().body("id", equalTo(9999));
        response.then().body("username", equalTo("testusr"));
        response.then().body("firstName", equalTo("Test"));
        response.then().body("lastName", equalTo("User"));
        response.then().body("email", equalTo("testuser@gmail.com"));
        response.then().body("password", equalTo("password123"));
        response.then().body("phone", equalTo("9876543210"));
    }

    @Test(priority=3)
    public void deleteUser() {
        Response response =
                given().contentType(ContentType.JSON) // Set headers
                        .pathParam("username", "testusr") // Add path parameter
                        .when().delete(baseURI + "/{username}");// Send DELETE request

        // Assertion
        response.then().body("code", equalTo(200));
        response.then().body("message", equalTo("testusr"));
    }
}
