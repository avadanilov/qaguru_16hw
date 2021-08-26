import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.hamcrest.Matchers.is;

public class ReqresTests {
    String baseUrl = "https://reqres.in/";

    @Test
    void get404Test() {
        get("https://reqres.in/api/unknown/23")
                .then()
                .statusCode(404);
    }

    @Test
    void createTest() {
        String response = given().contentType(ContentType.JSON)
                .body("{\"name\": \"morpheus\",\"job\": \"leader\"}")
                .when()
                .post(baseUrl + "api/users")
                .then()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("leader"))
                .extract().body().asString();

        assertThat(response).contains("id").contains("createdAt");
    }

    @Test
    void updateTest() {
        String response = given().contentType(ContentType.JSON)
                .body("{\"name\": \"morpheus\",\"job\": \"zion resident\"}")
                .when()
                .put(baseUrl + "api/users/2")
                .then()
                .statusCode(200)
                .body("name", is("morpheus"))
                .body("job", is("zion resident"))
                .extract().body().asString();

        assertThat(response).contains("updatedAt");
    }

    @Test
    void updatePatchTest() {
        String response = given().contentType(ContentType.JSON)
                .body("{\"name\": \"morpheus\",\"job\": \"zion resident\"}")
                .when()
                .patch(baseUrl + "api/users/2")
                .then()
                .statusCode(200)
                .body("name", is("morpheus"))
                .body("job", is("zion resident"))
                .extract().body().asString();

        assertThat(response).contains("updatedAt");
    }

    @Test
    void deleteTest() {
        delete(baseUrl + "api/users/2")
                .then()
                .statusCode(204);
    }
}
