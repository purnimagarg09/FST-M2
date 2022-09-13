package LiveProject;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

@ExtendWith(PactConsumerTestExt.class)
public class ConsumerTest {
    //Headers
    Map<String, String> headers = new HashMap<>();

    //Resource Path
    String resourcePath = "/api/users";         //  POST http://localhost:8585/api/users

    //Create the pact
    @Pact(consumer = "UserConsumer", provider = "UserProvider")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        //This contract is basically expectation - list of expected values
        //Add headers
        headers.put("Content-Type", "application/json");

        //Create body
        DslPart requestResponseBody = new PactDslJsonBody()
                .numberType("id", 123)
                .stringType("firstName", "Purnima")
                .stringType("lastName", "Garg")
                .stringType("email", "purnima@test.com");

        //Create Contract
        return builder.given("A request to create a user")
                .uponReceiving("A request to create a user")    //written with respect to Provider
                  .method("POST")
                  .path(resourcePath)
                  .headers(headers)
                  .body(requestResponseBody)
                .willRespondWith()
                  .status(201)
                  .body(requestResponseBody)
                //If need to write more requests like Get etc, we need to copy paste from .given till above interaction
                .toPact();
    }

    @Test
    @PactTestFor(providerName = "UserProvider", port = "8282")      //Generates mock server
    public void consumerTest(){
        String baseURI = "http://localhost:8282";

        //Create request body
        Map<String ,Object> reqBody = new HashMap<>();
        reqBody.put("id",123);
        reqBody.put("firstName","Purnima");
        reqBody.put("lastName","Garg");
        reqBody.put("email","purnima@test.com");

        //Generate response
        given().headers(headers).body(reqBody).
                when().post(baseURI+resourcePath).       //https://localhost:8282/api/users
                then().statusCode(201).log().all();
    }
}
