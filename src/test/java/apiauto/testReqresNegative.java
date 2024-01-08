package apiauto;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class testReqresNegative {

    @Test
    public void testGetListUsers() {
        ValidatableResponse all = given().when()
                .get("https://reqres.in/api/users?page=2")
                .then().log().all()
                .assertThat().statusCode(200)
                .assertThat().body("per_page", Matchers.equalTo(5))
                .assertThat().body("page", Matchers.equalTo(2));
    }

    @Test
    public void testPostCreateUser() {

        String valueName = "Joshua";
        String valueJob = "QA";

        JSONObject bodyObj = new JSONObject();

        bodyObj.put("name", valueName);
        bodyObj.put("job", valueJob);

        given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(bodyObj.toString())
                .when()
                .post("https://reqres.in/api/users")
                .then().log().all()
                .assertThat().statusCode(201)
                .assertThat().body("12345", Matchers.equalTo(valueName));

    }

    @Test
    public void testPutUser() {
        RestAssured.baseURI = "https://reqres.in/api/users/2";
        int userId = 2;
        String newName = "aslemweo";
        String fname = given().when().get("api/users"+userId).getBody().jsonPath().get("data.first_name");
        String lname = given().when().get("api/users"+userId).getBody().jsonPath().get("data.last_name");
        String avatar = given().when().get("api/users"+userId).getBody().jsonPath().get("data.avatar");
        String email = given().when().get("api/users"+userId).getBody().jsonPath().get("data.email");
        System.out.println("name before ="+fname);

        HashMap<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("id", userId);
        bodyMap.put("email", email);
        bodyMap.put("first_name", newName);
        bodyMap.put("lastname", lname);
        bodyMap.put("avatar", avatar);
        JSONObject jsonObject = new JSONObject(bodyMap);

        given().log().all()
                .header("Content-Type", "application/json")
                .body(jsonObject.toString())
                .put("api/users"+userId)
                .then().log().all()
                .assertThat().statusCode(200)
                .assertThat().body("ujang123", Matchers.equalTo(newName));

    }

    @Test
    public void testPatchUser() {
        RestAssured.baseURI = "https://reqres.in/api/users/2";
        int userId = 3;
        String newName = "aselole";
        String fname = given().when().get("api/users"+userId)
                .getBody().jsonPath().get("data.first_name");
        System.out.println("name before"+fname);

        HashMap<String, String> bodyMap = new HashMap<>();
        bodyMap.put("first_name", newName);
        JSONObject jsonObject = new JSONObject(bodyMap);

        given().log().all()
                .header("Content-Type", "application/json")
                .body(jsonObject.toString())
                .patch("api/users"+userId)
                .then().log().all()
                .assertThat().statusCode(200)
                .assertThat().body("asep@#", Matchers.equalTo(newName));
    }

    @Test
    public void testDeletUser() {
        RestAssured.baseURI = "https://reqres.in/api/users/2";
        int userToDelete = 4;
        given().log().all()
                .when().delete("api/users/"+ userToDelete)
                .then()
                .log().all()
                .assertThat().statusCode(204);
    }
}
