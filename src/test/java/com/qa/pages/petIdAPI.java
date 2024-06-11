package com.qa.pages;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;


public class petIdAPI {

    //users endpoint is used to indicate the endpoint name
    private static final String USERS_ENDPOINT = "/pet";

    //For the Method get pet by id
    public Response getPetById(int petId) {
        return given()
                .pathParam("id", petId)
                .when()
                .get(USERS_ENDPOINT + "/{id}");
}
 public Response createPet(String id, String name, String status) {
        return given()
                .contentType("application/json")
                .body("{ \"name\": \"" + name + "\", \"status\": \"" + status + "\" }")
                .when()
                .post(USERS_ENDPOINT);

    }

}